package com.bm.extensions

import com.badlogic.gdx.math.Rectangle


fun Rectangle.inset(x: Float, y: Float) {
    this.set(this.x + x, this.y + y, this.width - (x * 2f), this.height - (y * 2f))
}
