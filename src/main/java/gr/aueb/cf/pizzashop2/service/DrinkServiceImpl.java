package gr.aueb.cf.pizzashop2.service;

import gr.aueb.cf.pizzashop2.dto.DrinkDTO;
import gr.aueb.cf.pizzashop2.model.Drink;
import gr.aueb.cf.pizzashop2.repository.DrinkRepository;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DrinkServiceImpl implements IDrinkService {

    private final DrinkRepository drinkRepository;

    @Autowired
    public DrinkServiceImpl(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Transactional
    @Override
    public Drink addDrink(DrinkDTO drinkDTO) {
        return drinkRepository.save(convertToDrink(drinkDTO));
    }

    @Transactional
    @Override
    public Drink updateDrink(DrinkDTO drinkDTO) throws EntityNotFoundException {
        Drink drink = drinkRepository.findDrinkById(drinkDTO.getId());
        if (drink == null) throw new EntityNotFoundException(Drink.class, drinkDTO.getId());
        return drinkRepository.save(convertToDrink(drinkDTO));
    }

    @Transactional
    @Override
    public void deleteDrink(Long id) throws EntityNotFoundException {
        drinkRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Iterable<Drink> getAllDrinks() {
        return drinkRepository.findAll();
    }

    @Transactional
    @Override
    public Drink getDrinkByName(String name) throws EntityNotFoundException {
        Drink drink = drinkRepository.findDrinkByName(name);
        if (drink == null) throw new EntityNotFoundException(Drink.class, 0L);
        return drink;
    }

    @Transactional
    @Override
    public Drink getDrinkById(Long id) throws EntityNotFoundException {
        Drink drink = drinkRepository.findDrinkById(id);
        if (drink == null) throw new EntityNotFoundException(Drink.class, id);
        return drink;
    }

    @Transactional
    @Override
    public Iterable<Drink> getDrinksByKeyword(String keyword) throws EntityNotFoundException {
        Iterable<Drink> drinks = drinkRepository.findByNameContainingIgnoreCase(keyword);
        if (drinks == null) throw new EntityNotFoundException(Drink.class, 0L);
        return drinks;
    }

    private static Drink convertToDrink(DrinkDTO drinkDTO) {
        return new Drink(drinkDTO.getId(), drinkDTO.getName(), drinkDTO.getDescription(), drinkDTO.getPrice());
    }
}

