package robot
/**
 * JFL 27/10/12
 */
class RobotGame {

    static final MIN_ROBOTS = 2
    static final MAX_ROBOTS = 8
    static final MAX_LIFE = 100
    static final DEFAULT_SPEED_RATIO = 3
    static final DEFAULT_RADAR_DISTANCE = 250
    static final DEFAULT_SHOOT_DISTANCE = 150
    static final DEFAULT_SIZE = new Position(x: 800,y: 600)
    static final MINIMUM_DISTANCE_BORDERS = 5
    static final SHOOT_TEMPERATURE = 24
    static final REST_TEMPERATURE = 5
    static final IMPACT_DISTANCE = 4
    static final IMPACT_DAMAGE = 30
    static final COLLISION_DISTANCE = 3

    def robots
    def sameTime = MIN_ROBOTS
    def maxSize = DEFAULT_SIZE
    def speedRatio = DEFAULT_SPEED_RATIO
    def radarDistance = DEFAULT_RADAR_DISTANCE
    def shootDistance = DEFAULT_SHOOT_DISTANCE
    def players
    def shoots = []
    def stats
    def colors
    def colorNames
    def usedColor

    /**
     * Init game with a list of robots in
     */
    RobotGame() {
        //if (listRobots==null || listRobots.size() < MIN_ROBOTS || listRobots.size()>MAX_ROBOTS) {
        //    throw new Exception("Wrong number of robots, must be $MIN_ROBOTS-$MAX_ROBOTS")
        //}
        robots = []
        colors = []
        colorNames = []
        stats = [:]
        players = []

        /*
        colors.put('red','rgb(255,0,0)')
        colors.put('blue','rgb(0,0,255)')
        colors.put('green','rgb(0,255,0)')
        colors.put('purple','rgb(255,0,255)')
        colors.put('cyan','rgb(0,255,255)')
        colors.put('orange','rgb(255,128,0)')
        colors.put('yellow','rgb(255,255,0)')
        colors.put('black','rgb(0,0,0)')
        */
        colorNames << 'red'
        colorNames << 'blue'
        colorNames << 'green'
        colorNames << 'purple'
        colorNames << 'cyan'
        colorNames << 'orange'
        colorNames << 'yellow'
        colorNames << 'black'

        colors << 'rgb(255,0,0)'
        colors << 'rgb(0,0,255)'
        colors << 'rgb(0,255,0)'
        colors << 'rgb(255,0,255)'
        colors << 'rgb(0,255,255)'
        colors << 'rgb(255,128,0)'
        colors << 'rgb(255,255,0)'
        colors << 'rgb(0,0,0)'

        //colors.put('white','rgb(255,255,255)')
        usedColor = 0
    }

    /**
     * Get a random position where can put a new robot, inside limits
     * @return
     */
    def getRandomForStart() {
        def r = new Random()
        def x = r.nextInt(maxSize.x-2*MINIMUM_DISTANCE_BORDERS) + MINIMUM_DISTANCE_BORDERS
        def y = r.nextInt(maxSize.y-2*MINIMUM_DISTANCE_BORDERS) + MINIMUM_DISTANCE_BORDERS
        return new Position(x: x, y: y)
    }

    /**
     * Join a robot to battle
     * @param robot
     * @return
     */
    def joinRobotToBattle(robot) {
        robot.rXPosition = getRandomForStart()
        if (players.size()>0) {
            while (checkDistances(robot).min()<shootDistance) {
                robot.rXPosition = getRandomForStart()
            }
        }
        robot.rXActive = true
        robot.rXLife = MAX_LIFE
        robot.rXTemperature = 0
        robot.rXMaxPosition = maxSize
        robot.rXMaxShootDistance = shootDistance
        robot.data = [:]
        if (!robot.rXColor) {
            robot.rXColorName = colorNames[usedColor]
            robot.rXColor = colors[usedColor++]
        }
        players << robot
    }

    /**
     * Start game
     */
    def start() {
        if (robots.size()>=sameTime) {
            sameTime.times {
                //Init players and stats
                int i = 0
                while (players.size()<sameTime) {
                    def Robot robot = robots[i++]
                    joinRobotToBattle(robot)
                }
            }
        } else {
            throw new Exception("Can't start game with less than ${sameTime} players.")
        }
    }

