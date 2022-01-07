package com.bm.common


class NeighborDirections internal constructor(val index: Int) {
    companion object {
        const val BOTTOM_LEFT = 0
        const val BOTTOM = 1
        const val BOTTOM_RIGHT = 2
        const val RIGHT = 3
        const val TOP_RIGHT = 4
        const val TOP = 5
        const val TOP_LEFT = 6
        const val LEFT = 7
        const val DIRECTION_COUNT = 8

        fun GetDirection(fromX: Float, fromY: Float, toX: Float, toY: Float): Int {
            if (toX == fromX) {
                if (toY == fromY) return DIRECTION_COUNT
                if (toY > fromY) return TOP else return BOTTOM
            }

            if (toX > fromX) { //forward
                if (toY == fromY) return RIGHT
                if (toY > fromY) return TOP_RIGHT else return BOTTOM_RIGHT
            }

            //backward
            if (toY == fromY) return LEFT
            if (toY > fromY) return TOP_LEFT else return BOTTOM_LEFT
        }

        fun GetNextDirection(direction: Int): Int {
            return (direction + 1) % DIRECTION_COUNT
        }

        fun GetOppositeDirection(direction: Int): Int {
            return (direction + 4) % DIRECTION_COUNT
        }

        fun GetNeighborX(x: Int, direction: Int): Int {
            if (direction == LEFT || direction == BOTTOM_LEFT || direction == TOP_LEFT) return x - 1
            else if (direction == RIGHT || direction == BOTTOM_RIGHT || direction == TOP_RIGHT) return x + 1
            else return x
        }

        fun GetNeighborY(y: Int, direction: Int): Int {
            if (direction == BOTTOM || direction == BOTTOM_LEFT || direction == BOTTOM_RIGHT) return y - 1
            else if (direction == TOP || direction == TOP_LEFT || direction == TOP_RIGHT) return y + 1
            else return y
        }
    }

}
