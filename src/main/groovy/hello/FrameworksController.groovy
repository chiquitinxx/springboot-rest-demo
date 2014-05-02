package hello

import framework.model.Framework
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import javax.sql.DataSource

@Controller
@RequestMapping("/frameworks")
public class FrameworksController {

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody List<Framework> allFramworks() {
        return jdbcTemplate.queryForList('Select * from FRAMEWORKS').collect {
            [name: it.NAME, url: it.URL, urlImage: it.URL_IMAGE]
        }
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Framework insertFramework(@ModelAttribute("framework") Framework framework) {
        jdbcTemplate.update("insert into FRAMEWORKS values " +
                "('${framework.name}', '${framework.url}', '${framework.urlImage?:''}');")
        simpMessagingTemplate.convertAndSend("/topic/newFramework", framework)
        return framework
    }
}
