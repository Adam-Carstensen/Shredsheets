package com.bm.maths

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.bm.maths.extensions.getIndex
import com.bm.maths.extensions.relativePosition
import com.bm.factories.Vec2Factory
import java.lang.Exception

import java.util.ArrayList
import java.util.Random
import kotlin.math.*

object Maths {

    private val rnd = Random()
    val sqrt2 = sqrt(2.0).toFloat()

    fun getUnitCircleCoordinate(location: Float) : Vector2 {
        var twoPiLocation = location * PI * 2
        return Vector2(cos(twoPiLocation).toFloat(), sin(twoPiLocation).toFloat())
    }


    fun getRandomAddends(sum: Int, numberOfAddends: Int) : IntArray {
        if (numberOfAddends == 0) return intArrayOf()
        var result = IntArray(numberOfAddends)
        var distribution = getRandomDistribution(numberOfAddends)

        for (i in 0 until distribution.count()) {
            result[i] = (distribution[i] * sum).toInt()
        }

        var resultSum = result.sum()
        var sumDelta = sum - resultSum //100 - 101 = -1
        var randomIndex = randomInt(0, result.count())
        result[randomIndex] += sumDelta
        return result
    }

    fun getRandomDistribution(count: Int) : FloatArray {
        var result = FloatArray(count)
        var ints = IntArray(count)
        var sum = 0
        for (i in 0 until ints.count()) {
            ints[i] = randomInt(0, 10000)
            sum += ints[i]
        }
        for (i in 0 until result.count()) {
            result[i] = (ints[i] / sum.toFloat())
        }
        return result
    }

    fun getProbabilityArray(count: Int): FloatArray {
        val probabilities = FloatArray(count)
        val defaultValue = 1f / count.toFloat()
        for (i in probabilities.indices)
            probabilities[i] = defaultValue
        return probabilities
    }

    fun edgeAwareAddF(value: Float, valueToAdd: Float): Float {
        var sum = value + valueToAdd //0.4 + -0.6 = -0.2;
        while (sum < 0f) sum += 1f
        sum %= 1f //1.3 % 1 = 0.3
        return sum
    }

    fun edgeAwareAdd(value: Float, valueToAdd: Float, min: Float, max: Float): Float {
        val range = max - min // 10 - 4 = 6
        val shiftedValue = value - min //8 - 4 = 4;
        var shiftedSum = shiftedValue + valueToAdd //4+15 = 19;
        while (shiftedSum < min) shiftedSum += range
        shiftedSum %= range //23%6 = 6

        return min + shiftedSum
    }

    fun edgeAwareAdd(value: Int, valueToAdd: Int, min: Int, max: Int): Int {
        if (min > max)
            throw Exception("Minimum value: $min is higher than maximum value: $max.")
        if (min == max) return min
            //throw Exception("Minimum value and maximum value both = $min.  Minimum must be lower than maximum.")

        val range = max - min // 10 - 4 = 6
        val shiftedValue = value - min //8 - 4 = 4;
        var shiftedSum = shiftedValue + valueToAdd //4+15 = 19;
        while (shiftedSum < min) shiftedSum += range
        shiftedSum %= range //23%6 = 6

        return min + shiftedSum
    }

    fun randomBoolean(probability: Float = .5f): Boolean {
        return rnd.nextFloat() < probability
    }

    fun distance(fromX: Float, fromY: Float, toX: Float, toY: Float): Float {
        return sqrt((toX - fromX).toDouble().pow(2.0) + (toY - fromY).toDouble().pow(2.0)).toFloat()
    }

    fun <T> randomIndex(collection: Collection<T>): Int {
        return randomIndex(0, collection.size - 1);
    }

    fun <T> getRandom(collection: ArrayList<T>): T {
        return collection[randomIndex(collection)]
    }

