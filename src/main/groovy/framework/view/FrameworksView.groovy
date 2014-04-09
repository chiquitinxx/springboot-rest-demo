package framework.view

import framework.model.Framework
import org.grooscript.asts.GsNative
import org.grooscript.builder.Builder

/**
 * Created by jorge on 01/03/14.
 */
class FrameworksView {

    @GsNative
    def setHtml(String selector, String html) {/*
        $(selector).html(html);
    */}

    def updateFrameworks(List frameworks) {
        def html = Builder.build {
            ul {
                frameworks.each { Framework framework ->
                    li {
                        div(class: 'logo') {
                            img src: framework.hasImage() ? framework.urlImage : 'img/nologo.png'
                        }
                        a (href: framework.url, framework.name)
                    }
                }
            }
        }

        setHtml('#listFrameworks', html)
    }

    def validationError(List messages) {
        setHtml('#validationError', messages.join(' - '))
    }
}
