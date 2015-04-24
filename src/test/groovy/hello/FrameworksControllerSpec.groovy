package hello

import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

/**
 * User: jorgefrancoleza
 * Date: 25/02/14
 */
class FrameworksControllerSpec extends Specification {

    private MockMvc mockMvc
    private FrameworksController frameworksController
    private FrameworkRepository repository = Mock(FrameworkRepository)

    void setup() {
        frameworksController = new FrameworksController()
        frameworksController.repository = repository
        mockMvc = standaloneSetup(frameworksController).build()
    }

    def 'get frameworks list'() {
        when:
        def response = mockMvc.perform(get('/frameworks'))

        then:
        1 * repository.findAll() >> [[NAME: 'Grails', URL: 'grails.org', URL_IMAGE: 'image']]
        1 * repository.count() >> frameworksController.initialFrameworks.size()
        response.andExpect(status().isOk())
                .andExpect(content().string('[{"NAME":"Grails","URL":"grails.org","URL_IMAGE":"image"}]'))
        0 * _
    }
}
