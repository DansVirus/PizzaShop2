package gr.aueb.cf.pizzashop2.service;

import gr.aueb.cf.pizzashop2.dto.DrinkDTO;


import gr.aueb.cf.pizzashop2.model.Drink;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface IDrinkService {
    Drink addDrink(DrinkDTO drinkDTO);
    Drink updateDrink(DrinkDTO drinkDTO) throws EntityNotFoundException;
    void deleteDrink(Long id) throws EntityNotFoundException;
    Iterable<Drink> getAllDrinks();
    Drink getDrinkByName(String name) throws EntityNotFoundException;

    @Transactional
    Drink getDrinkById(Long id) throws EntityNotFoundException;

    @Transactional
    Iterable<Drink> getDrinksByKeyword(String keyword) throws EntityNotFoundException;
}
