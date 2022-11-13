package com.bm.common


import com.badlogic.gdx.graphics.Color
import kotlin.math.max
import kotlin.math.min

/**
 * The HSLColor class provides methods to manipulate HSL (Hue, Saturation
 * Luminance) values to create a corresponding Color object using the RGB
 * ColorSpace.
 *
 * The HUE is the color, the Saturation is the purity of the color (with
 * respect to grey) and Luminance is the brightness of the color (with respect
 * to black and white)
 *
 * The Hue is specified as an angel between 0 - 360 degrees where red is 0,
 * green is 120 and blue is 240. In between you have the colors of the rainbow.
 * Saturation is specified as a percentage between 0 - 100 where 100 is fully
 * saturated and 0 approaches gray. Luminance is specified as a percentage
 * between 0 - 100 where 0 is black and 100 is white.
 *
 * In particular the HSL color space makes it easier change the Tone or Shade
 * of a color by adjusting the luminance value.
 */
class HSLColor {
    var rgb: Color? = null
        private set
    var hsl: FloatArray
        private set
    var alpha: Float = 0f
        private set

    val hue: Float
        get() = hsl[0]

    val luminance: Float
        get() = hsl[2]

    val saturation: Float
        get() = hsl[1]

    /**
     * Create a HSLColor object using an RGB Color object.
     *
     * @param rgb the RGB Color object
     */
    constructor(rgb: Color) {
        this.rgb = rgb
        hsl = fromRGB(rgb)
        alpha = rgb.a / 255.0f
    }

    /**
     * Create a HSLColor object using individual HSL values.
     *
     * @param h     the Hue value in degrees between 0 - 360
     * @param s     the Saturation percentage between 0 - 100
     * @param l     the Lumanance percentage between 0 - 100
     * @param alpha the value value between 0 - 1
     */
    constructor(h: Float, s: Float, l: Float, alpha: Float = 1.0f) {
        hsl = floatArrayOf(h, s, l)
        this.alpha = alpha
        rgb = toRGB(hsl, alpha)
    }

    /**
     * Create a HSLColor object using an an array containing the
     * individual HSL values.
     *
     * @param hsl   array containing HSL values
     * @param alpha the value value between 0 - 1
     */
    constructor(hsl: FloatArray, alpha: Float = 1.0f) {
        this.hsl = hsl
        this.alpha = alpha
        rgb = toRGB(hsl, alpha)
    }

    /**
     * Create a RGB Color object based on this HSLColor with a different
     * Hue value. The degrees specified is an absolute value.
     *
     * @param degrees - the Hue value between 0 - 360
     * @return the RGB Color object
     */
    fun adjustHue(degrees: Float): Color {
        return toRGB(degrees, hsl[1], hsl[2], alpha)
    }

    /**
     * Create a RGB Color object based on this HSLColor with a different
     * Luminance value. The percent specified is an absolute value.
     *
     * @param percent - the Luminance value between 0 - 100
     * @return the RGB Color object
     */
    fun adjustLuminance(percent: Float): Color {
        return toRGB(hsl[0], hsl[1], percent, alpha)
    }

    /**
     * Create a RGB Color object based on this HSLColor with a different
     * Saturation value. The percent specified is an absolute value.
     *
     * @param percent - the Saturation value between 0 - 100
     * @return the RGB Color object
     */
    fun adjustSaturation(percent: Float): Color {
        return toRGB(hsl[0], percent, hsl[2], alpha)
    }

    /**
     * Create a RGB Color object based on this HSLColor with a different
     * Shade. Changing the shade will return a darker color. The percent
     * specified is a relative value.
     *
     * @param percent - the value between 0 - 100
     * @return the RGB Color object
     */
    fun adjustShade(percent: Float): Color {
        val multiplier = (100.0f - percent) / 100.0f
        val l = Math.max(0.0f, hsl[2] * multiplier)

        return toRGB(hsl[0], hsl[1], l, alpha)
    }

