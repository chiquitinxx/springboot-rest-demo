package game

import spock.lang.Specification
import spock.lang.Unroll

class GameSpec extends Specification {
    
    Game game = new Game()

    def 'init ok'() {
        expect:
        game.rows.size() == game.size
        game.columns.size() == game.size
        game.cells.size() == game.total
        game.cells.count { it.value } == game.initialNumbers
        !game.isDone()
        !game.isFull()
    }

    @Unroll
    def 'process line'() {
        given:
        def line = []

        when:
        values.eachWithIndex { value, i ->
            line[i] = new Cell(value: value)
        }
        game.processLine(line)

        then:
        line.collect { it.value } == expectedValues

        where:
        values	                 | expectedValues
        [null, null, null, null] | [null, null, null, null]
        [null, null, null, 2]    | [2, null, null, null]
        [null, 4, null, 2]       | [4, 2, null, null]
        [2, null, 4, 4]          | [2, 8, null, null]
        [2, 2, 4, 4]             | [4, 8, null, null]
    }

    def 'move right'() {
        given:
        def values = [null, 2, null, 2,
                      4, 2, null, 8,
                      null, 2, null, null,
                      4, 4, null, null]
        def expectedValues = [null, null, null, 4,
                              null, 4,    2,    8,
                              null, null, null, 2,
                              null, null, null, 8]

        when:
        game.cells.eachWithIndex { Cell cell, int i ->
            cell.value = values[i]
        }
        game.moveRight()

        then:
        game.cells.collect { it.value } == expectedValues
    }

    def 'move down'() {
        given:
        def values = [null, 2, null, 2,
                      4, 2, null, 8,
                      null, 2, null, null,
                      4, 4, null, null]
        def expectedValues = [null, null, null, null,
                              null, 2, null, null,
                              null, 4, null, 2,
                              8,    4, null, 8]

        when:
        game.cells.eachWithIndex { Cell cell, int i ->
            cell.value = values[i]
        }
        game.moveDown()

        then:
        game.cells.collect { it.value } == expectedValues
    }
}
