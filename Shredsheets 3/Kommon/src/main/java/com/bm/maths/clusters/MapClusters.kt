package com.bm.maths.clusters

import com.badlogic.gdx.math.Vector2
import com.bm.common.IntKeyPair
import com.bm.maths.extensions.getIndex
import com.bm.maths.extensions.getRectangle
import com.bm.maths.extensions.interpolate
import com.bm.maths.extensions.relativePosition
import com.bm.maths.Maths
import com.bm.common.NeighborDirections
import com.bm.factories.Vec2Factory

import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList

object MapClusters {

    inline fun <reified T> getNeighbors(map: Array<Array<T>>, x: Float, y: Float): Array<T?> {
        return getNeighbors(map, x.toInt(), y.toInt())
    }

    inline fun <reified T> getNeighbors(map: Array<Array<T>>, x: Int, y: Int): Array<T?> {
        return getNeighbors(map, x, y, false, false)
    }

    inline fun <reified T> getNeighbors(map: Array<Array<T>>, x: Float, y: Float, edgeAwareX: Boolean, edgeAwareY: Boolean): Array<T?> {
        return getNeighbors(map, x.toInt(), y.toInt(), edgeAwareX, edgeAwareY)
    }

    inline fun <reified T> getNeighbors(map: Array<Array<T>>, x: Int, y: Int, edgeAwareX: Boolean, edgeAwareY: Boolean): Array<T?> {
        return Array(8) { i -> getNeighbor(map, x, y, i, edgeAwareX, edgeAwareY) }
    }

    fun <T> getNeighbors(map: Array<Array<T>>, x: Int, y: Int, edgeAwareX: Boolean, edgeAwareY: Boolean, results: Array<T?>): Array<T?> {
        for (i in 0..7) {
            results[i] = getNeighbor(map, x, y, i, edgeAwareX, edgeAwareY)
        }
        return results
    }

    fun <T> getNeighbor(map: Array<Array<T>>, x: Int, y: Int, direction: Int): T? {
        return getNeighbor(map, x, y, direction, false, false)
    }

    fun <T> getNeighbor(map: Array<Array<T>>, x: Int, y: Int, direction: Int, edgeAwareX: Boolean, edgeAwareY: Boolean): T? {
        var targetX = x
        var targetY = y
        when (direction) {
            NeighborDirections.TOP_RIGHT, NeighborDirections.RIGHT, NeighborDirections.BOTTOM_RIGHT -> targetX++
            NeighborDirections.TOP_LEFT, NeighborDirections.LEFT, NeighborDirections.BOTTOM_LEFT -> targetX--
        }

        if (edgeAwareX) {
            targetX = (map[0].size + targetX) % map[0].size
        } else {
            if (targetX < 0 || targetX >= map[0].size) {
                return null
            }
        }

        //Y Coordinate
        if (direction == NeighborDirections.TOP_RIGHT || direction == NeighborDirections.TOP || direction == NeighborDirections.TOP_LEFT) targetY++
        else if (direction == NeighborDirections.BOTTOM_RIGHT || direction == NeighborDirections.BOTTOM || direction == NeighborDirections.BOTTOM_LEFT) targetY--

        if (edgeAwareY) {
            targetY = (map.size + targetY) % map.size
        } else {
            if (targetY < 0 || targetY >= map.size) {
                return null
            }
        }

//if (neighbor != item.getValue()) neighbor = null; //if the neighbor is a part of a different cluster, simply fill that neighbor's spot with null
        return map[targetY][targetX]
    }

    fun getNeighbors(map: Array<FloatArray>, item: MapItem): FloatArray {
        val items = FloatArray(8)
        for (i in 0..7) {
            items[i] = getNeighbor(map, item, i)
        }
        return items
    }

    fun getNeighbors(map: Array<IntArray>, item: MapItem): IntArray {
        val items = IntArray(8)
        for (i in 0..7) {
            items[i] = getNeighbor(map, item, i)
        }
        return items
    }

