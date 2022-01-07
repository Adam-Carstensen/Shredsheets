package com.bm.buildsite

import com.badlogic.gdx.physics.box2d.ContactListener
import com.bm.GameUtilities

object BsUtilities : GameUtilities() {
    override fun getGameGravity(): Float {
        return -15f;
    }

}