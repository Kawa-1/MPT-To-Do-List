package pl.kt.agh.model.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO {
    private Long tid;
    private Long cid;
    private String name;
    private String description;
    private LocalDateTime created;
    private LocalDateTime end_date;
}