    /**
     * Join a random robot to the battle
     * @param robot
     * @return
     */
    def joinRandomRobotToBattle() {

        def added = false
        while (!added && robots.size() > players.size()) {
            def number = new Random().nextInt(robots.size())
            def robot = robots[number]
            if (!players.find { it -> it.name == robot.name }) {
                joinRobotToBattle(robot)
                added = true
            }
        }
        //if (!players.find { it -> it.name == robot.name}) {
        //    robots << robot
        //}
    }

    /**
     * Distance between 2 positions
     * @param pos1
     * @param pos2
     * @return
     */
    def distance(Position pos1,Position pos2) {
        def total = Math.sqrt( Math.pow(pos1.x - pos2.x,2) + Math.pow(pos1.y - pos2.y,2))
        return total
    }

    /**
     * List of distance from this robot with rest
     * @param robot
     * @return
     */
    def checkDistances(Robot robot) {
        def result = []
        players.each { Robot player ->
            if (robot.name!=player.name) {
                result << distance(robot.rXPosition,player.rXPosition)
            }
        }

        return result
    }

    /**
     * Fill robots radar info with data in range of radar
     * @return
     */
    def processRadars() {
        players.each { Robot player ->
            def map = [:]
			players.each { Robot otherPlayer ->
                //println 'Try!'
                if (otherPlayer.rXActive && otherPlayer.name!=player.name && (distance(otherPlayer.rXPosition,player.rXPosition)<radarDistance)) {
                    //println '  Add!'
					def clonedPos = new Position(x:otherPlayer.rXPosition.x,y:otherPlayer.rXPosition.y)
                    map.put(otherPlayer.name,clonedPos)
					//map.put(otherPlayer.name,otherPlayer.rXPosition)
					//map.put(otherPlayer.name,new Position(otherPlayer.rXPosition.x,otherPlayer.rXPosition.y))
                }
            }
            //println "  Size ${player.name}:${map.size()}"
            player.rXRadar = map
        }

    }

    /**
     * Process actions of the robots
     * @return
     */
    def processActions() {
        players.each {  Robot robot ->
            if (robot.rXActive) {
                robot.actions(robot)
            }
        }
    }

    /**
     * Add shoots done in actions to list of shoots
     * @return
     */
    def addRobotShoots() {
        players.each {  Robot robot ->
            if (robot.rXActive) {
                if (robot.rXShoot && distance(robot.rXShoot,robot.rXPosition) <= shootDistance) {
                    //Add temperature of shoot to robot
                    robot.rXTemperature = robot.rXTemperature + SHOOT_TEMPERATURE
                    def shoot = new Expando()
                    shoot.origin = new Position(x: robot.rXPosition.x,y: robot.rXPosition.y)
                    shoot.destination = new Position(x: robot.rXShoot.x,y: robot.rXShoot.y)
                    shoot.distance = distance(shoot.origin,shoot.destination)
                    shoot.turns = 0
                    shoot.finished = false
                    shoot.robot = robot.name
                    shoot.incx = (shoot.distance>0?(shoot.destination.x - shoot.origin.x) / shoot.distance : 1)
                    shoot.incy = (shoot.distance>0?(shoot.destination.y - shoot.origin.y) / shoot.distance : 1)
                    shoots << shoot
                } else {
                    //Reduce temperature cause don't shoot
                    robot.rXTemperature = robot.rXTemperature - REST_TEMPERATURE
                    //Cant reduce less than 0
                    if (robot.rXTemperature < 0) {
                        robot.rXTemperature = 0
                    }
                }
                //Clear shoot on robot
                robot.rXShoot = null
            }
        }
    }

    /**
     * Process shoots, maybe some impacts in this phase
     * @param phase (from 1 to speedRatio)
     */
    def processShoots(phase) {
        shoots.each { shoot ->
            if (!shoot.finished) {
                def distanceDone = (shoot.turns*speedRatio) + phase
                if (distanceDone>=shoot.distance) {
                    processImpact(shoot)
                }
            }
        }
    }

