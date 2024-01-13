package pl.kt.agh.edu.task.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/task/assign")
    public TaskDTO assignTask(@RequestParam Long tid){
        return taskService.assignTask(tid);
    }

    @GetMapping("/task/assigned")
    public List<TaskDTO> assignedTasks(){
        return taskService.getAssignedTasks();
    }

    @PostMapping("/task/subtask/create")
    public SubtaskDTO createSubtask(@RequestBody SubtaskDTO subtaskDTO) {
        return taskService.createSubtask(subtaskDTO);
    }

    @GetMapping("/task/subtask/all")
    public List<SubtaskDTO> getSubtasks(@RequestParam Long tid) {
        return taskService.getSubtasks(tid);
    }

    @PutMapping("/task/subtask/update")
    public SubtaskDTO updateSubtask(@RequestParam Long sid, @RequestParam String status) {
        return taskService.updateSubtask(sid, status);
    }
}
