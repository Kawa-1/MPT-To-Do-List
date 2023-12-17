package pl.kt.agh.edu.task.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "subtasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;
    private Long tid;
    private String name;
    private String description;
    private String status;
    private LocalDateTime created;
}
