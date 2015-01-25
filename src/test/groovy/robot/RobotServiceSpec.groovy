package robot

import spock.lang.Specification

class RobotServiceSpec extends Specification {

	void 'test convert robot dsl'() {
		when:
		def robot = 'move north'
		def result = service.checkDsl(NAME,robot)
		
		then:
		result
		!result.exception
		result.name == NAME
		result.data == 'gs.mc(gSobject,"move",[gSobject.north], gSobject);'
	}

	void 'add sdi robot'() {
		when:
		def result = service.addSdiRobot()
		//println result.data

		then:
		result
		!result.exception
		result.name == 'sdi-0'
		result.data
	}

	private static NAME = 'name'
	private service = new RobotService()
}
