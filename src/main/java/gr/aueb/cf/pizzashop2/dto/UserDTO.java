package gr.aueb.cf.pizzashop2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String username;
    @Size(min = 8, message = "Password must have at least ${min} characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*?[a-z])(?=.*?\\d).*$")
    private String password;

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
