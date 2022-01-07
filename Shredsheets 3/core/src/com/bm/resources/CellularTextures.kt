package com.bm.resources

import com.badlogic.gdx.graphics.Texture
import com.bm.automik.CellMatrix

class CellularTextures {

    fun GetTexture(cellMatrix: CellMatrix<*, *>): Texture {
        return Texture(cellMatrix.getPixmap())
    }


}
