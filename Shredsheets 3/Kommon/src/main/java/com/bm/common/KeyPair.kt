package com.bm.common

open class KeyPair<TKeyOne, TKeyTwo>(
        var keyOne: TKeyOne,
        var keyTwo: TKeyTwo
) {

    override fun hashCode(): Int {
        return keyOne!!.hashCode() xor keyTwo!!.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is KeyPair<*, *>) {
            other.keyOne === keyOne && other.keyTwo === keyTwo
        } else super.equals(other)
    }
}

