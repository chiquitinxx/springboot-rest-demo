package templating

import groovy.text.Template
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import org.junit.Ignore
import spock.lang.Specification

/**
 * Created by jorge on 31/07/14.
 */
@Ignore
class GroovyTemplatesSpec extends Specification {

    def 'test hello template'() {
        when:
        def result = renderGroovyTemplate('src/main/resources/templates/views/hello.tpl',
                [groovyVersion: '2.4', twitter: 'jfrancoleza'])

        then:
        result == '<!DOCTYPE html><html><head><title>Hello</title></head>' +
                '<body><p>Hello, groovy version is 2.4!</p><p>@jfrancoleza</p></body></html>'
    }

    def 'test dom manipulation'() {
        given:
        def html = renderGroovyTemplate('src/main/resources/templates/views/hello.tpl', [groovyVersion: '2.4'])

        when:
        def result = JQueryEvaluator.evaluate (html) {
            $('body').append('<p>Hello2</p>')
        }

        then:
        result.endsWith '<p>Hello2</p>\n </body>\n</html>'
    }

    private renderGroovyTemplate(pathToFile, model) {
        TemplateConfiguration config = new TemplateConfiguration()
        MarkupTemplateEngine engine = new MarkupTemplateEngine(config)
        Template template = engine.createTemplate(new File(pathToFile).text)
        template.make(model).toString()
    }
}
