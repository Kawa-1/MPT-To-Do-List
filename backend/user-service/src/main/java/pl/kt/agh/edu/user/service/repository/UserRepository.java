package pl.kt.agh.edu.user.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.kt.agh.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginAndPassword(String username, String password);

    User findByLogin(String user);
}
