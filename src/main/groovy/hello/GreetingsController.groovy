package hello

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.jdbc.core.JdbcTemplate

import javax.sql.DataSource
import java.util.concurrent.atomic.AtomicLong

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@EnableAutoConfiguration
@RequestMapping("/greetings")
public class GreetingsController {

    private final AtomicLong counter = new AtomicLong()
    JdbcTemplate jdbcTemplate

    @Autowired
    public GreetingsController(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource)
    }

    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody List<Greeting> allGreetings() {
        return jdbcTemplate.queryForList('Select * from GREETINGS')
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Greeting insert(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        def greeting = new Greeting(counter.incrementAndGet(), "Hello, ${name}!")
        jdbcTemplate.update("insert into GREETINGS values (${greeting.id}, '${greeting.content}');")
        return greeting
    }
}
