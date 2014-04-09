package hello

import conversion.GrooscriptConverter
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class)
        app.addInitializers(new MyInitializer())
        //app.setShowBanner(false)
        ConfigurableApplicationContext ctx = app.run(args)

        println '************************** Running.....'
        println '**** go /conversion to do conversions'
    }
}

class MyInitializer implements ApplicationContextInitializer {

    void initialize(ConfigurableApplicationContext applicationContext) {
        def reader = new GroovyBeanDefinitionReader(applicationContext)
        reader.beans {
            grooscriptConverter(GrooscriptConverter)
        }
    }
}