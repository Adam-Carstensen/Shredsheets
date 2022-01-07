package com.bm.automik.hsl

import com.badlogic.gdx.graphics.Pixmap
import com.bm.maths.clusters.MapClusters
import com.bm.common.HSLColor
import com.bm.automik.Cell2D
import com.bm.automik.CellMatrix
import com.bm.maths.Maths
import com.bm.maths.extensions.clamp
import com.bm.maths.extensions.difference
import com.bm.common.NeighborDirections

class HSLCell(x: Float, y: Float, var hue: Float, var saturation: Float, var lightness: Float, var alpha: Float) : Cell2D<HSLCell, HSLMatrixModel>(x, y) {
    var huePermeability = Maths.randomFloat(.3f, .7f)
    var saturationPermeability = Maths.randomFloat(.7f, .9f)
    var lightnessPermeability = Maths.randomFloat(.6f, .8f)
    var alphaPermeability = 0f

    val color: HSLColor
        get() = HSLColor(hue, saturation, lightness)

    constructor(x: Float, y: Float, hue: Float, huePermeability: Float, saturation: Float, saturationPermeability: Float, lightness: Float, lightnessPermeability: Float, alpha: Float) : this(x, y, hue, saturation, lightness, alpha) {
        this.huePermeability = huePermeability
        this.saturationPermeability = saturationPermeability
        this.lightnessPermeability = lightnessPermeability
    }

    override fun getEmptyArray(size: Int): Array<HSLCell?> {
        return arrayOfNulls<HSLCell?>(size)
    }

    override fun clone(): HSLCell {
        return HSLCell(x, y, hue, saturation, lightness, alpha)
    }

    override fun push(cell: HSLCell, impact: Float) {
        val hueDifference = hue.difference(cell.hue, 0f, 1f)
        val effectiveDifference = hueDifference * huePermeability
        hue = Maths.edgeAwareAdd(hue, effectiveDifference, 0f, 1f)
        saturation = Maths.average(saturation, cell.saturation, saturationPermeability)
        lightness = Maths.average(lightness, cell.lightness, lightnessPermeability)
    }

    override fun draw(matrix: CellMatrix<HSLCell, HSLMatrixModel>, pixmap: Pixmap) {
        val pixelColor = HSLColor.toRGB(hue % 1 * 360, saturation.clamp(0f, 1f) * 100, lightness.clamp(0f, 1f) * 100, alpha)
        pixmap.setColor(pixelColor.r, pixelColor.g, pixelColor.b, pixelColor.a)
        pixmap.drawPixel(x.toInt(), y.toInt())
    }

    private fun drawRectangle(pixmap: Pixmap, cellSize: Int) {
        val rectColor = HSLColor.toRGB(hue % 1 * 360, saturation.clamp( 0f, 1f) * 100, lightness.clamp( 0f, 1f) * 100, alpha)
        pixmap.setColor(rectColor.r, rectColor.g, rectColor.b, rectColor.a)
        pixmap.fillRectangle((x * cellSize).toInt(), (y * cellSize).toInt(), cellSize, cellSize)
    }

    fun drawPixels(cellMatrix: CellMatrix<HSLCell, HSLMatrixModel>, pixmap: Pixmap, cellSize: Int) {
        val clusterNeighbors = getEmptyArray(8)
        val neighborList = MapClusters.getNeighbors(cellMatrix.cells, x.toInt(), y.toInt(), false, false, clusterNeighbors)
        val neighbors = arrayOfNulls<HSLCell>(8)
        for (i in neighborList.indices) {
            neighbors[i] = neighborList[i]
        }

        val fromX = x * cellSize
        val toX = fromX + cellSize
        val fromY = y * cellSize
        val toY = fromY + cellSize

        var py = fromY
        while (py < toY) {
            var px = fromX
            while (px < toX) {
                var totalDistance = 0f

                for (direction in 0 until NeighborDirections.DIRECTION_COUNT) {
                    val neighbor = neighbors[direction] ?: continue
                    totalDistance += Maths.distance(px, py, neighbor.x * cellSize, neighbor.y * cellSize)
                }

                var pixelHue = hue
                var pixelSaturation = saturation
                var pixelLightness = lightness
                var pixelAlpha = alpha

                for (direction in 0 until NeighborDirections.DIRECTION_COUNT) {
                    val neighbor = neighbors[direction] ?: continue

                    val distance = Maths.distance(px, py, neighbor.x * cellSize, neighbor.y * cellSize)
                    val impact = 1 - distance / totalDistance

                    pixelHue = Maths.average(hue, neighbor.hue, impact)
                    pixelSaturation = Maths.average(saturation, neighbor.saturation, impact)
                    pixelLightness = Maths.average(lightness, neighbor.lightness, impact)
                    pixelAlpha = Maths.average(alpha, neighbor.alpha, impact)
                }

                val rgb = HSLColor.toRGB(pixelHue * 360, pixelSaturation * 100, pixelLightness * 100, pixelAlpha)
                pixmap.setColor(rgb.r, rgb.g, rgb.b, rgb.a)
                pixmap.drawPixel(px.toInt(), py.toInt())
                px++
            }
            py++
        }
    }

