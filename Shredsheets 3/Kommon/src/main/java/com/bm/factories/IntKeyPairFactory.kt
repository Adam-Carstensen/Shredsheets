package com.bm.factories

import com.bm.common.IntKeyPair

object IntKeyPairFactory : Factory<IntKeyPair>() {
    override fun getNewItem(): IntKeyPair {
        return IntKeyPair(0, 0)
    }

    operator fun get(keyOne: Int, keyTwo: Int): IntKeyPair {
        val point = this.get()
        point.keyOne = keyOne
        point.keyTwo = keyTwo
        return point
    }

    operator fun get(pair: IntKeyPair): IntKeyPair {
        val point = this.get()
        point.keyOne = pair.keyOne
        point.keyTwo = pair.keyTwo
        return point
    }

    override fun dispose(item: IntKeyPair) {
        item.setZero()
        super.dispose(item)
    }
}
