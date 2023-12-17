package pl.kt.agh.edu.task.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.kt.agh.edu.task.service.service.TaskService;
import pl.kt.agh.model.dto.SubtaskDTO;
import pl.kt.agh.model.dto.TaskDTO;

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

    @PostMapping("/task/subtask/create")
    public SubtaskDTO createSubtask(@RequestBody SubtaskDTO subtaskDTO) {
        return taskService.createSubtask(subtaskDTO);
    }

    @GetMapping("/task/subtask/all")
    public List<SubtaskDTO> getSubtasks(@RequestBody SubtaskDTO subtaskDTO) {
        return taskService.getSubtasks(subtaskDTO);
    }

    @PostMapping("/task/subtask/update")
    public SubtaskDTO updateSubtask(@RequestBody SubtaskDTO subtaskDTO) {
        return taskService.updateSubtask(subtaskDTO);
    }
}
