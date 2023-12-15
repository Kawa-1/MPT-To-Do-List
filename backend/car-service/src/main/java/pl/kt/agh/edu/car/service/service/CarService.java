package pl.kt.agh.edu.car.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kt.agh.edu.car.service.entity.Car;
import pl.kt.agh.edu.car.service.mapper.CarMapper;
import pl.kt.agh.edu.car.service.repository.CarRepository;
import pl.kt.agh.model.dto.CarDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public List<CarDTO> getCars() {
        return carRepository.findAll().stream().map(carMapper::toDto).collect(Collectors.toList());
    }

    public CarDTO createCar(CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        return carMapper.toDto(carRepository.save(car));
    }

}
