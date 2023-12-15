package pl.kt.agh.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CarDTO {
    private Long cid;
    private String name;
    private String description;
    private LocalDateTime created;
}
