package gr.aueb.cf.pizzashop2.repository;


import gr.aueb.cf.pizzashop2.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Drink findDrinkById(Long id);
    Drink findDrinkByName(String name);

    List<Drink> findByNameContainingIgnoreCase(String keyword);
}
