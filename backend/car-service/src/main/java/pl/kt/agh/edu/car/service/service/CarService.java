package pl.kt.agh.edu.car.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kt.agh.edu.car.service.entity.Car;
import pl.kt.agh.edu.car.service.mapper.CarMapper;
import pl.kt.agh.edu.car.service.repository.CarRepository;
import pl.kt.agh.edu.common.security.SecurityContextConverter;
import pl.kt.agh.model.dto.CarDTO;

import java.util.List;
import java.util.stream.Collectors;

import static pl.kt.agh.edu.common.security.SecurityContextConverter.ID_CLAIM;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final SecurityContextConverter securityContextConverter;

    public List<CarDTO> getCars() {
        Long userId = securityContextConverter.resolveSecurityContextClaim(Number.class, ID_CLAIM).longValue();
        return carRepository.findAllByUid(userId)
                .stream()
                .map(carMapper::toDto)
                .collect(Collectors.toList());
    }

    public CarDTO createCar(CarDTO carDTO) {
        Long userId = securityContextConverter.resolveSecurityContextClaim(Long.class, ID_CLAIM);
        Car car = carMapper.toEntity(carDTO);
        car.setUid(userId);
        return carMapper.toDto(carRepository.save(car));
    }

}
