package view

import model.Framework
import org.grooscript.builder.Builder

/**
 * Created by jorge on 01/03/14.
 */
class FrameworksView {

    def updateFrameworks(List frameworks) {
        def html = Builder.build {
            ul {
                frameworks.each { Framework framework ->
                    li {
                        img src: framework.urlImage
                        a (href: framework.url, framework.name)
                    }
                }
            }
        }
        $('#listFrameworks').html(html)
    }
}
