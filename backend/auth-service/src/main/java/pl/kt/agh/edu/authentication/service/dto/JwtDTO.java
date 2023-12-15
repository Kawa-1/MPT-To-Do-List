package pl.kt.agh.edu.authentication.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kt.agh.model.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {
    private String jwt;
    private Role role;
}
