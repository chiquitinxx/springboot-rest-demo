package presenter

import model.Framework
import model.FrameworksModel
import org.codehaus.groovy.runtime.MethodClosure
import spock.lang.Specification
import view.FrameworksView

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
        1 * model.loadFrameworks(_ as MethodClosure)
        presenter.frameworks == []
        presenter.nameFramework == ''
        presenter.urlFramework == ''
        presenter.urlImageFramework == ''
    }

    def 'on button add new framework'() {
        when:
        presenter.nameFramework = 'name'
        presenter.urlFramework = 'url'
        presenter.urlImageFramework = 'urlImage'
        presenter.buttonAddFrameworkClick()

        then:
        1 * model.addFramework('name', 'url', 'urlImage', _ as MethodClosure)
        !presenter.frameworks
    }

    def 'on add new framework'() {
        given:
        def framework = new Framework(name: 'name')

        when:
        presenter.addNewFrameworkToList(framework)

        then:
        presenter.frameworks == [framework]
        1 * view.updateFrameworks(presenter.frameworks)
    }
}
