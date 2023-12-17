package pl.kt.agh.edu.task.service.mapper;

import org.mapstruct.Mapper;

import pl.kt.agh.edu.task.service.entity.Subtask;
import pl.kt.agh.model.dto.SubtaskDTO;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {
    SubtaskDTO toDto(Subtask task);

    Subtask toEntity(SubtaskDTO taskDTO);
}
