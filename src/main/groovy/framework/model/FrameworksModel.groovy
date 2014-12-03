package framework.model

import org.grooscript.jquery.GQuery
import org.grooscript.jquery.GQueryImpl

/**
 * Created by jorge on 01/03/14.
 */
class FrameworksModel {

    GQuery gQuery = new GQueryImpl()

    def loadFrameworks(Closure onLoaded) {
        gQuery.doRemoteCall('/frameworks', 'GET', null, onLoaded, null, Framework)
    }
}
