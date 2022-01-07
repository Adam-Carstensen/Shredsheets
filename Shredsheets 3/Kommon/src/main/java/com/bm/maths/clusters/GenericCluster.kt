package com.bm.maths.clusters

import com.bm.common.NeighborDirections

class GenericCluster<T> {
    companion object {

        fun GetNeighbors(map: Array<IntArray>, item: MapItem): IntArray {
            val items = IntArray(8)
            for (i in 0..7) {
                items[i] = GetNeighbor(map, item, i)
            }
            return items
        }

        fun GetNeighbor(map: Array<IntArray>, item: MapItem, direction: Int): Int {
            var targetX = item.xIndex
            var targetY = item.yIndex

            //X Coordinate
            if (direction == NeighborDirections.TOP_RIGHT || direction == NeighborDirections.RIGHT || direction == NeighborDirections.BOTTOM_RIGHT) targetX++
            else if (direction == NeighborDirections.TOP_LEFT || direction == NeighborDirections.LEFT || direction == NeighborDirections.BOTTOM_LEFT) targetX--

            if (targetX < 0 || targetX >= map[0].size) return 0

            //Y Coordinate
            if (direction == NeighborDirections.TOP_RIGHT || direction == NeighborDirections.TOP || direction == NeighborDirections.TOP_LEFT) targetY++
            else if (direction == NeighborDirections.BOTTOM_RIGHT || direction == NeighborDirections.BOTTOM || direction == NeighborDirections.BOTTOM_LEFT) targetY--

            if (targetY < 0 || targetY >= map.size) return 0
            else return map[targetY][targetX]
        }
    }

}
