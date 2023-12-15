package pl.kt.agh.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kt.agh.model.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO {
    private String username;
    private String password;
    private Role role;
}
