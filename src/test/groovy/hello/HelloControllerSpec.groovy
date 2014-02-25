package hello

import org.springframework.test.web.servlet.MockMvc

import spock.lang.Specification

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * User: jorgefrancoleza
 * Date: 23/02/14
 */
class HelloControllerSpec extends Specification {
    MockMvc mockMvc

    def setup() {
        mockMvc = standaloneSetup(new HelloController()).build()
    }
    def 'hello controller result'() {
        when:
        def response = mockMvc.perform(get('/hello'))

        then:
        response.andExpect(status().isOk())
                .andExpect(content().string('Greetings from Spring Boot!'))
    }
}
