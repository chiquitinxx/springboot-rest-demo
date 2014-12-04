package conversion

import org.grooscript.GrooScript
import org.grooscript.test.JsTestResult

/**
 * User: jorgefrancoleza
 * Date: 15/03/14
 */
class GrooscriptConverter {

    ConvertedCode convertCode(String groovyCode) {
        ConvertedCode convertedCode = new ConvertedCode(groovyCode: groovyCode)

        try {
            JsTestResult result = GrooScript.evaluateGroovyCode(groovyCode.trim())
            convertedCode.jsCode = result.jsCode
            convertedCode.console = result.console
            convertedCode.assertFails = result.assertFails
        } catch (e) {
            convertedCode.errorMessage = e.message
        }

        return convertedCode
    }
}