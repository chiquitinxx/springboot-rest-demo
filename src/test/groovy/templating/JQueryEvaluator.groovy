package templating

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by jorge on 31/07/14.
 */
class JQueryEvaluator {

    Document document

    def $(String selector) {
        document.select(selector)
    }

    String htmlCode() {
        document.outerHtml()
    }

    static String evaluate(String html, Closure closure) {
        def evaluator = new JQueryEvaluator(document: Jsoup.parse(html))
        closure.delegate = evaluator
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure()
        evaluator.htmlCode()
    }
}
