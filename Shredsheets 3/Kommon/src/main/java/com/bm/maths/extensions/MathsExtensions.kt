package com.bm.maths.extensions

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.bm.maths.Maths
import java.util.*


fun Int.sqrt(): Float {
    return Math.sqrt(this.toDouble()).toFloat()
}

fun Float.sqrt(): Float {
    return Math.sqrt(this.toDouble()).toFloat()
}

fun Double.sqrt(): Double {
    return Math.sqrt(this)
}


fun Float.distance(b: Float): Float {
    return Math.abs(b - this)
}

fun Float.difference(b: Float): Float {
    return b - this
}

fun Float.difference(b: Float, edgeMin: Float, edgeMax: Float): Float {
    val aToEdge = Math.min(this - edgeMin, edgeMax - this) // 10 - 0 vs 100 - 10
    val bToEdge = Math.min(b - edgeMin, edgeMax - b) // 50 - 0 vs 100 - 50

    val difference = b - this

    if (Math.abs(difference) < aToEdge + bToEdge) return difference

    return if (this < b) {
        -(aToEdge + bToEdge)
    } else aToEdge + bToEdge
}

fun Float.relativePosition(size: Float): Float {
    if (size <= 0) return 0f
    if (size == 1f) return 1f
    else return this / size
}

fun Float.relativePosition(size: Int): Float {
    if (size <= 0) return 0f
    if (size == 1) return 1f
    return this / size
}

fun Float.relativePosition(from: Float, to: Float): Float {
    val range = to - from
    return (this - from) / range
}

fun Int.relativePosition(size: Int): Float {
    if (size <= 0) return 0f
    if (size == 1) return 1f
    return this.toFloat() / size
}

fun Int.relativePosition(from: Int, to: Int): Float {
    val range = (to - from).toFloat()
    return ((this - from).toFloat() / range)
}

fun FloatArray.randomIndex(): Int {
    var probabilities = this.clone()
    probabilities = probabilities.fillProbabilities()
    val value = Maths.randomFloat()
    var totalProbability = 0f
    for (i in probabilities.indices) {
        totalProbability += probabilities[i]
        if (totalProbability >= value) return i
    }
    return 0
}

fun FloatArray.getWeightedIndex(value: Float): Int {
    var probabilities = this.clone()
    probabilities = probabilities.fillProbabilities()
    var totalProbability = 0f
    for (i in probabilities.indices) {
        totalProbability += probabilities[i]
        if (totalProbability >= value) return i
    }
    return 0
}

fun <T> Array<T>.getRelative(relativePosition: Float): T {
    return this[relativePosition.getIndex(this)]
}

fun FloatArray.relativeValues(): FloatArray {
    val relativeValues = FloatArray(this.size)
    val maxValue = this.max()!!

    for ((i, value) in this.withIndex()) {
        relativeValues[i] = value / maxValue
    }
    return relativeValues
}

fun Float.floor(): Int {
    return Math.floor(this.toDouble()).toInt()
}

fun Float.ceiling(): Int {
    return Math.ceil(this.toDouble()).toInt()
}

fun FloatArray.scale(scalar: Float): FloatArray {
    var scaled = FloatArray(((this.size * scalar).toInt()))
    this.forEachIndexed { i, fl -> scaled[((i / scalar).toInt())] += fl }
    return scaled
}

fun FloatArray.getRelative(relativePosition: Float): Float {
    return this[relativePosition.getIndex(this)]
}

fun ShortArray.vary(maxSwing: Float): ShortArray {
    for (i in this.indices)
        this[i] = this[i].vary(maxSwing);
    return this
}

fun Short.vary(maxSwing: Float): Short {
    return Maths.randomFloat(this - this * maxSwing, this + this * maxSwing).toShort()
}

fun Int.vary(maxSwing: Float): Int {
    return Maths.randomFloat(this - this * maxSwing, this + this * maxSwing).toInt()
}

fun Float.vary(maxSwing: Float): Float {
    return Maths.randomFloat(this - this * maxSwing, this + this * maxSwing)
}

fun Float.vary(maxSwing: Float, floor: Float, ceiling: Float): Float {
    return this.vary(maxSwing, Interpolation.linear, floor, ceiling, false)
}

fun Int.vary(maxSwing: Float, floor: Int, ceiling: Int): Int {
    return this.vary(maxSwing, Interpolation.linear, floor, ceiling, false)
}

fun Int.vary(maxSwing: Float, floor: Int, ceiling: Int, edgeAware: Boolean): Int {
    return this.vary(maxSwing, Interpolation.linear, floor, ceiling, edgeAware)
}

fun Float.add(value: Float) : Float {
    return this + value
}

fun Float.add(value: Float, floor: Float, ceiling: Float, edgeAware: Boolean): Float {
    var sum = this+value
    return sum.clamp(floor, ceiling, edgeAware)
}

fun Float.vary(maxSwing: Float, floor: Float, ceiling: Float, edgeAware: Boolean): Float {
    return this.vary(maxSwing, Interpolation.linear, floor, ceiling, edgeAware)
}

