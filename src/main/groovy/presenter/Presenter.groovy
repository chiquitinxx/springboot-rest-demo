package presenter

import model.Framework

/**
 * User: jorgefrancoleza
 * Date: 25/02/14
 */
class Presenter {

    def view
    def model

    List<Framework> frameworks = []
    String nameFramework
    String urlFramework
    String urlImageFramework

    def onLoad() {
        frameworks = []
        nameFramework = ''
        urlFramework = ''
        urlImageFramework = ''
        model.loadFrameworks(this.&updateFrameworksList)
    }

    def buttonAddFrameworkClick() {
        model.addFramework(nameFramework, urlFramework, urlImageFramework, this.&addNewFrameworkToList)
    }

    def updateFrameworksList(List<Framework> newFrameworks) {
        frameworks = newFrameworks
        view.updateFrameworks(newFrameworks)
    }

    def addNewFrameworkToList(Framework framework) {
        frameworks << framework
        view.updateFrameworks(frameworks)
    }
}
