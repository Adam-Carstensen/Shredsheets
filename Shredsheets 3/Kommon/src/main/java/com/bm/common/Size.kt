package com.bm.common

class Size(
        var width: Int,
        var height: Int
)  {

    override fun hashCode(): Int {
        return width!!.hashCode() xor height!!.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Size) {
            width === other.width && height === other.height
        } else super.equals(other)
    }
}
