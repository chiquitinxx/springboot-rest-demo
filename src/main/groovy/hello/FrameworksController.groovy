package hello

import framework.model.Framework
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import javax.sql.DataSource

@Controller
@EnableAutoConfiguration
@RequestMapping("/frameworks")
public class FrameworksController {

    JdbcTemplate jdbcTemplate

    @Autowired
    public FrameworksController(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource)
    }

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody List<Framework> allFramworks() {
        return jdbcTemplate.queryForList('Select * from FRAMEWORKS').collect {
            [name: it.NAME, url: it.URL, urlImage: it.URL_IMAGE]
        }
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Framework insertFramework(@RequestParam(value="name", required=true) String name,
                                         @RequestParam(value="url", required=true) String url,
                                         @RequestParam(value="urlImage", required=false) String urlImage) {
        def framework = new Framework(name: name, url: url, urlImage: urlImage)
        jdbcTemplate.update("insert into FRAMEWORKS values " +
                "('${framework.name}', '${framework.url}', '${framework.urlImage?:''}');")
        return framework
    }
}
