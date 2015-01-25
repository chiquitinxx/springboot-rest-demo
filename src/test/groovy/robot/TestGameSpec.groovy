package robot

import spock.lang.Specification

/**
 * JFL 27/10/12
 */
class TestGameSpec extends Specification {

    static final NAME_ROBOT1 = 'Robot1'
    static final NAME_ROBOT2 = 'Robot2'
    static final DEFAULT_DIRECTION = Direction.north
    static final DEFAULT_POSITION = new Position(x: 50, y: 50)
    static final DEFAULT_POSITION_CLOSE = new Position(x: 75, y: 75)
    static final DEFAULT_POSITION_FAR = new Position(x: 575, y: 575)
    static final INITIAL_ROBOTS = 2

    def 'create a game'() {

        when:
        def game = new RobotGame()

        then:
        notThrown(Exception)
        game

    }

    def 'start a game'() {

        when:
        def game = startedGame()

        then:
        game.maxSize == game.DEFAULT_SIZE
        game.speedRatio == game.DEFAULT_SPEED_RATIO
        game.radarDistance == game.DEFAULT_RADAR_DISTANCE
        game.shootDistance == game.DEFAULT_SHOOT_DISTANCE
        game.sameTime == game.MIN_ROBOTS
        game.players.size() == INITIAL_ROBOTS
        //Robot0
        game.players[0].rXActive == true
        game.players[0].rXLife == game.MAX_LIFE
        game.players[0].rXMove == null
        game.players[0].rXTemperature == 0
        game.players[0].rXShoot == null
        game.players[0].rXMaxPosition == game.maxSize
        game.players[0].rXMaxShootDistance == game.shootDistance
        game.players[0].rXPosition.x >= game.MINIMUM_DISTANCE_BORDERS &&
                game.players[0].rXPosition.x < game.DEFAULT_SIZE.x - game.MINIMUM_DISTANCE_BORDERS
        game.players[0].rXPosition.y >= game.MINIMUM_DISTANCE_BORDERS &&
                game.players[0].rXPosition.y < game.DEFAULT_SIZE.y - game.MINIMUM_DISTANCE_BORDERS
        game.players[0].data == [:]
        game.checkDistances(game.players[0]).min() > game.shootDistance
        //Robot1
        game.players[1].rXActive == true
        game.players[1].rXLife == game.MAX_LIFE
        game.players[1].rXMove == null
        game.players[1].rXTemperature == 0
        game.players[1].rXShoot == null
        game.players[1].rXMaxPosition == game.maxSize
        game.players[1].rXMaxShootDistance == game.shootDistance
        game.players[1].rXPosition.x >= game.MINIMUM_DISTANCE_BORDERS &&
                game.players[1].rXPosition.x < game.DEFAULT_SIZE.x - game.MINIMUM_DISTANCE_BORDERS
        game.players[1].rXPosition.y >= game.MINIMUM_DISTANCE_BORDERS &&
                game.players[1].rXPosition.y < game.DEFAULT_SIZE.y - game.MINIMUM_DISTANCE_BORDERS
        game.players[1].data == [:]
        game.checkDistances(game.players[1]).min() > game.shootDistance
        game.stats.size() == INITIAL_ROBOTS
    }

    def startedGame() {
        def robot1 = new Robot(NAME_ROBOT1)
        def robot2 = new Robot(NAME_ROBOT2)
        robot1.metaClass.actions = { move north }
        robot2.metaClass.actions = { shoot rXPosition.x+1,rXPosition.y+1 }
        //def listRobots = [robot1,robot2]
        def game = new RobotGame()
        game.addRobotToGame(robot1)
        game.addRobotToGame(robot2)
        game.start()
        return game
    }

    def 'process actions of robots'() {

        when:
        def robot1 = new Robot(NAME_ROBOT1)
        robot1.metaClass.actions = closure
        def robot2 = new Robot(NAME_ROBOT2)
        robot2.metaClass.actions = { it -> }
        //def listRobots = [robot1,robot2]
        def game = new RobotGame()
        game.addRobotToGame(robot1)
        game.addRobotToGame(robot2)

        game.start()
        robot1.rXPosition = DEFAULT_POSITION
        robot2.rXPosition = positionOtherRobot
        game.processRadars()
        game.processActions()

        then:
        robot1."$property" == value

        where:
        property        | value                             | closure
        'rXMove'        | null                              | { }
        'rXMove'        | DEFAULT_DIRECTION                 | { move DEFAULT_DIRECTION }
        'rXShoot'       | null                              | { }
        'rXShoot'       | DEFAULT_POSITION                  | { shoot DEFAULT_POSITION.x, DEFAULT_POSITION.y }
        'rXShoot'       | null                              | { shoot DEFAULT_POSITION.x, DEFAULT_POSITION.y; cancelShoot() }
        'data'          | false                             | { data = canShoot DEFAULT_POSITION_FAR.x, DEFAULT_POSITION_FAR.y }
        'data'          | true                              | { data = canShoot DEFAULT_POSITION_CLOSE.x, DEFAULT_POSITION_CLOSE.y }
        'rXRadar'       | ['Robot2':DEFAULT_POSITION_CLOSE] | { }
        //Now change position of the other robot to far
        'rXRadar'       | [:]                               | { }

        positionOtherRobot << [DEFAULT_POSITION_CLOSE,DEFAULT_POSITION_CLOSE,DEFAULT_POSITION_CLOSE,DEFAULT_POSITION_CLOSE,
                DEFAULT_POSITION_CLOSE, DEFAULT_POSITION_CLOSE, DEFAULT_POSITION_CLOSE, DEFAULT_POSITION_CLOSE,
                DEFAULT_POSITION_FAR]

    }

    def 'process a full cycle with lame robots'() {
        given:
        def game = startedGame()
        def robot1x = game.players[0].rXPosition.x
        def robot1y = game.players[0].rXPosition.y

        when:
        game.processRadars()
        game.processActions()
        game.addRobotShoots()
        def shoots = game.shoots.size()
        game.processShoots(1)
        game.processHotRobots()
        game.moveRobots()
        game.processUselessRobots()
        game.processShoots(2)
        game.processShoots(3)
        game.cleanTurn()

        then:
        game
        game.players.size() == INITIAL_ROBOTS
        game.players[0].rXActive
        game.players[1].rXActive
        shoots == 1
        game.shoots.size() == 0
        game.players[0].rXPosition.x == robot1x
        game.players[0].rXPosition.y == robot1y - 1
        game.players[1].rXTemperature == game.SHOOT_TEMPERATURE
        game.players[1].rXLife == game.MAX_LIFE - game.IMPACT_DAMAGE
        game.toString() == ' Robot1 Deaths:0 Ks:0 Robot2 Deaths:0 Ks:0'
        game.players[0].rXColor == 'rgb(255,0,0)'
        game.players[1].rXColor == 'rgb(0,0,255)'
    }

    def 'converting actions'() {
        given:
        def actions = 'move east'

        when:
        def result = ConvertActions.convert(actions)

        then:
        result == 'gs.mc(gSobject,"move",[gSobject.east], gSobject);'
    }

    def 'converting complex actions'() {
        given:
        def actions = '''
            if (getRadar() && getTemperature()<60 &&getPosition()) {
                def pos = getRadar().values()[0]
                shoot pos.x, pos.y
            }
            data.numberShoots = (data.numberShoots?data.numberShoots:0) + 1
            def a = new Random().nextInt(4)
            if (a==0) move north
            if (a==1) move south
            if (a==2) move east
            if (a==3) move west

'''

        when:
        def result = ConvertActions.convert(actions)

        then:
        //println result
        result
    }
}
