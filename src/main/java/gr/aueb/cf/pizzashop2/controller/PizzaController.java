package gr.aueb.cf.pizzashop2.controller;

import gr.aueb.cf.pizzashop2.repository.PizzaRepository;
import gr.aueb.cf.pizzashop2.service.IPizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    private final IPizzaService pizzaService;

    @Autowired
    public PizzaController(IPizzaService pizzaService) {
        this.pizzaService = pizzaService;

    }

    @GetMapping("/api/pizzas")
    public String getAllPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllPizzas());
        return "pizzas";
    }
}
