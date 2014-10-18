package conversion

import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 15/03/14
 */
class GrooscriptConverterSpec extends Specification {

    private static final GROOVY_CORRECT_CODE = 'def a = 5'
    private static final GROOVY_INCORRECT_CODE = 'def a = \'hello'
    private static final GROOVY_CONSOLE_CODE = "println 'Hello world!'"
    private static final GROOVY_ASSERT_CODE = "assert 1 == 2"

    GrooscriptConverter grooscriptConverter = new GrooscriptConverter()

    def 'test correct conversion'() {
        when:
        ConvertedCode result = grooscriptConverter.convertCode(GROOVY_CORRECT_CODE)

        then:
        result.errorMessage == ''
        result.groovyCode == GROOVY_CORRECT_CODE
        result.jsCode == 'var a = 5;\n'
        result.console == ''
        result.assertFails == false
    }

    def 'test incorrect conversion'() {
        when:
        ConvertedCode result = grooscriptConverter.convertCode(GROOVY_INCORRECT_CODE)

        then:
        result.errorMessage.startsWith 'Compiler ERROR on Script'
        result.groovyCode == GROOVY_INCORRECT_CODE
        result.jsCode == ''
        result.console == ''
        result.assertFails == false
    }

    def 'test correct with console message'() {
        when:
        ConvertedCode result = grooscriptConverter.convertCode(GROOVY_CONSOLE_CODE)

        then:
        result.errorMessage == ''
        result.groovyCode == GROOVY_CONSOLE_CODE
        result.jsCode == 'gs.println("Hello world!");\n'
        result.console == 'Hello world!'
        result.assertFails == false
    }

    def 'test assert fail'() {
        when:
        ConvertedCode result = grooscriptConverter.convertCode(GROOVY_ASSERT_CODE)

        then:
        result.errorMessage == ''
        result.groovyCode == GROOVY_ASSERT_CODE
        result.jsCode == 'gs.assert(gs.equals(1, 2), "Assertion fails: (1 == 2)");\n'
        result.console == 'Assertion fails: (1 == 2) - false'
        result.assertFails == true
    }
}
