package com.bm.maths

import com.badlogic.gdx.math.Vector2

import java.util.LinkedList

object Vertices {

    fun GetVertexFan(vertices: LinkedList<Vector2>): FloatArray {
        val vertexFan = FloatArray(vertices.size * 2)
        var index = 0
        for (vertex in vertices) {
            vertexFan[index++] = vertex.x
            vertexFan[index++] = vertex.y
        }
        return vertexFan
    }

    fun GetVertexFan3(vertices: LinkedList<Vector2>): FloatArray {
        val vertexFan = FloatArray(vertices.size * 3)
        var index = 0
        for (vertex in vertices) {
            vertexFan[index++] = vertex.x
            vertexFan[index++] = vertex.y
            vertexFan[index++] = 0f
        }
        return vertexFan
    }

    fun ExpandVertexFan2to3(vertices: FloatArray): FloatArray {
        val vertexFan = FloatArray(vertices.size / 2 * 3)
        for (i in vertices.indices) {
            vertexFan[i * 3] = vertices[i * 2]
            vertexFan[i * 3 + 1] = vertices[i * 2 + 1]
            vertexFan[i * 3 + 2] = 0f
        }
        return vertexFan
    }

    fun ExpandVertexFan(vertices: FloatArray, startingComponents: Int, endingComponents: Int): FloatArray {
        val vertexFan = FloatArray(vertices.size / startingComponents * endingComponents)
        for (i in 0 until vertices.size / startingComponents) {
            for (s in 0 until startingComponents) {
                vertexFan[i * endingComponents + s] = vertices[i * startingComponents + s]
            }

            for (e in 0 until endingComponents - startingComponents) {
                vertexFan[i * endingComponents + startingComponents + e] = 0f
            }
        }
        return vertexFan
    }

    fun ExpandVertexFan2to4(vertices: FloatArray): FloatArray {
        val vertexFan = FloatArray((vertices.size / 2f * 4f).toInt())
        var i = 0
        while (i < vertices.size / 2f) {
            vertexFan[i * 4] = vertices[i * 2]
            vertexFan[i * 4 + 1] = vertices[i * 2 + 1]
            vertexFan[i * 4 + 2] = 0f
            vertexFan[i * 4 + 3] = 0f
            i++
        }
        return vertexFan
    }

    fun ExpandVertexFan2to5(vertices: FloatArray): FloatArray {
        val vertexFan = FloatArray(vertices.size / 2 * 5)
        for (i in 0 until vertices.size / 2) {
            vertexFan[i * 5] = vertices[i * 2]
            vertexFan[i * 5 + 1] = vertices[i * 2 + 1]
            vertexFan[i * 5 + 2] = 0f
            vertexFan[i * 5 + 3] = 0f
            vertexFan[i * 5 + 4] = 0f
        }
        return vertexFan
    }


    fun GetDistance(vertices: LinkedList<Vector2>): Float {
        var distance = 0f
        var previous: Vector2? = null
        for (vector in vertices) {
            if (previous != null) distance += previous.dst(vector)
            previous = vector
        }
        return distance
    }

}
