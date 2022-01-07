package com.bm.maths

import com.badlogic.gdx.math.EarClippingTriangulator
import com.badlogic.gdx.math.Vector2
import com.bm.factories.Vec2Factory
import java.util.LinkedList

object Polygons {

    fun GetRectangle(position: Vector2, width: Float, height: Float): PolygonModel {
        val vertices = LinkedList<Vector2>()

        val halfWidth = width * .5f
        val halfHeight = height * .5f

        vertices.add(Vec2Factory.get(position.x - halfWidth, position.y - halfHeight))
        vertices.add(Vec2Factory.get(position.x + halfWidth, position.y - halfHeight))
        vertices.add(Vec2Factory.get(position.x + halfWidth, position.y + halfHeight))
        vertices.add(Vec2Factory.get(position.x - halfWidth, position.y + halfHeight))

        for (vector in vertices) vector.add(halfWidth, halfHeight)

        val vertexFan = Vertices.GetVertexFan(vertices)
        return GetPolygonModel(vertexFan)
    }

    fun GetTriangleVertices(x: Float, y: Float, width: Float, height: Float): LinkedList<Vector2> {
        return GetTriangleVertices(Vec2Factory.get(x, y), width, height)
    }

    fun GetTriangleVertices(position: Vector2, width: Float, height: Float): LinkedList<Vector2> {
        val vertices = LinkedList<Vector2>()

        val halfWidth = width * .5f
        val halfHeight = height * .5f

        vertices.add(Vec2Factory.get(position.x - halfWidth, position.y - halfHeight))
        vertices.add(Vec2Factory.get(position.x + halfWidth, position.y - halfHeight))
        vertices.add(Vec2Factory.get(position.x, position.y + halfHeight))

        for (vector in vertices) vector.add(halfWidth, halfHeight)
        return vertices
    }


    fun GetTriangleVertices(width: Float, height: Float): LinkedList<Vector2> {
        val vertices = LinkedList<Vector2>()

        val halfWidth = width * .5f
        val halfHeight = height * .5f

        vertices.add(Vec2Factory.get(-halfWidth, -halfHeight))
        vertices.add(Vec2Factory.get(halfWidth, -halfHeight))
        vertices.add(Vec2Factory.get(0f, halfHeight))

        for (vector in vertices) vector.add(halfWidth, halfHeight)
        return vertices
    }


    fun GetTriangle(x: Float, y: Float, width: Float, height: Float): PolygonModel {
        return GetTriangle(Vec2Factory.get(x, y), width, height)
    }

    fun GetTriangle(position: Vector2, width: Float, height: Float): PolygonModel {
        val vertices = GetTriangleVertices(position, width, height)
        val vertexFan = Vertices.GetVertexFan(vertices)
        return GetPolygonModel(vertexFan)
    }

    fun GetCircle(position: Vector2, radius: Float, points: Int): PolygonModel {
        val vertices = LinkedList<Vector2>()
        val target = Vec2Factory.get(position)

        var targetAngle = 0f
        val angleStep = 360f / points

        for (i in 0 until points) {

            val vertex = Vec2Factory.get(Math.cos(Math.toRadians(targetAngle.toDouble())).toFloat() * radius, (Math.sin(Math.toRadians(targetAngle.toDouble())) * radius).toFloat())
            vertex.add(radius, radius)

            vertex.add(target)
            vertices.add(vertex)

            targetAngle += angleStep
        }

        val vertexFan = Vertices.GetVertexFan(vertices)
        return GetPolygonModel(vertexFan)
    }

    fun GetPolygonModel(vertexFan: FloatArray): PolygonModel {
        val triangulator = EarClippingTriangulator()
        val triangleIndices = triangulator.computeTriangles(vertexFan)
        val triangleVertices = FloatArray(triangleIndices.size * 2)

        var i = 0
        while (i < triangleIndices.size) {
            //triangleIndices point to pairs (e.g. index 0x = item1, index 0y = item2, index 1x = item3, etc...
            val vertexIndex1 = triangleIndices.get(i) //triangle pairs are returned clockwise, but box2d needs them counterclockwise
            val vertexIndex2 = triangleIndices.get(i + 1)
            val vertexIndex3 = triangleIndices.get(i + 2)

            val vertexIndex = i * 2

            triangleVertices[vertexIndex] = vertexFan[vertexIndex1 * 2]       //1x
            triangleVertices[vertexIndex + 1] = vertexFan[vertexIndex1 * 2 + 1]   //1y

            triangleVertices[vertexIndex + 2] = vertexFan[vertexIndex2 * 2]       //2x
            triangleVertices[vertexIndex + 3] = vertexFan[vertexIndex2 * 2 + 1]   //2y

            triangleVertices[vertexIndex + 4] = vertexFan[vertexIndex3 * 2]       //3x
            triangleVertices[vertexIndex + 5] = vertexFan[vertexIndex3 * 2 + 1]   //3y
            i += 3
        }

        return PolygonModel(vertexFan, triangleVertices, triangleIndices.toArray())
    }

}
