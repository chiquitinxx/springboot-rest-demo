package conversion

import org.grooscript.GrooScript
import org.grooscript.test.JsTestResult
import org.grooscript.util.GrooScriptException
import javax.script.Bindings
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * User: jorgefrancoleza
 * Date: 15/03/14
 */
class GrooscriptConverter {

    ConvertedCode convertCode(String groovyCode) {
        ConvertedCode convertedCode = new ConvertedCode(groovyCode: groovyCode)

        try {
            GrooScript.clearAllOptions()
            GrooScript.setConversionProperty('initialText', 'gs.consoleOutput = false;')
            GrooScript.setConversionProperty('finalText', 'var gSfails = gs.fails;var gSconsole = gs.consoleData;')
            GrooScript.setConversionProperty('includeJsLib', 'grooscript.min')
            String jsCode = GrooScript.convert(groovyCode)
            JsTestResult result = evalJs(jsCode)
            convertedCode.jsCode = jsCode.substring(
                jsCode.indexOf('gs.consoleOutput = false;') + 26,
                jsCode.indexOf('var gSfails = gs.fails;') - 1
            )
            convertedCode.console = result.console
            convertedCode.assertFails = result.assertFails
        } catch (e) {
            println '**** Error conversion: ' + e.message
            convertedCode.errorMessage = e.message
        }

        return convertedCode
    }

    JsTestResult evalJs(finalScript) {
        def testResult = new JsTestResult()

        try {

            //Load script manager
            ScriptEngineManager factory = new ScriptEngineManager()
            ScriptEngine engine = factory.getEngineByName('JavaScript')
            if (!engine) {
                throw new GrooScriptException('Not engine available!')
            }
            Bindings bind = engine.createBindings()
            //Run javascript script
            engine.eval(finalScript, bind)

            //Put binding result to resultMap
            if (bind) {
                bind.each { testResult.bind.putAt(it.key, it.value) }
            }
            testResult.jsScript = finalScript
            testResult.assertFails = testResult.bind.gSfails
            testResult.console = testResult.bind.gSconsole

        } catch (e) {
            throw new GrooScriptException("Fail evaluating Js Script! - ${e.message}")
        }

        testResult
    }
}