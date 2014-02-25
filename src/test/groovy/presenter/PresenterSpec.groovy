package presenter

import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 25/02/14
 */
class PresenterSpec extends Specification {

    Presenter presenter = new Presenter()

    def 'init greetings on load'() {
        when:
        presenter.onLoad()

        then:
        1 * presenter.jquery.ajax('localhost:9000/greetings', presenter.&loadGreetings)
    }
}
