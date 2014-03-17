package conversion

import org.grooscript.convert.GsConverter
import org.grooscript.test.TestJs
import org.springframework.stereotype.Component

/**
 * User: jorgefrancoleza
 * Date: 15/03/14
 */
@Component
class GrooscriptConverter {

    ConvertedCode convertCode(String groovyCode) {
        ConvertedCode convertedCode = new ConvertedCode(groovyCode: groovyCode)

        GsConverter gsConverter = new GsConverter()
        try {
            convertedCode.jsCode = gsConverter.toJs(groovyCode)

            File jsFile = grooscriptJsFile
            String finalJsCode ='\ngs.consoleOutput = false;\n' + convertedCode.jsCode +
                '\nvar gSfails = gs.fails;var gSconsole = gs.consoleData;\n'
            Map result = TestJs.jsEval(finalJsCode, null, jsFile)
            convertedCode.console = result.gSconsole
            convertedCode.assertFails = result.gSfails
        } catch (e) {
            println '**** Error conversion: ' + e.message
            convertedCode.errorMessage = e.message
        }

        return convertedCode
    }

    private getGrooscriptJsFile() {
        File finalFile = new File('src/main/webapp/js/lib/grooscript.js')

        //if (!finalFile || !finalFile.exists() || !finalFile.isFile()) {
        //    finalFile = new File('js/lib/grooscript.js')
        //}
        finalFile
    }
}
