package framework.presenter

/**
 * Created by jorge on 14/06/14.
 */
class MyIf {
    def _if(eval) {
        def result = evaluation(eval)
        [and: and.rcurry(result), then: then.rcurry(result)]
    }

    Closure and = { eval, previousResult ->
        def result = previousResult && evaluation(eval)
        [and: and.rcurry(result), then: then.rcurry(result)]
    }

    Closure then = { closure, result ->
        if (result) {
            closure()
        }
        [otherwise: otherwise.rcurry(result)]
    }

    Closure otherwise = { closure, result ->
        if (!result) {
            closure()
        }
    }

    protected evaluation = { value ->
        value instanceof Closure ? value() : value
    }
}
