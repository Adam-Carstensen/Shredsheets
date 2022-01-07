package com.bm.resources

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture

class FilteredTexture : Texture {
    constructor(internalPath: String) : super(internalPath) {
        setFilter(TextureFilter.Linear, TextureFilter.Linear)
    }

    constructor(file: FileHandle) : super(file) {
        setFilter(TextureFilter.Linear, TextureFilter.Linear)
    }

    constructor(file: FileHandle, wrapX: TextureWrap, wrapY: TextureWrap) : this(file) {
        setWrap(wrapX, wrapY)
    }

}
