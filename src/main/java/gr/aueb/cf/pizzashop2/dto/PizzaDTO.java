package gr.aueb.cf.pizzashop2.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PizzaDTO {
    private Long id;
    private String name;
    private String description;
    private double price;



    public PizzaDTO(Long id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
