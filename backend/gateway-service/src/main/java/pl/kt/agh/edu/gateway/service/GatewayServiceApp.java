package pl.kt.agh.edu.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApp {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(GatewayServiceApp.class);
        log.info("test");
        SpringApplication.run(GatewayServiceApp.class);
    }
}
