package pl.kt.agh.edu.car.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kt.agh.edu.car.service.entity.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUid(Long uid);
}
