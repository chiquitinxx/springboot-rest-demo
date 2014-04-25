package hello

import conversion.ConvertedCode
import conversion.GrooscriptConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@RestController
@ComponentScan("conversion")
public class HelloController {

    @Autowired
    GrooscriptConverter grooscriptConverter

    @RequestMapping("/hello")
    String index() {
        return "Greetings from Spring Boot!"
    }

    @RequestMapping("/conversion")
    ModelAndView conversion() {
        return new ModelAndView('/conversions.html')
    }

    @RequestMapping(value="/conversion", method=RequestMethod.POST)
    public @ResponseBody ConvertedCode convertCode(@RequestParam(value="groovyCode", required=true) String groovyCode) {
        return grooscriptConverter.convertCode(groovyCode)
    }
}
