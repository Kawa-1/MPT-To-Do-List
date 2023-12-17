package pl.kt.agh.edu.task.service.service;

import lombok.RequiredArgsConstructor;
import pl.kt.agh.edu.common.security.SecurityContextConverter;
import pl.kt.agh.edu.task.service.entity.Subtask;
import pl.kt.agh.edu.task.service.entity.Task;
import pl.kt.agh.edu.task.service.mapper.SubtaskMapper;
import pl.kt.agh.edu.task.service.mapper.TaskMapper;
import pl.kt.agh.edu.task.service.repository.SubtaskRepository;
import pl.kt.agh.edu.task.service.repository.TaskRepository;
import pl.kt.agh.model.dto.SubtaskDTO;
import pl.kt.agh.model.dto.TaskDTO;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final TaskMapper taskMapper;
    private final SubtaskMapper subtaskMapper;
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

    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) {
        Subtask subtask = subtaskMapper.toEntity(subtaskDTO);
        if (subtask.getCreated() == null) {
            subtask.setCreated(LocalDateTime.now());
        }

        return subtaskMapper.toDto(subtaskRepository.save(subtask));
    }

    public List<SubtaskDTO> getSubtasks(SubtaskDTO subtaskDTO){
        return subtaskRepository.findAllByTid(subtaskDTO.getTid()).stream().map(subtaskMapper::toDto).collect(Collectors.toList());
    }

    public SubtaskDTO updateSubtask(SubtaskDTO subtaskDTO) {
        Subtask oldSubtask = subtaskRepository.findBySid(subtaskDTO.getSid());
        subtaskDTO.setCreated(oldSubtask.getCreated());
        oldSubtask = subtaskMapper.toEntity(subtaskDTO);

        return subtaskMapper.toDto(subtaskRepository.save(oldSubtask));
    }
}
