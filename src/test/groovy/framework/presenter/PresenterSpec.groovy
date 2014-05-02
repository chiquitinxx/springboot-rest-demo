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
        1 * view.validationError({ it.size() == numberErrors})
        !presenter.frameworks

        where:
        name    | url        | urlImage |numberErrors
        null    | null       | null     |2
        ''      | ''         | ''       |2
        'name'  | ''         | null     |1
        'name'  | 'htt://'   | null     |1
        'name'  | 'http://<' | null     |1
        'name<' | 'https://' | null     |1
        'name'  | 'https://' | '<'      |2
        'name'  | 'ht'       | '<'      |3
    }

    def 'on button add new framework'() {
        when:
        presenter.nameFramework = 'name'
        presenter.urlFramework = 'http://url'
        presenter.urlImageFramework = 'https://urlImage'
        presenter.buttonAddFrameworkClick()

        then:
        1 * model.addFramework('name', 'http://url', 'https://urlImage', presenter.addNewFrameworkToList)
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

    def 'can\t add a framework already in the list'() {
        given:
        def framework = new Framework(name: 'name')
        presenter.frameworks = [framework]

        when:
        presenter.addNewFrameworkToList(framework)

        then:
        presenter.frameworks == [framework]
        0 * _
    }

    def 'test _if'() {
        given:
        def a = 0

        when:
        presenter._if(true).
                and(true).
                and(false).
                then({ a = 1}).
                otherwise({ a = 2})

        then:
        a == 2
    }
}