fun Float.vary(maxSwing: Float, interpolation: Interpolation, floor: Float, ceiling: Float, edgeAware: Boolean): Float {
    var from = this * (1f - maxSwing * .5f)
    var to = this * (1f + maxSwing * .5f)

    if (edgeAware) {
        val result = Maths.randomFloat(from, to, interpolation)
        val size = ceiling - floor
        return (size + result) % size
    }

    if (from > ceiling) from = floor
    if (from < floor) from = floor
    if (to < floor) to = floor
    if (to > ceiling) to = ceiling

    return Maths.randomFloat(from, to, interpolation)
}

fun Int.vary(maxSwing: Float, interpolation: Interpolation, floor: Int, ceiling: Int, edgeAware: Boolean): Int {
    var from = (this * (1f - maxSwing * .5f)).toInt()
    var to = (this * (1f + maxSwing * .5f)).toInt()

    if (edgeAware) {
        val result = Maths.randomInt(from, to, interpolation)
        val size = ceiling - floor
        return ((size + result) % size)
    }

    if (from > ceiling) from = floor
    if (from < floor) from = floor
    if (to < floor) to = floor
    if (to > ceiling) to = ceiling

    return Maths.randomInt(from, to, interpolation)
}

fun FloatArray.rotate(steps: Int): FloatArray {
    var steps = steps
    val rotated = FloatArray(this.size)
    steps = (this.size + steps) % this.size
    var tempIndex = 0
    for (i in steps until steps + this.size) {
        rotated[tempIndex++] = this[i % this.size]
    }
    return rotated
}

fun <T> Array<T>.rotate(steps: Int): Array<T> {
    var steps = steps
    val rotated = this.clone()

    steps = (this.size + steps) % this.size

    var tempIndex = 0
    for (i in steps until steps + this.size) {
        rotated[tempIndex++] = this[i % this.size]
    }
    return rotated
}

fun FloatArray.fillProbabilities(): FloatArray {
    var totalProbability = 0f
    var zeroes = 0
    for (i in this.indices) {
        totalProbability += this[i]
        if (this[i] == 0f) zeroes++
    }

    val difference = totalProbability.difference(1f)
    if (difference == 0f) return this

    if (zeroes != 0) {
        val offset = difference / zeroes
        for (i in this.indices) {
            if (this[i] == 0f) {
                this[i] = offset
            }
        }
        return this
    }

    val offset = difference / this.size
    for (i in this.indices)
        this[i] += offset

    return this
}


fun FloatArray.setProbability(index: Int, value: Float): FloatArray {
    val currentValue = this[index]
    val difference = currentValue.difference(value) // .5f,  .75f  = .25f
    val offset = difference / (this.size - 1f) //don't offset the value which is being set

    for (i in this.indices) {
        if (i == index) continue
        this[i] -= offset
    }

    this[index] = value
    return this
}

fun Interpolation.interpolate(from: Float, to: Float, a: Float): Float {
    val range = to - from
    return from + range * this.apply(a)
}

fun Float.interpolate(to: Float, a: Float): Float {
    val range = to - this
    return this + range * a
}

fun Float.interpolate(to: Float, a: Float, min: Float, max: Float): Float {
    val range = to - this// 100 - 20 = 80
    val edgeRange = max - min //40 - 5 = 35
    val shiftedValue = this - min //20 - 5 = 15;

    val valueToAdd = range * a //80 * .5 = 40

    var shiftedSum = shiftedValue + valueToAdd // 15 + 40 = 55
    while (shiftedSum < min) shiftedSum += range
    shiftedSum %= edgeRange //55 %= 35 = 20

    return min + shiftedSum // 10 + 20 = 30
}

fun LinkedList<Vector2>.getRectangle(): Rectangle {
    var top = -java.lang.Float.MAX_VALUE
    var bottom = java.lang.Float.MAX_VALUE
    var left = java.lang.Float.MAX_VALUE
    var right = -java.lang.Float.MAX_VALUE

    for (point in this) {
        if (point.x < left) left = point.x
        if (point.x > right) right = point.x
        if (point.y < bottom) bottom = point.y
        if (point.y > top) top = point.y
    }

    return Rectangle(left, bottom, right - left, top - bottom)
}

fun Int.inRange(min: Int, max: Int): Boolean {
    return this in min..max
}

fun Int.clamp(min: Int, max: Int): Int {
    return Math.min(Math.max(this, min), max)
}

fun Float.clamp(min: Float, max: Float, edgeAware: Boolean = false): Float {
    if (!edgeAware) return Math.min(Math.max(this, min), max)
    val range = max - min
    return (range + this) % range
}

fun Float.inRange(min: Float, max: Float): Boolean {
    return this in min..max
}

fun Double.clamp(min: Double, max: Double): Double {
    return Math.min(Math.max(this, min), max)
}

fun Double.inRange(min: Double, max: Double): Boolean {
    return this in min..max
}

fun Float.getIndex(min: Float, max: Float): Int {
    val range = max - min
    return Math.round(min + range * this)
}

fun Float.getIndex(array: FloatArray): Int {
    return Math.round(this * (array.size - 1))
}

fun <T> Float.getIndex(array: Array<T>): Int {
    return Math.round(this * (array.size - 1))
}

fun Float.getIndex(arraySize: Float): Int {
    return Math.round(this * (arraySize - 1))
}

fun Float.getIndex(arraySize: Int): Int {
    return Math.round(this * (arraySize - 1))
}
