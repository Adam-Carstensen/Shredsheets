package com.bm.maths.clusters

class MapItem(
        val xIndex: Int,
        val yIndex: Int,
        value: Float,
        val tileSize: Float
)
{
    var value: Float = 0f
        private set

    val sceneX: Int
        get() = (xIndex * tileSize).toInt()

    val sceneY: Int
        get() = (yIndex * tileSize).toInt()

    init {
        this.value = value
    }

    fun setValue(value: Int) {
        this.value = value.toFloat()
    }

    override fun hashCode(): Int {
        return xIndex xor yIndex xor value.toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (other is MapItem) {
            val map = other as MapItem?
            return map!!.xIndex == xIndex && map.yIndex == yIndex && map.value == map.value
        }
        return false
    }
}
