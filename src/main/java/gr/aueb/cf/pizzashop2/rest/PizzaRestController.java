package gr.aueb.cf.pizzashop2.rest;


import gr.aueb.cf.pizzashop2.dto.PizzaDTO;
import gr.aueb.cf.pizzashop2.dto.UserDTO;
import gr.aueb.cf.pizzashop2.model.Pizza;
import gr.aueb.cf.pizzashop2.service.IPizzaService;
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
public class PizzaRestController {

    private final IPizzaService pizzaService;
    private final MessageSource messageSource;
    private MessageSourceAccessor accessor;

    @Autowired
    public PizzaRestController(IPizzaService pizzaService, MessageSource messageSource) {
        this.pizzaService = pizzaService;
        this.messageSource = messageSource;

    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource);
    }


    @Operation(summary = "Get all pizzas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizzas Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) })
    })
    @RequestMapping(path = "/pizzas", method = RequestMethod.GET)
    public ResponseEntity<List<PizzaDTO>> getAllPizzas() {

        Iterable<Pizza> pizzas = pizzaService.getAllPizzas();
        List<PizzaDTO> pizzasDTO = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            pizzasDTO.add(new PizzaDTO(pizza.getId(), pizza.getName(), pizza.getDescription(), pizza.getPrice()));
        }
        return new ResponseEntity<>(pizzasDTO, HttpStatus.OK);

    }

    @Operation(summary = "Get pizza by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza Found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PizzaDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Pizza Not Found",
                    content = @Content)
    })
    @RequestMapping(path = "/pizzas/{pizzaName}", method = RequestMethod.GET)
    public ResponseEntity<PizzaDTO> getPizzaByName(@PathVariable("pizzaName") String name) {
        Pizza pizza;
        try {
            pizza = pizzaService.getPizzaByName(name);
            PizzaDTO PizzaDTO = new PizzaDTO(pizza.getId(),
                    pizza.getName(), pizza.getDescription(), pizza.getPrice());
            return new ResponseEntity<>(PizzaDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LoggerUtil.getCurrentLogger().warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a pizza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pizza created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PizzaDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content)})
    @RequestMapping(value = "/pizzas", method = RequestMethod.POST)
    public ResponseEntity<PizzaDTO> addPizza(@RequestBody PizzaDTO dto) {
        Pizza pizza = pizzaService.addPizza(dto);
        PizzaDTO pizzaDTO = map(pizza);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pizzaDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(pizzaDTO);
    }

    @Operation(summary = "Delete a pizza by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PizzaDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Pizza not found",
                    content = @Content)})
    @RequestMapping(value = "/pizzas/{pizzaId}", method = RequestMethod.DELETE)
    public ResponseEntity<PizzaDTO> deletePizza(@PathVariable("pizzaId") Long pizzaId) {
        try {
            Pizza pizza = pizzaService.getPizzaById(pizzaId);
            pizzaService.deletePizza(pizzaId);
            PizzaDTO pizzaDTO = map(pizza);
            return new ResponseEntity<>(pizzaDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LoggerUtil.getCurrentLogger().warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a pizza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pizza updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pizza not found",
                    content = @Content) })
    @RequestMapping(value = "/pizzas/{pizzaId}", method = RequestMethod.PUT)
    public ResponseEntity<PizzaDTO> updatePizza(@PathVariable("pizzaId") Long pizzaId,
                                              @RequestBody PizzaDTO dto) {
        try {
            dto.setId(pizzaId);
            Pizza pizza = pizzaService.updatePizza(dto);
            PizzaDTO pizzaDTO = map(pizza);
            return new ResponseEntity<>(pizzaDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LoggerUtil.getCurrentLogger().warning(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private PizzaDTO map(Pizza pizza) {
        PizzaDTO pizzaDTO = new PizzaDTO();
        pizzaDTO.setId(pizza.getId());
        pizzaDTO.setName(pizza.getName());
        pizzaDTO.setDescription(pizza.getDescription());
        pizzaDTO.setPrice(pizza.getPrice());
        return pizzaDTO;
    }

}



