package com.bm.shredsheets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.bm.resources.FilteredTexture
import com.bm.resources.Textures

object ShredsheetsTextures : Textures() {
    var titleScreenImage: Texture = FilteredTexture(Gdx.files.internal("Shredsheets Title Image.png"))
}