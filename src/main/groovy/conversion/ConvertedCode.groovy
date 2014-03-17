package conversion

/**
 * User: jorgefrancoleza
 * Date: 15/03/14
 */
class ConvertedCode {

    String groovyCode = ''
    String jsCode = ''
    String console = ''
    String errorMessage = ''
    boolean assertFails = false

    boolean hasErrors() {
        !jsCode || errorMessage || assertFails
    }
}
