package com.bm.factories

import com.badlogic.gdx.math.Vector2

object Vec2Factory : Factory<Vector2>() {

    override val initialCapacity: Int
        get() = 500000

    override fun getNewItem(): Vector2 {
        return Vector2()
    }

    fun get(x: Float = 0f, y: Float = 0f): Vector2 {
        val point = this.get()
        point.x = x
        point.y = y
        return point
    }

    fun get(vector: Vector2): Vector2 {
        val point = this.get()
        point.x = vector.x
        point.y = vector.y
        return point
    }

    override fun dispose(item: Vector2) {
        item.setZero()
        super.dispose(item)
    }

    private val empty = Vector2(0f, 0f)

    fun getEmpty(): Vector2 {
        empty.x = 0f
        empty.y = 0f
        return empty
    }

}
