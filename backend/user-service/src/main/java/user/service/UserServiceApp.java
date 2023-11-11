package user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApp {
    public static void main(String[] args) {
        System.setProperty("service-name", "user_service");
        SpringApplication.run(UserServiceApp.class, args);
    }
}
