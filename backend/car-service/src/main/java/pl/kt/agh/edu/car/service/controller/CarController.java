package pl.kt.agh.edu.car.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kt.agh.edu.car.service.service.CarService;
import pl.kt.agh.model.dto.CarDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/car/all")
    public List<CarDTO> getCars() {
        return carService.getCars();
    }

    @PostMapping("/car/create")
    public CarDTO createCar(CarDTO carDTO) {
        return carService.createCar(carDTO);
    }

}
