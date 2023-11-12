package discovery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApp {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(DiscoveryServiceApp.class);
        log.info("test");
        SpringApplication.run(DiscoveryServiceApp.class, args);
    }
}
