package hello

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import javax.sql.DataSource

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

/**
 * User: jorgefrancoleza
 * Date: 25/02/14
 */
class GreetingsControllerSpec extends Specification {

    private MockMvc mockMvc
    private GreetingsController greetingsController = new GreetingsController(Mock(DataSource))
    private JdbcTemplate jdbcTemplate = Mock(JdbcTemplate)

    void setup() {
        greetingsController.jdbcTemplate = jdbcTemplate
        mockMvc = standaloneSetup(greetingsController).build()
    }

    def 'get greetings list'() {
        when:
        def response = mockMvc.perform(get('/greetings'))

        then:
        1 * jdbcTemplate.queryForList('Select * from GREETINGS') >> [[ID: 1, CONTENT:'content']]
        response.andExpect(status().isOk())
                .andExpect(content().string('[{"ID":1,"CONTENT":"content"}]'))
    }

    def 'post a stranger in no name passed'() {
        when:
        def response = mockMvc.perform(post('/greetings'))

        then:
        1 * jdbcTemplate.update("insert into GREETINGS values (1, 'Hello, Stranger!');")
        response.andExpect(status().isOk())
                .andExpect(content().string('{"id":1,"content":"Hello, Stranger!"}'))
    }

    def 'post a new greeting with name param'() {
        when:
        def response = mockMvc.perform(post('/greetings').param('name', 'Jorge'))

        then:
        1 * jdbcTemplate.update("insert into GREETINGS values (1, 'Hello, Jorge!');")
        response.andExpect(status().isOk())
                .andExpect(content().string('{"id":1,"content":"Hello, Jorge!"}'))
    }
}
