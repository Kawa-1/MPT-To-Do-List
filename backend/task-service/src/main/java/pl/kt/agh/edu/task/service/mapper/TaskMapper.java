package pl.kt.agh.edu.task.service.mapper;

import org.mapstruct.Mapper;
import pl.kt.agh.edu.task.service.entity.Task;
import pl.kt.agh.model.dto.TaskDTO;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDto(Task task);

    Task toEntity(TaskDTO taskDTO);
}
