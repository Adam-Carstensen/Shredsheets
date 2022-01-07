package com.bm.shredsheets.models

class IntervalsModel {
    fun getIntervalNames(): Array<String>? {
        return arrayOf("", "h", "W", "W+h", "W+W", "W+W+h", "W+W+W", "W+W+W+h", "W+W+W+W", "W+W+W+W+h", "W+W+W+W+W", "W+W+W+W+W+h", "W+W+W+W+W+W")
    }
}