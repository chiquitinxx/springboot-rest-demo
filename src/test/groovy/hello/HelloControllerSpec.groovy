package hello

import conversion.ConvertedCode
import conversion.GrooscriptConverter
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
    GrooscriptConverter grooscriptConverter

    def setup() {
        grooscriptConverter = Mock(GrooscriptConverter)
        mockMvc = standaloneSetup(
                new HelloController(grooscriptConverter: grooscriptConverter)).build()
    }

    def 'hello controller index'() {
        when:
        def response = mockMvc.perform(get('/hello'))

        then:
        response.andExpect(status().isOk())
                .andExpect(model().attribute('groovyVersion', '2.4.0'))
                .andExpect(model().attribute('twitter', 'jfrancoleza'))
                .andExpect(view().name('views/hello'))
    }

    def 'hello controller conversion'() {
        when:
        def response = mockMvc.perform(get('/conversion'))

        then:
        response.andExpect(status().isOk())
                .andExpect(view().name('/conversions.html'))
    }

    def 'do a conversion of code'() {
        when:
        def response = mockMvc.perform(post('/conversion').
                param('groovyCode', 'code'))

        then:
        1 * grooscriptConverter.convertCode('code') >>
                new ConvertedCode(groovyCode: 'groovy', jsCode: 'js', errorMessage: 'error', console: 'console')
        response.andExpect(status().isOk())
                .andExpect(content().string('{"groovyCode":"groovy","jsCode":"js","console":"console",' +
                '"errorMessage":"error","assertFails":false}'))
    }
}
