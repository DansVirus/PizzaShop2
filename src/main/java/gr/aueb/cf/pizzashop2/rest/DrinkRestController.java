package gr.aueb.cf.pizzashop2.rest;

import gr.aueb.cf.pizzashop2.dto.DrinkDTO;
import gr.aueb.cf.pizzashop2.dto.UserDTO;
import gr.aueb.cf.pizzashop2.model.Drink;
import gr.aueb.cf.pizzashop2.service.IDrinkService;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.pizzashop2.service.util.LoggerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DrinkRestController {

    private final IDrinkService drinkService;
    private final MessageSource messageSource;
    private MessageSourceAccessor accessor;

    @Autowired
    public DrinkRestController(IDrinkService drinkService, MessageSource messageSource) {
        this.drinkService = drinkService;
        this.messageSource = messageSource;
    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource);
    }

    @Operation(summary = "Get all drinks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drinks Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) })
    })
    @RequestMapping(path = "/drinks", method = RequestMethod.GET)
    public ResponseEntity<List<DrinkDTO>> getAllDrinks() {
        Iterable<Drink> drinks = drinkService.getAllDrinks();
        List<DrinkDTO> drinksDTO = new ArrayList<>();
        for (Drink drink : drinks) {
            drinksDTO.add(new DrinkDTO(drink.getId(), drink.getName(), drink.getDescription(), drink.getPrice()));
        }
        return new ResponseEntity<>(drinksDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get drink by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drink Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DrinkDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Drink Not Found",
                    content = @Content)
    })
    @RequestMapping(path = "/drinks/{drinkName}", method = RequestMethod.GET)
    public ResponseEntity<DrinkDTO> getDrinkByName(@PathVariable("drinkName") String name) {
        Drink drink;
        try {
            drink = drinkService.getDrinkByName(name);
            DrinkDTO drinkDTO = new DrinkDTO(drink.getId(),
                    drink.getName(), drink.getDescription(), drink.getPrice());
            return new ResponseEntity<>(drinkDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LoggerUtil.getCurrentLogger().warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a drink")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Drink created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DrinkDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content)})
    @RequestMapping(value = "/drinks", method = RequestMethod.POST)
    public ResponseEntity<DrinkDTO> addDrink(@RequestBody DrinkDTO dto) {
        Drink drink = drinkService.addDrink(dto);
        DrinkDTO drinkDTO = map(drink);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(drinkDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(drinkDTO);
    }

    @Operation(summary = "Delete a drink by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drink Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DrinkDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Drink not found",
                    content = @Content)})
    @RequestMapping(value = "/drinks/{drinkId}", method = RequestMethod.DELETE)
    public ResponseEntity<DrinkDTO> deleteDrink(@PathVariable("drinkId") Long drinkId) {
        try {
            Drink drink = drinkService.getDrinkById(drinkId);
            drinkService.deleteDrink(drinkId);
            DrinkDTO drinkDTO = map(drink);
            return new ResponseEntity<>(drinkDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LoggerUtil.getCurrentLogger().warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a drink")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drink updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Drink not found",
                    content = @Content) })
    @RequestMapping(value = "/drinks/{drinkId}", method = RequestMethod.PUT)
    public ResponseEntity<DrinkDTO> updateDrink(@PathVariable("drinkId") Long drinkId,
                                                @RequestBody DrinkDTO dto) {
        try {
            dto.setId(drinkId);
            Drink drink = drinkService.updateDrink(dto);
            DrinkDTO drinkDTO = map(drink);
            return new ResponseEntity<>(drinkDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LoggerUtil.getCurrentLogger().warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private DrinkDTO map(Drink drink) {
        DrinkDTO drinkDTO = new DrinkDTO();
        drinkDTO.setId(drink.getId());
        drinkDTO.setName(drink.getName());
        drinkDTO.setDescription(drink.getDescription());
        drinkDTO.setPrice(drink.getPrice());
        return drinkDTO;
    }
}

