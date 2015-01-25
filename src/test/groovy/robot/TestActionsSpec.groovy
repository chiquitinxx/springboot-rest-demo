package robot

import spock.lang.Specification
import spock.lang.Unroll

/**
 * JFL 27/10/12
 */
class TestActionsSpec extends Specification {

	def 'can move'(){
        expect:
        canDo == CheckRobotActions.checkActions(action)

        where:
        canDo   |   action
        true    |   'move west'
        true    |   'move north'
        true    |   'move south'
        true    |   'move east'
        false   |   'move 3'
        false   |   'move 3.5'
    }

    def 'cant access basic stuff'(){
        expect:
        canDo == CheckRobotActions.checkActions(action)

        where:
        canDo   |   action
        false   |   'metaClass'
        false   |   'println "f"'
        false   |   'new File("h")'
        false   |   'System.exit(0)'
    }

    def 'cant access secure properties'(){
        expect:
        canDo == CheckRobotActions.checkActions(action)

        where:
        canDo   |   action
        false   |   'rXController=5'
        false   |   'setrXController(5)'
        false   |   'metaClass.rXController = 5'
        false   |   'this.getMetaClass.rXController = 5'
        false   |   'setProperty("rXController",5)'
		false   |   'def aa=rXController;setProperty("aa",5)'
    }

	@Unroll('canDo -> #canDo action -> #action')
	def 'can access data map, radar data, position, maximums, temperature and life'() {
        expect:
        canDo == CheckRobotActions.checkActions(action)

        where:
        canDo   |   action
        true    |   'data=5'
        true    |   'getRadar()'
        true    |   'getTemperature()'
        true    |   'getLife()'
        true    |   'getPosition()'
        true    |   'getMaxPosition()'
        false   |   'rXRadar=5'
        false   |   'rXTemperature=5'
        false   |   'rXLife=5'
        false   |   'rXPosition=5'
        false   |   'rXMaxPosition=5'

    }

    def 'can shoot or check it'() {
        expect:
        canDo == CheckRobotActions.checkActions(action)

        where:
        canDo   |   action
        true    |   'canShoot 6,7'
        true    |   'shoot 9,23'
        true    |   'cancelShoot()'
        false   |   'canShoot "hola"'
        false   |   'shoot 6.35'

    }

    def 'process full robot'() {

        def actions = '''
//Example robot
if (data.direction) {
  if (getPosition().x<5) {
    data.direction = east
  }
  if (getPosition().x>(getMaxPosition().x-5)) {
    data.direction = west
  }
  move data.direction
} else {
  data.direction = east
}
move data.direction
if (getTemperature()<70) {
  def radar = getRadar()
  def maxDistance = 999
  if (radar.size()>0) {
    radar.each { name,position ->
      def distan = distance getPosition(),position
      if (canShoot(position.x,position.y) && distan < maxDistance) {
         maxDistance = distan
         //Only will do last shoot
         shoot position.x,position.y
      }
    }
  }
}
'''
        expect:
        CheckRobotActions.checkActions(actions)

    }
}
