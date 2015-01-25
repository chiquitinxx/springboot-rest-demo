package hello

import conversion.GrooscriptConverter
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import robot.RobotService

@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class)
        app.run(args)

        println '************************** Running.....'
        println '**** go /conversion to do conversions'
    }

    @Bean
    GrooscriptConverter grooscriptConverter() {
        new GrooscriptConverter()
    }

    @Bean
    RobotService robotService() {
        new RobotService()
    }
}