    /**
     * Create a RGB Color object based on this HSLColor with a different
     * Tone. Changing the tone will return a lighter color. The percent
     * specified is a relative value.
     *
     * @param percent - the value between 0 - 100
     * @return the RGB Color object
     */
    fun adjustTone(percent: Float): Color {
        val multiplier = (100.0f + percent) / 100.0f
        val l = Math.min(100.0f, hsl[2] * multiplier)

        return toRGB(hsl[0], hsl[1], l, alpha)
    }

    override fun toString(): String {
        return "HSLColor[h=${hsl[0]},s=${hsl[1]},l=${hsl[2]},value=$alpha]"
    }

    companion object {

        var SaturationModifier = 1f
        var LightnessModifier = 1f

        fun fromRGB(color: Color): FloatArray {
            //  get RGB values in the range 0 - 1
            val rgb = floatArrayOf(color.r, color.g, color.b)
            val r = rgb[0]
            val g = rgb[1]
            val b = rgb[2]

            //	Minimum and Maximum RGB values are used in the HSL calculations
            val min = Math.min(r, Math.min(g, b))
            val max = Math.max(r, Math.max(g, b))

            //  Calculate the Hue

            var h = 0f

            if (max == min) h = 0f
            else if (max == r) h = (60 * (g - b) / (max - min) + 360) % 360
            else if (max == g) h = 60 * (b - r) / (max - min) + 120
            else if (max == b) h = 60 * (r - g) / (max - min) + 240

            //  Calculate the Luminance

            val l = (max + min) / 2

            //  Calculate the Saturation

            var s: Float

            if (max == min) s = 0f
            else if (l <= .5f) s = (max - min) / (max + min)
            else s = (max - min) / (2f - max - min)

            return floatArrayOf(h, s * 100, l * 100)
        }

        /**
         * Convert HSL values to a RGB Color.
         * H (Hue) is specified as degrees in the range 0 - 360.
         * S (Saturation) is specified as a percentage in the range 1 - 100.
         * L (Lumanance) is specified as a percentage in the range 1 - 100.
         *
         * @param hsl   an array containing the 3 HSL values
         * @param alpha the value value between 0 - 1
         * @returns the RGB Color object
         */
        @JvmOverloads
        fun toRGB(hsl: FloatArray, alpha: Float = 1.0f): Color {
            return toRGB(hsl[0], hsl[1], hsl[2], alpha)
        }

        val rgbColor = Color()
        @JvmOverloads
        fun toRGB(h: Float, s: Float, l: Float, alpha: Float = 1.0f, color: Color = rgbColor): Color {
            var hue = h
            var saturation = s
            var luminance = l
            if (saturation < 0.0f || saturation > 100.0f) {
                val message = "Color parameter outside of expected range - Saturation"
                throw IllegalArgumentException(message)
            }

            if (luminance < 0.0f || luminance > 100.0f) {
                val message = "Color parameter outside of expected range - Luminance"
                throw IllegalArgumentException(message)
            }

            if (alpha < 0.0f || alpha > 1.0f) {
                val message = "Color parameter outside of expected range - Alpha"
                throw IllegalArgumentException(message)
            }

            // Formula needs all values between 0 - 1.

            hue %= 360.0f
            hue /= 360f
            saturation /= 100f
            luminance /= 100f

            saturation *= SaturationModifier
            luminance *= LightnessModifier

            var q: Float

            if (luminance < 0.5)
                q = luminance * (1 + saturation)
            else
                q = luminance + saturation - saturation * luminance

            val p = 2 * luminance - q

            var r = max(0f, HueToRGB(p, q, hue + 1.0f / 3.0f))
            var g = max(0f, HueToRGB(p, q, hue))
            var b = max(0f, HueToRGB(p, q, hue - 1.0f / 3.0f))

            r = min(r, 1.0f)
            g = min(g, 1.0f)
            b = min(b, 1.0f)

            color.set(r, g, b, alpha)
            return color
        }

        private fun HueToRGB(p: Float, q: Float, h: Float): Float {
            var hue = h
            if (hue < 0) hue += 1f
            else if (hue > 1) hue -= 1f

            if (6 * hue < 1) return p + (q - p) * 6f * hue
            else if (2 * hue < 1) return q
            else if (3 * hue < 2) return p + (q - p) * 6f * (2.0f / 3.0f - hue)
            else return p

        }
    }
}
