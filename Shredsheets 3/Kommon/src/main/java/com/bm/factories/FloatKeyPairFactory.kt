package com.bm.factories

import com.bm.common.FloatKeyPair

object FloatKeyPairFactory : Factory<FloatKeyPair>() {

    override fun getNewItem(): FloatKeyPair {
        return FloatKeyPair(0f, 0f)
    }

    operator fun get(keyOne: Float, keyTwo: Float): FloatKeyPair {
        val point = this.get()

        point.keyOne = keyOne
        point.keyTwo = keyTwo
        return point
    }

    operator fun get(pair: FloatKeyPair): FloatKeyPair {
        val point = this.get()
        point.keyOne = pair.keyOne
        point.keyTwo = pair.keyTwo
        return point
    }

    override fun dispose(item: FloatKeyPair) {
        item.setZero()
        super.dispose(item)
    }
}
