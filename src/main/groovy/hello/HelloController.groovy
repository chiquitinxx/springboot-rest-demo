package hello

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
public class HelloController {

    @RequestMapping("/hello")
    String index() {
        return "Greetings from Spring Boot!"
    }

}
