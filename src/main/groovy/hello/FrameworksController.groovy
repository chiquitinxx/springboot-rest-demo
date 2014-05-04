package hello

import framework.model.Framework
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/frameworks")
public class FrameworksController {

    @Autowired
    FrameworkRepository repository

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody List<Framework> allFrameworks() {
        repository.findAll()
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Framework insertFramework(@ModelAttribute("framework") Framework framework) {
        repository.save(framework)
        simpMessagingTemplate.convertAndSend("/topic/newFramework", framework)
        framework
    }
}
