package gr.aueb.cf.pizzashop2.controller;

import gr.aueb.cf.pizzashop2.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping
    public String getAllPizzas(Model model) {
        model.addAttribute("pizzas", pizzaRepository.findAll());
        return "pizzas";
    }
}
