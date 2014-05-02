package framework

import framework.model.Framework
import hello.FrameworksController
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.messaging.simp.SimpMessagingTemplate
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
class FrameworksControllerSpec extends Specification {

    private MockMvc mockMvc
    private SimpMessagingTemplate messagingTemplate = Mock(SimpMessagingTemplate)
    private FrameworksController frameworksController
    private JdbcTemplate jdbcTemplate = Mock(JdbcTemplate)

    void setup() {
        frameworksController = new FrameworksController()
        frameworksController.simpMessagingTemplate = messagingTemplate
        frameworksController.jdbcTemplate = jdbcTemplate
        mockMvc = standaloneSetup(frameworksController).build()
    }

    def 'get frameworks list'() {
        when:
        def response = mockMvc.perform(get('/frameworks'))

        then:
        1 * jdbcTemplate.queryForList('Select * from FRAMEWORKS') >> [[NAME: 'Grails', URL: 'grails.org', URL_IMAGE: 'image']]
        response.andExpect(status().isOk())
                .andExpect(content().string('[{"name":"Grails","url":"grails.org","urlImage":"image"}]'))
        0 * _
    }

    def 'post a new framework'() {
        when:
        def response = mockMvc.perform(post('/frameworks').
                param('name', 'Spock').
                param('url', 'spock.org').
                param('urlImage', 'image'))

        then:
        1 * jdbcTemplate.update("insert into FRAMEWORKS values ('Spock', 'spock.org', 'image');")
        response.andExpect(status().isOk())
                .andExpect(content().string('{"name":"Spock","url":"spock.org","urlImage":"image"}'))
        1 * messagingTemplate.convertAndSend('/topic/newFramework', new Framework(
                name: 'Spock', url: 'spock.org', urlImage: 'image'
        ))
        0 * _
    }
}
