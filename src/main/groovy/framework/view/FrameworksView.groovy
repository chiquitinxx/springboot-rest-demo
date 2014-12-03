package framework.view

import framework.model.Framework
import org.grooscript.asts.GsNative
import org.grooscript.builder.HtmlBuilder

/**
 * Created by jorge on 01/03/14.
 */
class FrameworksView {

    @GsNative
    void putHtml(String selector, String html) {/*
        $(selector).html(html);
        AniJS.run();
    */}

    def updateFrameworks(List frameworks) {
        def html = HtmlBuilder.build {
            ul {
                frameworks.each { Framework framework ->
                    li(alt: framework.description) {
                        div(class: 'logo', 'data-anijs': 'if: mouseenter, do: flip animated') {
                            if (!framework.hasImage() && framework.isGithub()) {
                                img src: 'img/github.png'
                            } else {
                                img src: framework.hasImage() ? framework.urlImage : 'img/nologo.png'
                            }
                        }
                        p {
                            a (href: framework.url, framework.name)
                        }
                    }
                }
            }
        }

        putHtml('#listFrameworks', html)
    }
}
