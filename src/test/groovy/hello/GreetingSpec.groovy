package hello

import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 23/02/14
 */
class GreetingSpec extends Specification {

    def 'test inmutability'() {
        given:
        def greeting = new Greeting(1, 'Content')

        expect:
        greeting

        when:
        greeting.content = 'otherContent'

        then:
        thrown(ReadOnlyPropertyException)
    }
}
