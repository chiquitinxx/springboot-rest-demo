package hello

import conversion.ConvertedCode
import conversion.GrooscriptConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@RestController
public class HelloController {

    @Autowired
    GrooscriptConverter grooscriptConverter

    @RequestMapping("/hello")
    ModelAndView index() {
        return new ModelAndView(
            "views/hello",
            [groovyVersion: GroovySystem.version, twitter: 'jfrancoleza'])
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
