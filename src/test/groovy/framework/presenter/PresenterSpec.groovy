package framework.presenter

import framework.model.Framework
import framework.model.FrameworksModel
import spock.lang.Specification
import framework.view.FrameworksView
import spock.lang.Unroll

/**
 * User: jorgefrancoleza
 * Date: 25/02/14
 */
class PresenterSpec extends Specification {

    Presenter presenter
    FrameworksView view = Mock(FrameworksView)
    FrameworksModel model = Mock(FrameworksModel)

    def setup() {
        presenter = new Presenter(view: view, model: model)
    }

    def 'update frameworks'() {
        when:
        presenter.updateFrameworksList(list)

        then:
        1 * view.updateFrameworks(list)
        presenter.frameworks == list

        where:
        list << [[], [new Framework()]]
    }

    def 'init presenter on load'() {
        when:
        presenter.onLoad()

        then:
        1 * model.loadFrameworks(presenter.updateFrameworksList)
        presenter.frameworks == []
    }
}
