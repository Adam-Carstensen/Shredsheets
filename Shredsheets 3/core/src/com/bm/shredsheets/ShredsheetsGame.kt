package com.bm.shredsheets

import com.bm.Application
import com.bm.shredsheets.scenes.ScaleScene
import com.bm.shredsheets.scenes.ShredsheetsTitleScene

class ShredsheetsGame : Application() {

    init {
        Instance = this
        setScene(ScaleScene())
    }

    companion object {
        lateinit var Instance: ShredsheetsGame
    }
}