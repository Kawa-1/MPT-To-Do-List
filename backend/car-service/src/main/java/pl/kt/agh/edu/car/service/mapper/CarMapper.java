package pl.kt.agh.edu.car.service.mapper;

import org.mapstruct.Mapper;
import pl.kt.agh.edu.car.service.entity.Car;
import pl.kt.agh.model.dto.CarDTO;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO toDto(Car car);

    Car toEntity(CarDTO carDTO);
}
