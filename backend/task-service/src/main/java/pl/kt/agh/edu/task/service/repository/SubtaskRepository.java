package pl.kt.agh.edu.task.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kt.agh.edu.task.service.entity.Subtask;

import java.util.List;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
    List<Subtask> findAllByTid(Long tid);
    Subtask findBySid(Long sid);
}
