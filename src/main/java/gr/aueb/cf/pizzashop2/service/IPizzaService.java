package gr.aueb.cf.pizzashop2.service;


import gr.aueb.cf.pizzashop2.dto.PizzaDTO;
import gr.aueb.cf.pizzashop2.model.Pizza;

import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPizzaService {
    Pizza addPizza(PizzaDTO pizzaDTO);
    Pizza updatePizza(PizzaDTO pizzaDTO) throws EntityNotFoundException;
    void deletePizza(Long id) throws EntityNotFoundException;
    Iterable<Pizza> getAllPizzas();
    Pizza getPizzaByName(String name) throws EntityNotFoundException;

    @Transactional
    Pizza getPizzaById(Long id) throws EntityNotFoundException;

    @Transactional
    Iterable<Pizza> getPizzasByKeyword(String keyword) throws EntityNotFoundException;
}
