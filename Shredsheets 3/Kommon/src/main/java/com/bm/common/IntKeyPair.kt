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
}
