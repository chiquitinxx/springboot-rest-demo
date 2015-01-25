package robot

class RobotService {

	private static NUMBER_STAR = 0

    Map checkDsl(name, actions) {
	
		def newData
		def exception
	
		//First we check actions DSL
		def canDo = CheckRobotActions.checkActions(actions)
	
		if (canDo) {
			//Laters we check can convert to javascript
			try {
				newData = ConvertActions.convert(actions)
			} catch (e) {
				exception = e.message.substring(0, 100)
			}
		} else {
			exception = "Problem with the DSL. Something in your code is wrong (${CheckRobotActions.messageError?.substring(0,100)})."
		}
	
	
		def finalResult = [:]
		finalResult.exception = exception
		finalResult.data = newData
		finalResult.name = name

		return finalResult
	}

	Map addSdiRobot() {
		checkDsl('sdi-' + NUMBER_STAR++, SDI_ROBOT)
	}

	static SDI_ROBOT = '''
def rotateLeft = { dir ->
   if (dir == north)
       return west
   else if (dir == west)
       return south
   else if (dir == south)
       return east
   else if (dir == east)
       return north
   return null
}
def rotateRight = { dir ->
   if (dir == north)
       return east
   else if (dir == west)
       return north
   else if (dir == south)
       return west
   else if (dir == east)
       return south
   return null
}
def randomizeDirection = {
   if (!data.direction)
       data.direction = east
   def random = new Random()
   def r = random.nextInt(2)
   if (r == 0)
       data.direction = rotateRight(data.direction)
   else
       data.direction = rotateLeft(data.direction)
   data.duration = 50 + random.nextInt(100)
}
def isWithinBoundaries = { pos ->
   def maxPos = getMaxPosition()
   def margin = 5
   return pos.x > margin && pos.x < maxPos.x - margin &&
           pos.y > margin && pos.y < maxPos.y - margin
}
def checkBoundaries = { pos ->
   def maxPos = getMaxPosition()
   def margin = 5
   if (pos.x < margin)
       data.direction = east
   else if (pos.x > maxPos.x - margin)
       data.direction = west
   if (pos.y < margin)
       data.direction = south
   else if (pos.y > maxPos.y - margin)
       data.direction = north
}
def analyzeRadar = {
   def radar = getRadar()
   def copy = [:]
   radar.each { k, pos ->
       copy[k] = [x: pos.x, y: pos.y]
   }
   assert copy == radar
   data.radar = copy
}
def calcNewPosition = {
   def pos = getPosition()
   pos = [x: pos.x, y: pos.y]
   if (data.direction == west)
       pos.x -= data.duration
   else if (data.direction == east)
       pos.x += data.duration
   else if (data.direction == north)
       pos.y -= data.duration
   else if (data.direction == south)
       pos.y += data.duration
   return pos
}
def calcDirection = { enemy ->
   def dir = null
   def prevRadar = data.radar
   if (prevRadar != null) {
       def radar = getRadar()
       def pos = radar[enemy]
       def prevPos = prevRadar[name]
       if (pos != null && prevPos != null) {
           def dx = pos.x - prevPos.x
           def dy = pos.y - prevPos.y
           if (dx < 0)
               dir = west
           else if (dx > 0)
               dir = east
           else if (dy < 0)
               dir = north
           else if (dy > 0)
               dir = south
       }
   }
   return dir
}
def evade = { enemy ->
   def dir = calcDirection(enemy)
   if (dir != null)
       dir = rotateRight(dir)
   else {
       dir = rotateRight(data.direction)
       if (dir == null)
           dir = east
   }
   data.direction = dir
   data.duration = 75
   int i = 3
   while (i > 0) {
       def pos = calcNewPosition()
       if (isWithinBoundaries(pos))
           break
       data.direction = rotateRight(data.direction)
       i--
   }
}
def calcRoot = { a, b, c ->
   def root = null
   def D = b * b - 4 * a * c
   if (D >= 0) {
       def Dr = Math.sqrt(D)
       root = (0-b - Dr) / (2 * a)
       if (D > 0) {
           def root2 = (0-b + Dr) / (2 * a)
           if (root < 0) {
               if (root2 > 0) {
                   root = root2
               }
           } else if (root2 > 0)
               root = Math.min(root, root2)
       }
   }
   return root
}
def calcShootingPosition = { name, position ->
   def pos = [x: position.x, y: position.y]
   def prevRadar = data.radar
   if (prevRadar != null) {
       def prevPos = prevRadar[name]
       if (prevPos != null) {
           def dx = pos.x - prevPos.x
           def dy = pos.y - prevPos.y
           if (dx != 0 || dy != 0) {
               def myPos = getPosition()
               def cx = Math.abs(pos.x - myPos.x)
               def cy = Math.abs(pos.y - myPos.y)
               if (dx != 0) {
                   def offset = calcRoot(8, 2 * cx, 0-(cx * cx + cy * cy))
                   if (offset > 0)
                       pos.x += offset * dx
               }
               else {
                   def offset = calcRoot(8, 2 * cy, 0-(cx * cx + cy * cy))
                   if (offset > 0)
                       pos.y += offset * dy
               }
           }
       }
   }
   return pos
}
def hunt = {
   def oldEnemy = data.enemy
   data.enemy = null
   def ep = null
   def en = null
   def radar = getRadar()
   def temp = getTemperature()
   def maxDistance = 999
   radar.each { name, position ->
       def dist = distance(getPosition(), position)
       if (dist && dist < maxDistance) {
           ep = position
           en = data.enemy
           def pos = calcShootingPosition(name, position)
           if (canShoot(pos.x, pos.y)) {
               data.enemy = name
               maxDistance = dist
               if (temp < 75) {
                   if (temp % 2 == 0)
                       shoot pos.x, pos.y
                   else
                       shoot position.x, position.y
               }
           }
       }
   }
   if (!data.enemy) {
       if (ep) {
           def pos = getPosition()
           def cx = ep.x - pos.x
           def cy = ep.y - pos.y
           def acx = Math.abs(cx)
           def acy = Math.abs(cy)
           if (acx > acy) {
               def dir = cx > 0 ? east : west
               if (calcDirection(en) != dir) {
                   data.direction = dir
                   data.duration = acx - 1
               }
           }
           else {
               def dir = cy > 0 ? south : north
               if (calcDirection(en) != dir) {
                   data.direction = dir
                   data.duration = acy - 1
               }
           }
       }
   }
   else if (oldEnemy && data.enemy != oldEnemy)
       data.duration = -1
}

if (!data.started) {
   data.started = true
   data.direction = east
   data.duration = 1
   move data.direction
} else {
   if (data.duration < 0) {
       def enemy = data.enemy
       if (enemy) {
           evade(enemy)
       }
       else {
           randomizeDirection()
       }
   }
   checkBoundaries(getPosition())
   move data.direction
   data.duration--

}

hunt()
analyzeRadar()
'''
}
