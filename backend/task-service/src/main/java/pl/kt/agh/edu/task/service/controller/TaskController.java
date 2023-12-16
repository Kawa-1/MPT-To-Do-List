package pl.kt.agh.edu.task.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.ws.rs.core.SecurityContext;
import pl.kt.agh.edu.common.security.SecurityContextConverter;
import pl.kt.agh.edu.task.service.service.TaskService;
import pl.kt.agh.model.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/task/all")
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

    @PostMapping("/task/create")
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

}
