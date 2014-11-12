package templating

import groovy.text.Template
import groovy.text.markup.MarkupTemplateEngine
import groovy.text.markup.TemplateConfiguration
import org.junit.Ignore
import spock.lang.Specification

/**
 * Created by jorge on 31/07/14.
 */
class GroovyTemplatesSpec extends Specification {

    def 'test hello template'() {
        when:
        def result = renderGroovyTemplate(html, [groovyVersion: '2.4'])

        then:
        result == '<!DOCTYPE html><html><head><title>Hello</title></head>' +
                '<body><p>Hello, groovy version is 2.4!</p></body></html>'
    }

    def 'test dom manipulation'() {
        given:
        def html = renderGroovyTemplate(html, [groovyVersion: '2.4'])

        when:
        def result = JQueryEvaluator.evaluate (html) {
            $('body').append('<p>Hello2</p>')
        }

        then:
        result.endsWith '<p>Hello2</p>\n </body>\n</html>'
    }

    private renderGroovyTemplate(htmlCode, model) {
        TemplateConfiguration config = new TemplateConfiguration()
        MarkupTemplateEngine engine = new MarkupTemplateEngine(config)
        Template template = engine.createTemplate(htmlCode)
        template.make(model).toString()
    }

    private html = '''
yieldUnescaped '<!DOCTYPE html>'
html {
  head {
    title 'Hello'
  }
  body {
    p "Hello, groovy version is $groovyVersion!"
  }
}'''
}
