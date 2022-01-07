package com.bm.proto

import com.bm.Application
import com.bm.Scene

class ProtoGame : Application() {
    var currentScene = TestingScene()
    override fun getScene(): Scene = currentScene
}