package gr.aueb.cf.pizzashop2.repository;


import gr.aueb.cf.pizzashop2.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findByNameContainingIgnoreCase(String keyword);

    List<Pizza> findByPriceLessThan(double price);
}
