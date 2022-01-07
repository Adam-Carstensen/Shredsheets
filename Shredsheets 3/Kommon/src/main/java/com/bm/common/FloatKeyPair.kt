package com.bm.common

class FloatKeyPair(
        private var KeyOne: Float,
        private var KeyTwo: Float
) : KeyPair<Float, Float>(KeyOne, KeyTwo) {

    fun setZero() {
        KeyOne = 0f
        KeyTwo = 0f
    }
}
