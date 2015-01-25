package robot

/**
 * JFL 27/10/12
 */

class Robot {

    def String name
    def data

    def east = Direction.east
    def west = Direction.west
    def north = Direction.north
    def south = Direction.south

    //Not accesibles
    def boolean rXActive
    def int rXMaxShootDistance
    def rXController
    def int rXTemperature
    def Position rXPosition
    def int rXLife
    def Position rXMaxPosition
    def Map rXRadar
    def Position rXShoot
    def Direction rXMove
    def rXColor
    def rXColorName

    Robot(name) {
        this.name = name
        this.rXActive = false
    }

    def move(Direction direction) {
        if (direction!=null) {
            this.rXMove = direction
        }
    }

    def distance(position1,position2) {
        if (position1 && position2) {
            def total = Math.sqrt( Math.pow(position1.x - position2.x,2) + Math.pow(position1.y - position2.y,2))
            return total
        } else {
            return 0
        }
    }

    def canShoot(x, y) {
        if (rXPosition) {
            def pos = new Position()
            pos.x = x
            pos.y = y
            def total = distance(rXPosition,pos)
            return (total<= rXMaxShootDistance)
        } else {
            return false
        }
    }

    def shoot(x, y) {
        this.rXShoot = new Position(x: x,y: y)
    }

    def cancelShoot() {
        this.rXShoot = null
    }

    def getRadar() {
        return rXRadar
    }

    def getLife() {
		def life = rXLife
        return life
    }

    def getTemperature() {
		def temperature = rXTemperature
        return temperature
    }

    def getPosition() {
		if (rXPosition) {
			return new Position(x:rXPosition.x,y:rXPosition.y)
		} else {
        	return new Position(x:0,y:0)
		}
    }

    def getMaxPosition() {
		if (rXMaxPosition) {
			return new Position(x:rXMaxPosition.x,y:rXMaxPosition.y)
		} else {
        	return new Position(x:0,y:0)
		}
    }

    def isCrashed() {
        return rXPosition.x >= rXMaxPosition.x || rXPosition.y >= rXMaxPosition.y ||
                rXPosition.x <=0 || rXPosition.y <=0

    }

    def isDestroyed() {
        return rXLife <=0
    }

    def isExploding() {
        return rXTemperature>=100
    }


}
