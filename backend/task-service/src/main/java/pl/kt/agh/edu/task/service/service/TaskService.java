package pl.kt.agh.edu.task.service.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kt.agh.edu.common.security.SecurityContextConverter;
import pl.kt.agh.edu.task.service.entity.Subtask;
import pl.kt.agh.edu.task.service.entity.Task;
import pl.kt.agh.edu.task.service.mapper.SubtaskMapper;
import pl.kt.agh.edu.task.service.mapper.TaskMapper;
import pl.kt.agh.edu.task.service.repository.SubtaskRepository;
import pl.kt.agh.edu.task.service.repository.TaskRepository;
import pl.kt.agh.model.dto.CarDTO;
import pl.kt.agh.model.dto.SubtaskDTO;
import pl.kt.agh.model.dto.TaskDTO;
import pl.kt.agh.model.enums.Role;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String GET_CARS_API = "https://car-service/car/all";
    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final TaskMapper taskMapper;
    private final SubtaskMapper subtaskMapper;
    private final SecurityContextConverter securityContextConverter;
    private final RestTemplate restTemplate;

    public List<TaskDTO> getTasks() {
        String roleStr = securityContextConverter.resolveSecurityContextClaim(String.class, SecurityContextConverter.ROLE_CLAIM);
        switch (Role.valueOf(roleStr)) {
            case CLIENT -> {
                CarDTO[] carDTOS = restTemplate.exchange(createGetCarByIdRequest(), CarDTO[].class).getBody();
                if (carDTOS != null) {
                    List<Long> carIds = Arrays.stream(carDTOS).sequential().map(CarDTO::getCid).collect(Collectors.toList());
                    return taskRepository.findAllByCidIn(carIds).stream().map(taskMapper::toDto).collect(Collectors.toList());
                }
                return Collections.emptyList();
            }
            case MECHANIC -> {
                return taskRepository.findAllByUidIsNull().stream().map(taskMapper::toDto).collect(Collectors.toList());
            }
        }
        LOGGER.warn("Cannot resolve role: {}", roleStr);
        throw new IllegalArgumentException("Cannot resolve role");
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        if (task.getCreated() == null) {
            task.setCreated(LocalDateTime.now());
        }

        if (task.getEnd_date() == null) {
            task.setEnd_date(LocalDateTime.now().plusDays(7));
        }
        return taskMapper.toDto(taskRepository.save(task));
    }

    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) {
        Subtask subtask = subtaskMapper.toEntity(subtaskDTO);
        if (subtask.getCreated() == null) {
            subtask.setCreated(LocalDateTime.now());
        }

        return subtaskMapper.toDto(subtaskRepository.save(subtask));
    }

    public List<SubtaskDTO> getSubtasks(SubtaskDTO subtaskDTO) {
        return subtaskRepository.findAllByTid(subtaskDTO.getTid()).stream().map(subtaskMapper::toDto).collect(Collectors.toList());
    }

    public SubtaskDTO updateSubtask(SubtaskDTO subtaskDTO) {
        Subtask oldSubtask = subtaskRepository.findBySid(subtaskDTO.getSid());
        subtaskDTO.setCreated(oldSubtask.getCreated());
        oldSubtask = subtaskMapper.toEntity(subtaskDTO);

        return subtaskMapper.toDto(subtaskRepository.save(oldSubtask));
    }

    private RequestEntity<Void> createGetCarByIdRequest() {
        String jwt = securityContextConverter.unwrapSecurityToken();
        String header = BEARER_PREFIX + jwt;
        return RequestEntity.get(URI.create(GET_CARS_API))
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, header)
                .build();
    }

    public TaskDTO assignTask(Long tid) {
        String roleStr = securityContextConverter.resolveSecurityContextClaim(String.class, SecurityContextConverter.ROLE_CLAIM);
        if (Role.valueOf(roleStr) != Role.MECHANIC){
            LOGGER.warn("Only user with role mechanic can assign task!");
            throw new IllegalArgumentException("Only user with role mechanic can assign task!");
        }
        Optional<Task> taskOpt = taskRepository.findById(tid);
        if (taskOpt.isEmpty()){
            LOGGER.warn("Cannot find task with id: {}", tid);
            throw new IllegalArgumentException("Cannot find task with id: " + tid);
        }

        long uid = securityContextConverter.resolveSecurityContextClaim(Number.class, SecurityContextConverter.ID_CLAIM).longValue();
        taskOpt.get().setUid(uid);
        Task savedTask = taskRepository.save(taskOpt.get());
        return taskMapper.toDto(savedTask);
    }
}
