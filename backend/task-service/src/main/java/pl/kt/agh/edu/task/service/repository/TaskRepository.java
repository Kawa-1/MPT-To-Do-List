package pl.kt.agh.edu.task.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kt.agh.edu.task.service.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUid(Long uid);
    List<Task> findAllByUidIsNull();
    List<Task> findAllByCidIn(List<Long> carId);
}
