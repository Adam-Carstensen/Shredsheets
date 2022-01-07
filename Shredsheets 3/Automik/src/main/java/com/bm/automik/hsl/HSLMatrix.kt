package com.bm.automik.hsl

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.Interpolation
import com.bm.common.HSLColor
import com.bm.automik.CellMatrix
import com.bm.common.Size
import com.bm.maths.*
import com.bm.maths.extensions.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HSLMatrix constructor(model: HSLMatrixModel) : CellMatrix<HSLCell, HSLMatrixModel>(model) {

    override fun generate(): HSLCell {
        return HSLCell(0f, 0f, 0f, 0f, 0f, 0f)
    }

    override fun generateMatrix(size: Size): Array<Array<HSLCell>> {
        return Array(size.height) { Array(size.width) { generate() } }
    }

//    private var matrixCells: Array<Array<HSLCell>> = generateMatrix(width, height)
//    override var cells: Array<Array<HSLCell>> = matrixCells

    fun populateCells(pixmap: Pixmap) {
        val pixel = Color()
        for (y in 0 until model.size.height) {
            for (x in 0 until model.size.width) {
                Color.rgba8888ToColor(pixel, pixmap.getPixel(x, y))
                val hslColor = HSLColor(pixel)
                val cell = HSLCell(x.toFloat(), y.toFloat(), hslColor.hue, hslColor.saturation, hslColor.luminance, hslColor.alpha)
                matrixCells[y][x] = cell
            }
        }
    }

    private fun populateCells(hslMatrix: HSLMatrix) {
        val hslCells = hslMatrix.cells
        for (y in 0 until model.size.height) {
            for (x in 0 until model.size.width) {
                matrixCells[y][x] = hslCells[y][x].clone()
            }
        }
    }

    fun populateCells(color: HSLColor, noise: Float, alpha: Float) {
        populateCells(color.hue / 360, color.saturation / 100, color.luminance / 100, noise, alpha)
    }

    fun populateCells(hue: Float, saturation: Float, lightness: Float, noise: Float, alpha: Float) {
        runBlocking {
            for (y in 0 until model.size.height) {
                for (x in 0 until model.size.width) {
                    launch {
                        val cell = HSLCell(x.toFloat(), y.toFloat(),
                                hue.vary(noise) % 1,
                                saturation.vary(noise, 0f, 1f),
                                lightness.vary(noise, 0f, 1f), alpha)
                        matrixCells[y][x] = cell
                    }
                }
            }
        }
    }

    fun populateCells(hue: Float, huePermeability: Float, saturation: Float, saturationPermeability: Float, lightness: Float, lightnessPermeability: Float, noise: Float, alpha: Float) {
        runBlocking {
            for (y in 0 until model.size.height) {
                for (x in 0 until model.size.width) {
                    launch {
                        val cell = HSLCell(x.toFloat(), y.toFloat(),
                                hue.vary(noise) % 1, huePermeability,
                                saturation.vary(noise, 0f, 1f), saturationPermeability,
                                lightness.vary(noise, 0f, 1f), lightnessPermeability,
                                alpha)
                        matrixCells[y][x] = cell
                    }
                }
            }
        }
    }

    fun getScaledHeights(heights: FloatArray): FloatArray {
        val scaledHeights = FloatArray(heights.size)
        val maxValue = heights.max()!!

        for ((i, height) in heights.withIndex()) {
            scaledHeights[i] = height / maxValue
        }

        return scaledHeights
    }

    fun lightnessDescent(heights: FloatArray, from: Float, to: Float, interpolation: Interpolation) {
        for (x in 0 until model.size.width) {
            val relativeX = x.relativePosition(model.size.width)
            val groundHeight = heights.getRelative(relativeX)

            val groundY = (1f-groundHeight).getIndex(model.size.height) //Matrix 0,0 is top left

            for (y in groundY until model.size.height) {
                val relativePosition = y.relativePosition(groundY, model.size.height)
                val targetLightness = interpolation.apply(from, to, relativePosition)
                matrixCells[y][x].lightness = targetLightness
            }
        }
    }

    @JvmOverloads
    fun hueGradientVertical(from: Float, to: Float, a: Float, interpolation: Interpolation = Interpolation.pow3In) {
        val hueDifference = from.difference(to, 0f, 1f)
        for (y in 0 until model.size.height) {

            val relativeY = y.relativePosition(model.size.height)
            val value = hueDifference * interpolation.apply(relativeY) * a
            for (x in 0 until model.size.width) {
                val cell = this.matrixCells[y][x]
                val hue = Maths.edgeAwareAdd(cell.hue, value, 0f, 1f)
                cell.hue = hue
            }
        }
    }

    @JvmOverloads
    fun hueGradientHorizontal(from: Float, to: Float, a: Float, interpolation: Interpolation = Interpolation.pow5In) {
        val hueDifference = from.difference(to, 0f, 1f)
        for (y in 0 until model.size.height) {
            for (x in 0 until model.size.width) {
                val cell = this.matrixCells[y][x]
                val relativeX = x.relativePosition(model.size.width)
                val value = hueDifference * interpolation.apply(relativeX) * a
                val hue = Maths.edgeAwareAdd(cell.hue, value, 0f, 1f)
                cell.hue = hue
            }
        }
    }

    fun lightnessAscent(maxLightness: Float) {
        val minLightness = maxLightness * .2f
        for (y in 0 until model.size.height) {
            val lightness = Interpolation.fade.apply(minLightness, maxLightness, y.toFloat() / model.size.height.toFloat())
            for (x in 0 until model.size.width) {
                this.matrixCells[y][x].lightness = lightness
            }
        }
    }

    override fun alphaCompare(cell: HSLCell): Boolean {
        return cell.lightness <= model.lightnessAlphaThreshold
    }



    override fun clone(): CellMatrix<HSLCell, HSLMatrixModel> {
        val copy = HSLMatrix(model)
        copy.populateCells(this)
        return copy
    }


}