    fun getBottomLeft(map: Array<FloatArray>): Vector2? {
        for (y in map.indices) {
            for (x in 0 until map[y].size) {
                if (map[y][x] > 0f) return Vec2Factory.get(x.toFloat(), y.toFloat())
            }
        }
        return null
    }

    fun getNeighborDirection(map: Array<FloatArray>, x: Int, y: Int, flowDownward: Boolean): Int {
        var bestValue = -1f
        val bestDirections = ArrayList<Int>()

        for (direction in 0 until NeighborDirections.DIRECTION_COUNT) {
            val nValue = getNeighbor(map, x, y, direction)
            if (nValue == -1f) continue

            if (bestValue == -1f) {
                bestValue = nValue
                bestDirections.add(direction)
                continue
            }

            if (nValue == bestValue) {
                bestDirections.add(direction)
                continue
            }

            if (flowDownward && nValue < bestValue || !flowDownward && nValue > bestValue) {
                bestValue = nValue
                bestDirections.clear()
                bestDirections.add(direction)
            }
        }

        return if (bestDirections.size == 0) -1 else bestDirections[Maths.randomInt(0, bestDirections.size)]
    }

    fun getNeighbor(map: Array<FloatArray>, x: Int, y: Int, direction: Int): Float {
        val targetX = NeighborDirections.GetNeighborX(x, direction)
        val targetY = NeighborDirections.GetNeighborY(y, direction)

        if (targetX < 0 || targetX >= map[0].size) return -1f
        return if (targetY < 0 || targetY >= map.size) -1f else map[targetY][targetX]

    }

    fun getNeighbor(map: Array<FloatArray>, item: MapItem, direction: Int): Float {
        return getNeighbor(map, item.xIndex, item.yIndex, direction)
    }

    fun getNeighbor(map: Array<IntArray>, item: MapItem, direction: Int): Int {
        val targetX = NeighborDirections.GetNeighborX(item.xIndex, direction)
        val targetY = NeighborDirections.GetNeighborY(item.yIndex, direction)

        if (targetX < 0 || targetX >= map[0].size) return -1
        return if (targetY < 0 || targetY >= map.size) -1 else map[targetY][targetX]

    }

    fun getClusters(tileMap: Array<FloatArray>, key: Int, tileSize: Float): ArrayList<MapCluster> {
        //MapItem[][] map = GetMap(tileMap);
        val clusters = ArrayList<MapCluster>()

        for (y in tileMap.indices) {
            val row = tileMap[y]
            for (x in row.indices) {
                val item = row[x]
                if (item == 0f || item != key.toFloat()) continue

                val clusterItem = MapItem(x, y, item.toInt().toFloat(), tileSize)

                var clusterFound = false
                for (cluster in clusters) {
                    if (cluster.contains(clusterItem)) {
                        clusterFound = true
                        break
                    } else {
                        clusterFound = false
                    }
                }
                if (clusterFound) continue

                val cluster = getCluster(clusterItem, tileMap, clusters) ?: continue
                clusters.add(cluster)
            }
        }

        return clusters
    }

    fun getLargestCluster(tileMap: Array<FloatArray>, tileSize: Float = 1f): MapCluster? {
        return getClusters(tileMap, tileSize).maxBy { c -> c.size() }
    }

    fun getClusters(tileMap: Array<FloatArray>, tileSize: Float = 1f): ArrayList<MapCluster> {
        val clusters = ArrayList<MapCluster>()

        for (y in tileMap.indices) {
            val row = tileMap[y]
            for (x in row.indices) {
                val item = row[x]
                if (item == 0f) continue

                val clusterItem = MapItem(x, y, item.toInt().toFloat(), tileSize)

                var clusterFound = false
                for (cluster in clusters) {
                    if (cluster.contains(clusterItem)) {
                        clusterFound = true
                        break
                    } else {
                        clusterFound = false
                    }
                }
                if (clusterFound) continue

                val cluster = getCluster(clusterItem, tileMap, clusters) ?: continue
                clusters.add(cluster)
            }
        }

        return clusters
    }

