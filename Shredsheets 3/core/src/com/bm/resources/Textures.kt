package com.bm.resources

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

open class Textures {

    var confirmCheckmark: Texture = FilteredTexture(Gdx.files.internal("icons\\Confirm Checkmark.png"))

    var arrow: Texture = FilteredTexture(Gdx.files.internal("Arrow 64x64.png"))

    var playButtonTexture: Texture = FilteredTexture(Gdx.files.internal("Play Button.png"))
    var designButtonTexture: Texture = FilteredTexture(Gdx.files.internal("Design Button.png"))


    companion object {
        private var whiteTextureRegion: TextureRegion? = null
        fun getWhiteTexture(): TextureRegion {
            if (whiteTextureRegion != null) return whiteTextureRegion!!
            whiteTextureRegion = getColorTexture(Color.WHITE)
            return whiteTextureRegion!!
        }

        private var textureRegions: HashMap<Color, TextureRegion> = HashMap()
        fun getColorTexture(color: Color): TextureRegion {
            return textureRegions.getOrElse(color) {
                var texture = createTextureRegion(color)
                textureRegions[color] = texture
                return texture
            }
        }

        private var textureRegionDrawables: HashMap<Color, TextureRegionDrawable> = HashMap()
        fun getColorDrawable(color: Color): TextureRegionDrawable {
            return textureRegionDrawables.getOrElse(color) {
                var texture = getColorTexture(color)
                var drawable = TextureRegionDrawable(texture)
                textureRegionDrawables[color] = drawable
                return drawable
            }
        }

        private fun createTextureRegion(color: Color): TextureRegion {
            val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
            pixmap.setColor(color)
            pixmap.drawPixel(0, 0)
            var texture = Texture(pixmap) //remember to dispose of later
            pixmap.dispose()
            return TextureRegion(texture, 0, 0, 1, 1)!!
        }
    }

}