    fun randomIndex(min: Int, max: Int, interpolation: Interpolation = Interpolation.linear): Int {
        val fmin = min - .5f + java.lang.Float.MIN_VALUE
        val fmax = max + .5f - java.lang.Float.MIN_VALUE

        val floatValue = fmin + interpolation.apply(rnd.nextFloat()) * (fmax - fmin)
        val value = floatValue.roundToInt()
        return if (value < min || value > max) {
            value
        } else value
    }

    fun randomFloat(min: Float, max: Float, interpolation: Interpolation): Float {
        return min + interpolation.apply(rnd.nextFloat()) * (max - min)
    }

    fun randomFloat(constraint: Constraint<Float>): Float {
        return randomFloat(constraint.minimum, constraint.maximum)
    }

    fun randomFloat(interpolation: Interpolation): Float {
        return interpolation.apply(rnd.nextFloat())
    }

    fun randomFloat(): Float {
        return rnd.nextFloat()
    }

    fun randomFloat(min: Float, max: Float): Float {
        return min + rnd.nextFloat() * (max - min)
    }

    fun randomFloat(max: Float): Float {
        return rnd.nextFloat() * (max)
    }

    fun randomLong() : Long {
        return rnd.nextLong()
    }

    fun randomInt(max: Int): Int {
        return randomFloat(max.toFloat()).toInt()
    }

    fun randomInt(min: Int, max: Int): Int {
        return randomFloat(min.toFloat(), max.toFloat()).toInt()
    }

    fun randomInt(min: Float, max: Float): Int {
        return randomFloat(min, max).toInt()
    }

    fun randomInt(min: Int, max: Int, interpolation: Interpolation): Int {
        return randomFloat(min.toFloat(), max.toFloat(), interpolation).roundToInt()
    }

    fun mean(a: Int, b: Int): Float {
        return (a + b) / 2f
    }

    fun mean(a: Float, b: Float): Float {
        return (a + b) / 2f
    }

    fun mean(a: Float, b: Float, c: Float): Float {
        return (a + b + c) / 3f
    }

    fun average(a: Float, b: Float, impact: Float): Float {
        val average = (a + b) / 2f
        val difference = average - a
        return a + difference * impact
    }

    fun screen(a: Float, b: Float): Float {
        return 1 - (1 - a) * (1 - b)
    }

    fun screen(a: Float, b: Float, impact: Float): Float {
        return 1 - (1 - a) * ((1 - b) * impact)
    }

    fun getRandomMatrixLocations(width: Int, height: Int): ArrayList<Vector2> {
        val locations = ArrayList<Vector2>()

        for (x in 0 until width) {
            for (y in 0 until height) {
                locations.add(Vec2Factory.get(x.toFloat(), y.toFloat()))
            }
        }

        locations.shuffle()
        return locations
    }

    fun invert(matrix: Array<FloatArray>): Array<FloatArray> {
        for (y in matrix.indices) {
            val row = matrix[y]
            for (x in row.indices) {
                row[x] = 1 - row[x]
            }
        }
        return matrix
    }

    fun angle(fromX: Float, fromY: Float, toX: Float, toY: Float): Float {
        val from = Vec2Factory.get(fromX, fromY)
        val to = Vec2Factory.get(toX, toY)
        val angle = angle(from, to)
        Vec2Factory.dispose(from)
        Vec2Factory.dispose(to)
        return angle
    }

    fun angle(from: Vector2, to: Vector2): Float {
        return (180.0f / Math.PI * atan2((to.x - from.x).toDouble(), (from.y - to.y).toDouble())).toFloat()
    }

    fun scale(map: Array<FloatArray>, width: Int, height: Int): Array<FloatArray> {
        if (map.size == height && map[0].size == width) return map
        val newMap = Array(height) { FloatArray(width) }

        for (y in newMap.indices) {
            val row = newMap[y]
            val relativeY = y.relativePosition(newMap.size)

            for (x in row.indices) {
                var relativeX = x.relativePosition(newMap[0].size)

                val originalY = relativeY.getIndex(map)
                val originalX = relativeX.getIndex(map[originalY])

                newMap[y][x] = map[originalY][originalX]
            }
        }
        return newMap
    }
}
