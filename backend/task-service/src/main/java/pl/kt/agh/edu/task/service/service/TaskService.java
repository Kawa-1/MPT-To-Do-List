package pl.kt.agh.edu.task.service.service;

import lombok.RequiredArgsConstructor;
import pl.kt.agh.edu.common.security.SecurityContextConverter;
import pl.kt.agh.edu.task.service.entity.Task;
import pl.kt.agh.edu.task.service.mapper.TaskMapper;
import pl.kt.agh.edu.task.service.repository.TaskRepository;
import pl.kt.agh.model.dto.TaskDTO;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final SecurityContextConverter securityContextConverter;

    public List<TaskDTO> getTasks() {
        Long uid = securityContextConverter.resolveSecurityContextClaim(Number.class, SecurityContextConverter.ID_CLAIM).longValue();
        return taskRepository.findAllByUid(uid).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Long uid = securityContextConverter.resolveSecurityContextClaim(Number.class, SecurityContextConverter.ID_CLAIM).longValue();
        Task task = taskMapper.toEntity(taskDTO);
        if (task.getCreated() == null) {
            task.setCreated(LocalDateTime.now());
        }

        if (task.getEnd_date() == null) {
            task.setEnd_date(LocalDateTime.now().plusDays(7));
        }
        task.setUid(uid);
        return taskMapper.toDto(taskRepository.save(task));
    }

}
