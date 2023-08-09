package gr.aueb.cf.pizzashop2.service;

import gr.aueb.cf.pizzashop2.dto.PizzaDTO;
import gr.aueb.cf.pizzashop2.model.Pizza;
import gr.aueb.cf.pizzashop2.repository.PizzaRepository;
import gr.aueb.cf.pizzashop2.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PizzaServiceImpl implements IPizzaService {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaServiceImpl(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Transactional
    @Override
    public Pizza addPizza(PizzaDTO pizzaDTO)  {
        return pizzaRepository.save(convertToPizza(pizzaDTO));
    }


    @Transactional
    @Override
    public Pizza updatePizza(PizzaDTO pizzaDTO) throws EntityNotFoundException {
        Pizza pizza = pizzaRepository.findPizzaById(pizzaDTO.getId());
        if (pizza == null) throw new EntityNotFoundException(Pizza.class, pizzaDTO.getId());
        return pizzaRepository.save(convertToPizza(pizzaDTO));
    }

    @Transactional
    @Override
    public void deletePizza(Long id) throws EntityNotFoundException {
        pizzaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Iterable<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @Transactional
    @Override
    public Pizza getPizzaByName(String name) throws EntityNotFoundException {
        Pizza pizza;
        pizza = pizzaRepository.findPizzaByName(name);
        if (pizza == null) throw new EntityNotFoundException(Pizza.class, 0L);
        return pizza;
    }


    @Transactional
    @Override
    public Pizza getPizzaById(Long id) throws EntityNotFoundException {
        Pizza pizza;
        pizza = pizzaRepository.findPizzaById(id);
        if (pizza == null) throw new EntityNotFoundException(Pizza.class, id);
        return pizza;
    }

    @Transactional
    @Override
    public Iterable<Pizza> getPizzasByKeyword(String keyword) throws EntityNotFoundException {
        Iterable<Pizza> pizzas;
        pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword);
        if (pizzas == null) throw new EntityNotFoundException(Pizza.class, 0L);
        return pizzas;
    }


    private static Pizza convertToPizza(PizzaDTO pizzaDTO) {
        return new Pizza(pizzaDTO.getId(), pizzaDTO.getName(), pizzaDTO.getDescription() ,pizzaDTO.getPrice());
    }
}
