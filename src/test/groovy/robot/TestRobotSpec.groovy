package robot

import spock.lang.Specification

/**
 * JFL 27/10/12
 */
class TestRobotSpec extends Specification {

    static final NAME_ROBOT = 'George'
    static final BORDER = 100

    def 'create robot'() {
        when:
        def robot = new Robot(NAME_ROBOT)

        then:
        robot
        robot.name == NAME_ROBOT
        !robot.rXActive
    }

    def 'robot possible deaths'() {
        given:
        def robot = new Robot(NAME_ROBOT)
        robot.rXMaxPosition = new Position(x: BORDER,y: BORDER)

        when:
        robot.rXTemperature = temperature

        then:
        robot.isExploding() == failTemperature

        when:
        robot.rXPosition = position

        then:
        robot.isCrashed() == failDirection

        when:
        robot.rXLife = life

        then:
        robot.isDestroyed() == failLife

        where:
        temperature << [100,50]
        failTemperature << [true,false]
        position << [new Position(x: BORDER/2,y: BORDER/2),new Position(x: BORDER,y: BORDER)]
        failDirection << [false,true]
        life << [75,0]
        failLife << [false,true]
    }
}
