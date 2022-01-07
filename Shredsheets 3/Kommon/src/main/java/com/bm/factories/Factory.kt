package com.bm.factories

import java.util.LinkedList
import java.util.Queue

abstract class Factory<T> protected constructor() {
    private var stack: Queue<T> = LinkedList()
    protected open val initialCapacity: Int = 5000

    init {
        for (i in 0 until initialCapacity) {
            val newItem = getNewItem()
            stack.add(newItem)
        }
    }

    protected abstract fun getNewItem(): T

    fun get(): T {
        return if (stack.isEmpty()) {
            getNewItem()
        } else stack.remove()
    }

    fun peek(): T {
        if (stack.isEmpty()) {
            stack.add(getNewItem())
        }
        return stack.peek()
    }

    open fun dispose(item: T) {
        stack.add(item)
    }

    open fun dispose(items: Collection<T>) {
        for (item in items) stack.add(item)
    }
}