    override fun add(cell: HSLCell, impact: Float) {
        val hueDiff = this.hue.difference(cell.hue, 0f, 1f) * impact * huePermeability * cell.huePermeability

        this.hue = Maths.edgeAwareAdd(this.hue, hueDiff, 0f, 1f)
        this.saturation = (this.saturation + cell.saturation * impact * saturationPermeability * cell.saturationPermeability).clamp( 0f, 1f)
        this.lightness = (this.lightness + cell.lightness * impact * lightnessPermeability * cell.lightnessPermeability).clamp( 0f, 1f)
        this.alpha = (this.alpha + cell.alpha * impact * alphaPermeability * cell.alphaPermeability).clamp(0f, 1f)
    }

    override fun subtract(cell: HSLCell, impact: Float) {
        val hueDiff = this.hue.difference(cell.hue, 0f, 1f) * impact * huePermeability * cell.huePermeability
        this.hue = Maths.edgeAwareAdd(this.hue, -hueDiff, 0f, 1f)
        this.saturation = (this.saturation - cell.saturation * impact * saturationPermeability * cell.saturationPermeability).clamp(0f, 1f)
        this.lightness = (this.lightness - cell.lightness * impact * lightnessPermeability * cell.lightnessPermeability).clamp(0f, 1f)
        this.alpha = (this.alpha - cell.alpha * impact * alphaPermeability * cell.alphaPermeability).clamp(0f, 1f)
    }

    override fun multiply(cell: HSLCell, impact: Float) {
        this.hue = Maths.average(this.hue, this.hue * cell.hue, impact * huePermeability * cell.huePermeability)
        this.saturation = Maths.average(this.saturation, this.saturation * cell.saturation, impact * saturationPermeability * cell.saturationPermeability)
        this.lightness = Maths.average(this.lightness, this.lightness * cell.lightness, impact * lightnessPermeability * cell.lightnessPermeability)
        this.alpha = Maths.average(this.alpha, this.alpha * cell.alpha, impact * alphaPermeability * cell.alphaPermeability)
    }

    override fun divide(cell: HSLCell, impact: Float) {
        this.hue = Maths.average(this.hue, this.hue / cell.hue, impact * huePermeability * cell.huePermeability)
        this.saturation = Maths.average(this.saturation, this.saturation / cell.saturation, impact * saturationPermeability * cell.saturationPermeability)
        this.lightness = Maths.average(this.lightness, this.lightness / cell.lightness, impact * lightnessPermeability * cell.lightnessPermeability)
        this.alpha = Maths.average(this.alpha, this.alpha / cell.alpha, impact * alphaPermeability * cell.alphaPermeability)
    }

    override fun average(cell: HSLCell, impact: Float) {
        this.hue = Maths.average(this.hue, cell.hue, impact * huePermeability * cell.huePermeability)
        this.saturation = Maths.average(this.saturation, cell.saturation, impact * saturationPermeability * cell.saturationPermeability)
        this.lightness = Maths.average(this.lightness, cell.lightness, impact * lightnessPermeability * cell.lightnessPermeability)
        this.alpha = Maths.average(this.alpha, cell.alpha, impact * alphaPermeability * cell.alphaPermeability)
    }

    override fun screen(cell: HSLCell, impact: Float) {
        this.hue = Maths.average(this.hue, Maths.screen(this.hue, cell.hue), impact * huePermeability * cell.huePermeability)
        this.saturation = Maths.average(this.saturation, Maths.screen(this.saturation, cell.saturation), impact * saturationPermeability * cell.saturationPermeability)
        this.lightness = Maths.average(this.lightness, Maths.screen(this.lightness, cell.lightness), impact * lightnessPermeability * cell.lightnessPermeability)
        this.alpha = Maths.average(this.alpha, Maths.screen(this.alpha, cell.alpha), impact * alphaPermeability * cell.alphaPermeability)
    }

    override fun difference(cell: HSLCell, impact: Float): Float {
        val h = cell.hue.difference(hue, 0f, 1f)
        val s = cell.saturation - saturation
        val l = cell.lightness - lightness
        val a = cell.alpha - alpha
        return (h + s + l + a) / 4
    }

    override fun mask(alpha: Float) {
        this.alpha = alpha
    }
}
