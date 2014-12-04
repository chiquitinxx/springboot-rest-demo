package conversion

import org.grooscript.asts.GsNative
import org.grooscript.builder.HtmlBuilder
import org.grooscript.jquery.GQuery
import org.grooscript.jquery.GQueryImpl

/**
 * Created by jorge on 03/12/14.
 */
class ConversionPresenter {

    GQuery gQuery = new GQueryImpl()
    def resultDiv = gQuery('#results')
    def url
    def groovyEditor

    void init(String initUrl = '', conversionButtonId, clearButtonId) {
        initEditor()
        clearResults()
        url = initUrl
        gQuery(conversionButtonId).click this.&doConversion
        gQuery(clearButtonId).click this.&clearEditor
        groovyEditor.setValue '''//List
def max = [1, 3, 8].collect { it * 2 }.max()
println "Maximum double is: $max"
assert max == 16

//Traits example
trait FlyingAbility {
  String fly() { "I'm flying!" }
}

class Bird implements FlyingAbility {}
def b = new Bird()
println b.fly()
assert b.fly() == "I'm flying!"'''
    }

    void doConversion() {
        clearResults()
        gQuery.doRemoteCall(url, 'POST', [groovyCode: groovyEditor.getValue()], { ConvertedCode convertedCode ->
            if (convertedCode.hasErrors()) {
                resultInfo('errorMessage', convertedCode.errorMessage ?: '' + convertedCode.console ?: '')
            } else {
                resultInfo('consoleMessage', convertedCode.console ?: '')
            }
            if (convertedCode.jsCode)
                resultDiv.append('<pre>'+convertedCode.jsCode +'</pre>')
        }, { error ->
            resultInfo('errorMessage', "Error: ${error}")
        }, ConvertedCode)
    }

    @GsNative
    private initEditor() {/*
        this.groovyEditor = ace.edit("editor");
        this.groovyEditor.setTheme("ace/theme/textmate");
        this.groovyEditor.getSession().setMode("ace/mode/groovy");
    */}

    private clearResults() {
        resultDiv.html ''
    }

    void clearEditor() {
        groovyEditor.setValue ''
        clearResults()
        gQuery(groovyEditor).focus()
    }

    private resultInfo(nameClass, code) {
        resultDiv.html HtmlBuilder.build {
            pre(class: nameClass) {
                yieldUnescaped code
            }
        }
    }
}
