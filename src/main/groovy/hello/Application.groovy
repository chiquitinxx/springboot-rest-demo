package hello

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class)
        //app.addInitializers(new MyInitializer())
        //app.setShowBanner(false)
        //ConfigurableApplicationContext ctx =
        app.run(args)

        println '************************** Running.....'
        println '**** go /conversion to do conversions'
    }
}

/*class MyInitializer implements ApplicationContextInitializer {

    void initialize(ConfigurableApplicationContext applicationContext) {
        def reader = new GroovyBeanDefinitionReader(applicationContext)
        reader.beans {
            grooscriptConverter(GrooscriptConverter)
        }
    }
}*/