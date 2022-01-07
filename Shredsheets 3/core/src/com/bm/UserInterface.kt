package com.bm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Array
import com.bm.resources.Textures
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisTextField
import kotlin.math.max

object UserInterface {



    fun getCloseModalButtonStyle(): TextButton.TextButtonStyle {
        var font = getFont(BuildSiteFonts.FreeSansBold_Unicode, 64, color = Color(.9f, .3f, .3f, 1f))
        val style = TextButton.TextButtonStyle()
        style.font = font
        style.fontColor = Color(.9f, .5f, .3f, 1f)
        return style
    }

    fun getLabelStyle(size: Int): Label.LabelStyle {
        var font = getFont(BuildSiteFonts.Labtop_Unicase, size, Color.BLACK)
        val style = Label.LabelStyle()
        style.font = font
        style.fontColor = Color.BLACK
        return style
    }

    fun getTextButtonStyle(size: Int): VisTextButton.VisTextButtonStyle {
        var font = getFont(BuildSiteFonts.Labtop_Unicase, size, Color.BLACK)
        val style = VisTextButton.VisTextButtonStyle()
        style.font = font
        style.fontColor = Color.BLACK
        return style
    }


    fun getVisTextButtonCheckedStyle(size: Int): VisTextButton.VisTextButtonStyle {
        var font = getFont(BuildSiteFonts.Labtop_Unicase, size, Color.BLACK)
        val style = VisTextButton.VisTextButtonStyle()
        style.font = font
        style.fontColor = Color.BLACK

        style.checked = Textures.getColorDrawable(Color(.3f, .5f, .9f, .2f))
        return style
    }

    fun getTextFieldStyle(size: Int): VisTextField.VisTextFieldStyle {
        var font = getFont(BuildSiteFonts.Labtop_Unicase, size, Color.BLACK)
        val style = VisTextField.VisTextFieldStyle()
        style.font = font
        style.fontColor = Color.BLACK

        style.cursor = Textures.getColorDrawable(Color.WHITE)
        style.background = Textures.getColorDrawable(Color(.7f, .85f, 1f, .5f))
        style.focusedBackground = Textures.getColorDrawable(Color(.3f, .6f, 1f, .5f))
        style.selection = Textures.getColorDrawable(Color(.8f, .9f, 1f, .8f))

        return style
    }

    var fontCache = HashMap<Triple<BuildSiteFonts, Int, Color>, BitmapFont>()

    var minFontSize: Int = 4

    var lookupKeyTripple = Triple(BuildSiteFonts.Labtop_Unicase, 10, Color.BLACK)
    fun getFont(font: BuildSiteFonts, size: Int, color: Color, borderColor: Color = Color.BLACK, shadowOffsetX: Int = 0, shadowOffsetY: Int = 0, borderWidth: Float = 1f, shadowColor: Color = Color.BLACK): BitmapFont {
        lookupKeyTripple = Triple(font, size, color)

        var cachedFont: BitmapFont? = fontCache[lookupKeyTripple]
        if (cachedFont != null) {
            return cachedFont!!
        }

        println("Generating new font: $font, size: $size, color: $color")

        var fontPath: String

        when (font) {
            BuildSiteFonts.DIGISTRIP_B -> fontPath = "fonts\\digistrip_b.ttf"
            BuildSiteFonts.Labtop_Unicase_Wide -> fontPath = "fonts\\Labtop Unicase Wide.ttf"
            BuildSiteFonts.Labtop_Unicase -> fontPath = "fonts\\Labtop Unicase.ttf"
            BuildSiteFonts.McFoodPoisoning1 -> fontPath = "fonts\\McFoodPoisoning1.ttf"
            BuildSiteFonts.Adelon_Medium -> fontPath = "fonts\\Adelon-Medium.ttf"
            BuildSiteFonts.FreeSerif_Unicode -> fontPath = "fonts\\Unicode\\FreeSerif.ttf"
            BuildSiteFonts.FreeSerifBold_Unicode -> fontPath = "fonts\\Unicode\\FreeSerifBold.ttf"
            BuildSiteFonts.FreeSans_Unicode -> fontPath = "fonts\\Unicode\\FreeSans.ttf"
            BuildSiteFonts.FreeSansBold_Unicode -> fontPath = "fonts\\Unicode\\FreeSansBold.ttf"
        }

        val fontCharacters =
            "\u0000ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890♯♭Δ\"!`?'.,;:()[]{}<>|/@\\^$€-%+=#_&~*\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F\u00A0\u00A1\u00A2\u00A3\u00A4\u00A5\u00A6\u00A7\u00A8\u00A9\u00AA\u00AB\u00AC\u00AD\u00AE\u00AF\u00B0\u00B1\u00B2\u00B3\u00B4\u00B5\u00B6\u00B7\u00B8\u00B9\u00BA\u00BB\u00BC\u00BD\u00BE\u00BF\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C6\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D0\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D7\u00D8\u00D9\u00DA\u00DB\u00DC\u00DD\u00DE\u00DF\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2\u00F3\u00F4\u00F5\u00F6\u00F7\u00F8\u00F9\u00FA\u00FB\u00FC\u00FD\u00FE\u00FF"

        val generator = getFontGenerator(fontPath)
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.characters = fontCharacters
        parameter.size = max(size, minFontSize)
        parameter.borderWidth = borderWidth
        parameter.borderColor = borderColor
        parameter.color = color
        parameter.shadowOffsetX = shadowOffsetX
        parameter.shadowOffsetY = shadowOffsetY
        parameter.shadowColor = shadowColor

        println("Generating font: size: $size")

        val font: BitmapFont = generator.generateFont(parameter)
        //generator.dispose()
        font.region.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        fontCache[lookupKeyTripple] = font
        return font
    }

    var generatorMap = HashMap<String, FreeTypeFontGenerator>()

    fun getFontGenerator(fontPath: String): FreeTypeFontGenerator {
        var generator = generatorMap[fontPath]

        if (generator == null) {
            generator = FreeTypeFontGenerator(Gdx.files.internal(fontPath))
            generatorMap[fontPath] = generator
        }
        return generator
    }


}

enum class BuildSiteFonts {
    DIGISTRIP_B, Labtop_Unicase, Labtop_Unicase_Wide, McFoodPoisoning1, Adelon_Medium, FreeSerif_Unicode, FreeSerifBold_Unicode, FreeSans_Unicode, FreeSansBold_Unicode
}
