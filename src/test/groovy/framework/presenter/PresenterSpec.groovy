package framework.presenter

import framework.model.Framework
import framework.model.FrameworksModel
import org.codehaus.groovy.runtime.MethodClosure
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
        presenter.nameFramework == ''
        presenter.urlFramework == ''
        presenter.urlImageFramework == ''
    }

    @Unroll
    def 'on button add with incorrect params [name: #name, url: #url, urlImage: #urlImage]'() {
        when:
        presenter.nameFramework = name
        presenter.urlFramework = url
        presenter.urlImageFramework = urlImage
        presenter.buttonAddFrameworkClick()

        then:
        1 * view.validationError('No, no, no!')
        !presenter.frameworks

        where:
        name    | url        | urlImage
        null    | null       | null
        ''      | ''         | ''
        'name'  | ''         | null
        'name'  | 'htt://'   | null
        'name'  | 'http://<' | null
        'name<' | 'https://' | null
        'name'  | 'https://' | '<'
    }

    def 'on button add new framework'() {
        when:
        presenter.nameFramework = 'name'
        presenter.urlFramework = 'http://url'
        presenter.urlImageFramework = 'urlImage'
        presenter.buttonAddFrameworkClick()

        then:
        1 * model.addFramework('name', 'http://url', 'urlImage', presenter.addNewFrameworkToList)
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
