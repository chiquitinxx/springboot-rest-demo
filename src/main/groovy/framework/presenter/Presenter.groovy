package framework.presenter

import framework.model.Framework
import framework.model.FrameworksModel
import framework.view.FrameworksView

/**
 * User: jorgefrancoleza
 * Date: 25/02/14
 */
class Presenter {

    FrameworksView view
    FrameworksModel model

    List<Framework> frameworks = []
    String nameFramework
    String urlFramework
    String urlImageFramework

    def onLoad() {
        frameworks = []
        nameFramework = ''
        urlFramework = ''
        urlImageFramework = ''
        model.loadFrameworks(updateFrameworksList)
    }

    boolean validateFramework() {
        nameFramework && urlFramework &&
                ['http://', 'https://'].any { urlFramework.startsWith it } &&
                ![nameFramework, urlFramework, urlImageFramework].any { it && it.indexOf('<') >= 0}
    }

    def buttonAddFrameworkClick() {
        view.validationError('')
        if (validateFramework()) {
            model.addFramework(nameFramework, urlFramework, urlImageFramework, addNewFrameworkToList)
        } else {
            view.validationError('No, no, no!')
        }
    }

    def updateFrameworksList = { List<Framework> newFrameworks ->
        frameworks = newFrameworks
        view.updateFrameworks(newFrameworks)
    }

    def addNewFrameworkToList = { Framework framework ->
        frameworks << framework
        view.updateFrameworks(frameworks)
    }
}
