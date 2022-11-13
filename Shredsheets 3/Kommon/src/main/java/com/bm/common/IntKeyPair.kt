package com.bm.common

class IntKeyPair(
        private var KeyOne: Int,
        private var KeyTwo: Int
) : KeyPair<Int, Int>(KeyOne, KeyTwo) {
    fun setZero() {
        KeyOne = 0
        KeyTwo = 0
    }

    override fun hashCode(): Int {
        return KeyOne xor KeyTwo
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as IntKeyPair

        if (KeyOne != other.KeyOne) return false
        if (KeyTwo != other.KeyTwo) return false

        return true
    }
}
