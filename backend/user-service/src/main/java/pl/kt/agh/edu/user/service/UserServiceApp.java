package pl.kt.agh.edu.user.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import pl.kt.agh.model.entity.User;

@SpringBootApplication
@EntityScan(basePackageClasses = User.class)
public class UserServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}
