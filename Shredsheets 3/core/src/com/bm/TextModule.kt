package com.bm

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Align
import com.bm.maths.extensions.floor
import kotlin.math.min


object TextModule {

    private var glyphLayout: GlyphLayout = GlyphLayout()

    fun CalculateTextSize(text: String, bsFont: BuildSiteFonts, maxWidth: Float, maxHeight: Float): Float {
        //temporarily set the fontSize to maxHeight
        var font = UserInterface.getFont(bsFont, maxHeight.floor(), Color.BLACK)
        glyphLayout.setText(font, text)

        //calculate the maximum reduction required of the glyphLayout to be within the bounds of the rectangle
        var coefficient: Float = min(maxWidth / glyphLayout.width, maxHeight / glyphLayout.height)

        //multiply maxHeight by the inverse overlap percentage to get the new text size
        return coefficient * maxHeight
    }

    //val textFont: BitmapFont = UserInterface.getFont(BuildSiteFonts.Labtop_Unicase

    //var defaultFont = UserInterface.getFont(BuildSiteFonts.Labtop_Unicase, 10)

    fun DrawText(batch: Batch, rect: Rectangle, text: String, bsFont: BuildSiteFonts, align: Int = Align.center) {
        DrawText(batch, rect, text, bsFont, align, Color.BLACK)
    }

    fun DrawText(batch: Batch, rect: Rectangle, text: String, bsFont: BuildSiteFonts, align: Int, color: Color) {

        var textSize: Float = CalculateTextSize(text, bsFont, rect.width, rect.height)
        var font = UserInterface.getFont(bsFont, textSize.floor(), color)

        glyphLayout.setText(font, text)

        var targetY = 0f

        when (align) {
            Align.left, Align.center, Align.right -> targetY = rect.y + rect.height / 2f + glyphLayout.height / 2f
            Align.topLeft, Align.top, Align.topRight -> targetY = rect.y + rect.height
            Align.bottomLeft, Align.bottom, Align.bottomRight -> targetY = rect.y + glyphLayout.height
        }

        var hAlign: Int = 0
        when (align) {
            Align.left, Align.topLeft, Align.bottomLeft -> hAlign = Align.left
            Align.top, Align.center, Align.bottom -> hAlign = Align.center
            Align.right, Align.topRight, Align.bottomRight -> hAlign = Align.right
        }

        font.draw(batch, text, rect.x, targetY, rect.width, hAlign, false)
    }

    fun drawCentered(font: BitmapFont, spriteBatch: SpriteBatch?, text: String?, bounds: Rectangle, align: Int = Align.center) {
        glyphLayout.setText(font, text, Color.BLACK, bounds.width, Align.center, true)
        font.draw(spriteBatch, text, bounds.x, bounds.y + bounds.height / 2f + glyphLayout.height / 2f, bounds.width, align,true)
    }


//        private static RectF DrawScaledText(Canvas canvas, String value, RectF scaledRect, Paint paint, Layout.Alignment alignment) {
//            return DrawScaledText(canvas, value, scaledRect.left, scaledRect.top, scaledRect.right - scaledRect.left, scaledRect.bottom - scaledRect.top, paint, alignment);
//        }
//        public static RectF DrawScaledText(Canvas canvas, String value, RectF container, RectF textDimensions, Paint paint, Layout.Alignment alignment) {
//            RectF scaledRect = RectModule.getScaledRect(textDimensions, container, alignment);
//            return DrawScaledText(canvas, value, scaledRect, paint, alignment);
//        }
//        public static RectF DrawScaledText(Canvas canvas, String value, RectF container, RectF textDimensions, int color, Layout.Alignment alignment) {
//            return DrawScaledText(canvas, value, container, textDimensions, getTextPaint(color), alignment);
//        }
//        public static RectF DrawText(Canvas canvas, String value, RectF container, int color, Layout.Alignment alignment) {
//            return DrawText(canvas, value, container, getTextPaint(color), alignment);
//        }


    fun getOrdinallySuffexedNumber(value: Int): String {
        val sufixes = arrayOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        return when (value % 100) {
            11, 12, 13 -> value.toString() + "th"
            else -> "$value${sufixes[value % 10]}"
        }
    }
}
