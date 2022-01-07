package com.bm.common

import java.util.LinkedList

class ConstrainedQueue<T>(private var limit: Int) : LinkedList<T>() {

    override fun add(element: T): Boolean {
        super.add(element)
        while (size > limit) {
            super.remove()
        }
        return true
    }

    fun setLimit(value: Int) {
        this.limit = value
    }

    fun setLimit(): Int {
        return this.limit
    }
}