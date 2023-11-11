package user.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    static Logger log = LoggerFactory.getLogger(Controller.class);

    @GetMapping(path = "test")
    public String test() {
        log.info("test");
        return "siema";
    }
}
