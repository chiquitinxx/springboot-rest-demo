package robot

import org.grooscript.GrooScript
/**
 * JFL 01/11/12
 */
class ConvertActions {

    static final BEGIN = '''
   class Robot {

    def name
    def data

    def east
    def west
    def north
    def south

    //Not accesibles
    def rXActive
    def rXMaxShootDistance
    def rXController
    def rXTemperature
    def rXPosition
    def rXLife
    def rXMaxPosition
    def rXRadar
    def rXShoot
    def rXMove
    def rXColor
    def rXColorName

    def actions() {
'''

    static final END = '}}'

    static String convert(actions) {

        def result = ''
        try {
            String text = GrooScript.convert(BEGIN + actions + END)
            //println '*************************'
            //println text
            //println '*************************'

            def begin = text.indexOf('gSobject[\'actions\'] = ') + 40
            def end = text.indexOf('if (arguments.length == 1)') - 7
            text = text.substring(begin, end).trim()
            if (text.startsWith('return')) {
                text = text.substring(7)
            }
            //println 'Text before:'+text
            text = text.replaceAll('this','gSobject')

            result =  text
        } catch (e) {
            throw e
        }
        return result
    }
}
