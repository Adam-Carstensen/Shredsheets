package com.bm.proto

import com.badlogic.gdx.graphics.g2d.Batch
import com.bm.Scene

class TestingScene : Scene() {
//    var environment = jetbrains.exodus.env.Environments.newInstance("./protoData")
//    var entityStore = jetbrains.exodus.entitystore.PersistentEntityStores.newInstance("./protoEntities")

    override fun load(): Boolean {
//        var tx = entityStore.beginTransaction()
//
//        var systemEntity = tx.getAll("System").first
//        if (systemEntity == null) systemEntity = tx.newEntity("System")
//
//        var property = systemEntity.getProperty("Test")
//
//        var values: Array<Int> = arrayOf(1, 2, 3, 4)
//
//        systemEntity.setProperty("Test", 3)
//
//        tx.flush()
        return true
    }

    override fun draw(batch: Batch) {

    }

    override fun unload() {
        TODO("Not yet implemented")
    }
}