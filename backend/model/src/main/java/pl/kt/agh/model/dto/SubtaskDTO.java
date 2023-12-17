package pl.kt.agh.model.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubtaskDTO {
    private Long sid;
    private Long tid;
    private String name;
    private String description;
    private String status;
    private LocalDateTime created;
}