    /**
     * Process impact of a shoot
     * @param shoot
     * @return
     */
    def processImpact(shoot) {
        players.each { Robot player ->
            if (player.rXActive) {
                if (distance(player.rXPosition,shoot.destination) <= IMPACT_DISTANCE) {
                    player.rXLife = player.rXLife - IMPACT_DAMAGE
                    if (player.isDestroyed()) {
                        player.rXActive = false
                        increaseDeaths(player.name)
                        //Kill myself isn't a kill shot
                        if (shoot.robot!=player.name) {
                            increaseKillShoots(shoot.robot)
                        }

                    }
                }
            }
        }
        shoot.finished = true
    }

    /**
     * Death robots with hot temperature
     * @return
     */
    def processHotRobots() {
        players.each {  Robot robot ->
            if (robot.rXActive) {
                if (robot.isExploding()) {
                    robot.rXActive = false
                    increaseDeaths(robot.name)
                }
            }
        }
    }

    /**
     * Robot that crash with walls, go out of game zone, or crash with other robots
     * @return
     */
    def processUselessRobots() {
        players.each {  Robot robot ->
            if (robot.rXActive) {
                if (robot.isCrashed()) {
                    robot.rXActive = false
                    increaseDeaths(robot.name)
                } else {
                    players.each {  Robot otherRobot ->
                        if (otherRobot.name!=robot.name && otherRobot.rXActive &&
                            distance(robot.rXPosition,otherRobot.rXPosition) < COLLISION_DISTANCE) {
                            //Both die
                            robot.rXActive = false
                            increaseDeaths(robot.name)
                            otherRobot.rXActive = false
                            increaseDeaths(otherRobot.name)
                        }

                    }
                }
            }
        }
    }

    /**
     * Move robots
     * @return
     */
    def moveRobots() {
        players.each { Robot player ->
            if (player.rXMove) {
                if (player.rXMove == Direction.north) {
                    player.rXPosition.y = player.rXPosition.y - 1
                }
                if (player.rXMove == Direction.south) {
                    player.rXPosition.y = player.rXPosition.y + 1
                }
                if (player.rXMove == Direction.east) {
                    player.rXPosition.x = player.rXPosition.x + 1
                }
                if (player.rXMove == Direction.west) {
                    player.rXPosition.x = player.rXPosition.x - 1
                }
            }
            player.rXMove = null
        }
    }

    def cleanTurn() {
        //Recreate new list of players
        def newPlayers = []
        players.each { Robot it ->
            if (it.rXActive) {
                newPlayers << it
            }
        }
        players = null
        players = newPlayers
        //Fill with new robots if needed
        while (players.size() < sameTime) {
            joinRandomRobotToBattle()
        }
        //Increase turn in shoots and remove finished
        def newShoots = []
        shoots.each { shoot ->
            if (!(shoot.finished)) {
                shoot.turns = shoot.turns + 1
                newShoots << shoot
            }
        }
        shoots = null
        shoots = newShoots
    }

    def addStat(name) {
        if (!stats.containsKey(name)) {
            def exp = new Expando()
            exp.ks = 0
            exp.deaths = 0
            //exp.color = robots.find { it -> it.name==name}.rXColorName
            stats.putAt(name, exp)
        }
    }

    /**
     * Add a robot to the game
     * @param robot
     */
    def addRobotToGame(robot) {
        if (robot && robots.size() < MAX_ROBOTS && robot.name && !robots.any() { it.name == robot.name}) {
            robots << robot
            addStat(robot.name)
            return true
        } else {
            return false
        }
    }

    def increaseDeaths(name) {
        //prepareInsertStat(name)
        stats."$name".deaths = stats."$name".deaths + 1
    }

    def increaseKillShoots(name) {
        //prepareInsertStat(name)
        stats."$name".ks = stats."$name".ks + 1
    }

    def String toString() {
        def out = ''
        stats.each { key,value ->
            out += " ${key} Deaths:${value.deaths} Ks:${value.ks}"
        }
        return out
    }

    def toTable() {
        def result = "<table class='table'>"
        result += '<tr> <th>Color</th> <th>Name</th> <th>Deaths</th> <th> Kill shoots </th> </tr>'
        stats.each { key,value ->
            def robot = robots.find { it -> it.name==key}
            result += " <tr> <td class='${robot.rXColorName}'>${(robot.rXActive?robot.rXLife:'Off')}</td> <td>${key}</td> <td align='center'>${value.deaths}</td> <td align='center'> ${value.ks} </td> </tr>"
        }
        return result + '</table>'
    }

}
