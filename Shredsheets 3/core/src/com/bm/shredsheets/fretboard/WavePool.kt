package com.bm.shredsheets.fretboard

import com.badlogic.gdx.audio.AudioDevice
import com.bm.maths.Maths
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class WavePool(val audioDevice: AudioDevice, val lengthInSeconds: Float, val sampleRate: Int, val segmentCount: Int) {

    var pool: Array<ShortArray>

    var playingIndex: Int = 0
    var poolIndex: Int = 0
    var playing: Boolean = false
    var totalSize = lengthInSeconds * sampleRate
    var segmentSize = totalSize / segmentCount.toFloat()

    init {


        pool = Array<ShortArray>(segmentCount) {
            ShortArray(segmentSize.toInt())
        }
    }


    fun queueWaveForm(wave: ShortArray) {
        //clearPool()

        poolIndex = playingIndex + 1
        var poolWave = pool[poolIndex % pool.size]

        var poolWaveIndex = 0

        for (i in wave.indices) {
            if (poolWaveIndex >= poolWave.size) {
                poolIndex += 1
                poolWave = pool[poolIndex % pool.size]
                poolWaveIndex = 0
            }

            poolWave[poolWaveIndex] = Maths.average (poolWave[poolWaveIndex].toFloat(), wave[i].toFloat(), .8f).toInt().toShort() // poolWave[poolWaveIndex] + wave[i]) * .75f).toInt().toShort()
            poolWaveIndex += 1
        }
    }

    fun clearPool() {
        for (wave in pool)
            silence.copyInto(wave, 0, 0, wave.size)
    }

    var silence = WaveForm.getSilence(lengthInSeconds)

    fun playWavePool() {
        if (playing) return

        playing = true

        thread {
            var wave: ShortArray
            while (true) {
                while (playingIndex < pool.size) {
                    wave = pool[playingIndex].clone()

                    thread {
                        silence.copyInto(pool[playingIndex], 0, 0, pool[playingIndex].size)
                    }

                    audioDevice.writeSamples(wave, 0, wave.size)
                    playingIndex++
                }
                playingIndex = 0
            }
        }
    }
}
