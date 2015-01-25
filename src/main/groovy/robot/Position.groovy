package robot

/**
 * JFL 28/10/12
 */
class Position {
    def x
    def y

    def boolean equals (other) {
        return (other && other.x && other.y && other.x==x && other.y==y)
    }
}
