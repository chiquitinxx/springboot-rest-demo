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

    def onLoad() {
        frameworks = []
        model.loadFrameworks(updateFrameworksList)
    }

    def start() {
        view = new FrameworksView()
        model = new FrameworksModel()
    }

    def updateFrameworksList = { List<Framework> newFrameworks ->
        frameworks = newFrameworks
        view.updateFrameworks(newFrameworks)
    }
}
