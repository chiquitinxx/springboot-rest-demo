package framework.view

import framework.model.Framework
import spock.lang.Specification

/**
 * User: jorgefrancoleza
 * Date: 08/03/14
 */
class FrameworksViewSpec extends Specification {

    private static final ERROR = 'error'
    private static final NAME = 'aName'
    private static final URL = 'aUrl'
    private static final URL_IMAGE = 'aUrlImage.jpg'
    private FrameworksView view = new FrameworksView()
    private String htmlResult

    def setup() {
        htmlResult = ''
        view.metaClass.putHtml = { String selector, String html ->
            if (selector == '#listFrameworks') {
                htmlResult = html
            } else {
                htmlResult = ERROR
            }
        }
    }

    def 'get empty list'() {
        when:
        view.updateFrameworks([])

        then:
        htmlResult == '<ul></ul>'
    }

    def 'get list of frameworks'() {
        when:
        view.updateFrameworks([new Framework(name: NAME, url: URL, urlImage: URL_IMAGE)])

        then:
        htmlResult == '<ul><li><div class=\'logo\' data-anijs=\'if: mouseenter, do: flip animated\'><img src=\'aUrlImage.jpg\'></img></div><a href=\'aUrl\'>aName</a></li></ul>'
    }
}