    private fun getCluster(item: MapItem, map: Array<FloatArray>, clusters: ArrayList<MapCluster>): MapCluster? {
        var item = item
        val boundaryPoints = LinkedList<MapItem>()
        val locations = HashSet<IntKeyPair>()

        var startLocation = 0
        while (true) {
            if (boundaryPoints.size > 1 && boundaryPoints.first == item)
                break
            val neighbors = getNeighbors(map, item)
            var neighborCount = 0
            for (neighbor in neighbors)
                if (neighbor != 0f && neighbor == item.value) neighborCount++

            if (neighborCount >= 2 && locations.add(IntKeyPair(item.xIndex, item.yIndex)))
                boundaryPoints.add(item) //each location only gets added once, to deal with one pixel bridges

            var itemFound = false
            for (i in startLocation until neighbors.size + startLocation) {
                val neighborValue = neighbors[i % 8]
                if (neighborValue == 0f) continue
                if (neighborValue != item.value) continue

                val clusterNeighbor = MapItem(NeighborDirections.GetNeighborX(item.xIndex, i % 8), NeighborDirections.GetNeighborY(item.yIndex, i % 8), neighborValue.toInt().toFloat(), item.tileSize)
                for (cluster in clusters)
                    if (cluster.contains(clusterNeighbor))
                        return null //don't return the same cluster multiple times

                neighborCount = 0
                for (neighborNeighbor in getNeighbors(map, clusterNeighbor)) {
                    if (neighborNeighbor == item.value) neighborCount++
                }

                if (boundaryPoints.size != 0 || neighborCount >= 2) {
                    item = clusterNeighbor
                    startLocation = NeighborDirections.GetOppositeDirection(i) + 1
                    itemFound = true
                    break
                }
            }
            if (!itemFound) break
        }

        return if (boundaryPoints.size < 3) null else MapCluster(boundaryPoints)
    }

    //expects a counter clockwise list of border points
    fun getHeights(borderPoints: LinkedList<Vector2>, cellSize: Float): FloatArray {
        val rectangle = borderPoints.getRectangle()

        val width = (rectangle.width / cellSize).toInt()
        val heights = FloatArray(width)
        val heightsCount = IntArray(width)

        for (current in borderPoints) {
            if (current.y == rectangle.y) continue

            val currentX = current.x - rectangle.x
            val relativeX = currentX.relativePosition(rectangle.width)
            val heightIndex = relativeX.getIndex(heights)

            val currentY = current.y - rectangle.y
            heights[heightIndex] += currentY
            heightsCount[heightIndex]++
        }

        for (i in heights.indices) {
            if (heightsCount[i] != 0) heights[i] /= heightsCount[i].toFloat()
        }

        for (i in heights.indices) {
            if (heightsCount[i] == 0) {
                val previous = getPreviousValue(heights, i)
                val next = getNextValue(heights, i)

                //y coordinate is the distance
                val range = previous.y + next.y //3 + 5 = 8
                val position = previous.y / range //3 / 8 = 0.375

                heights[i] = previous.x.interpolate(next.x, position)
            }
        }

        return heights
    }

    //x is the value, y is the distance
    private fun getNextValue(values: FloatArray, index: Int): Vector2 {
        val nextValue = Vec2Factory.get()
        for (i in index + 1 until values.size) {
            val value = values[i]
            if (value != 0f) {
                nextValue.set(value, (i - index).toFloat())
                break
            }
        }
        return nextValue
    }

    //x is the value, y is the distance
    private fun getPreviousValue(values: FloatArray, index: Int): Vector2 {
        val nextValue = Vec2Factory.get()
        for (i in index - 1 downTo 0) {
            val value = values[i]
            if (value != 0f) {
                nextValue.set(value, (index - i).toFloat())
                break
            }
        }
        return nextValue
    }


}