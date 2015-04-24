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
import robot.RobotService

@RestController
public class HelloController {

    @Autowired
    GrooscriptConverter grooscriptConverter

    @Autowired
    RobotService robotService

    @RequestMapping(value="/conversion", method=RequestMethod.GET)
    ModelAndView conversion() {
        return new ModelAndView('/conversions.html')
    }

    @RequestMapping(value="/conversion", method=RequestMethod.POST)
    public @ResponseBody ConvertedCode convertCode(@RequestParam(value="groovyCode", required=true) String groovyCode) {
        return grooscriptConverter.convertCode(groovyCode)
    }

    @RequestMapping(value="/checkRobot", method=RequestMethod.POST)
    public @ResponseBody Map checkRobot(@RequestParam(value="name", required=true) String name,
                                        @RequestParam(value="actions", required=true) String actions) {
        return robotService.checkDsl(name, actions)
    }

    @RequestMapping(value="/sdiRobot", method=RequestMethod.POST)
    public @ResponseBody Map addSdiRobot() {
        return robotService.addSdiRobot()
    }
}
