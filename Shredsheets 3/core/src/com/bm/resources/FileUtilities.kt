package com.bm.resources

import com.badlogic.gdx.Gdx

object FileUtilities {

    fun GetFiles(directoryPath: String): Array<String?> {
        val files = Gdx.files.internal(directoryPath).list()
        val fileList = arrayOfNulls<String>(files.size)
        for (i in files.indices)
            fileList[i] = files[i].name()
        return fileList
    }
}
