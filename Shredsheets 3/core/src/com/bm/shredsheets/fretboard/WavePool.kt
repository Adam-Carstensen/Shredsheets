package com.bm.shredsheets.fretboard

import com.badlogic.gdx.audio.AudioDevice
import com.bm.maths.Maths
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

//Segment count represents how many tiny waves comprise a single standard wave.  Higher counts add overhead, lower counts reduce note tracking.  Sweet spot is around 20/sec.
class WavePool(val audioDevice: AudioDevice, val lengthInSeconds: Float, val sampleRate: Int, var segmentCount: Int = (lengthInSeconds * 20f).toInt()) {

    var pool: Array<ShortArray>

    var playingIndex: Int = 0
    var poolIndex: Int = 0
    var playing: Boolean = false
    var totalSize = lengthInSeconds * sampleRate
    var segmentSize = totalSize / segmentCount.toFloat()

    init {
        if (segmentCount < 2) segmentCount = 2
        pool = Array<ShortArray>(segmentCount) {
            ShortArray(segmentSize.toInt())
        }
    }


    fun queueWaveForm(wave: ShortArray) {
        //switch to the next slot in the pool, increasing for each queued waveform
        poolIndex = playingIndex + 1
        var poolWave = pool[poolIndex % pool.size] //modulus on the poolsize keeps us iterating in a loop starting at the beginning of the pool

        var poolWaveIndex = 0

        //iterating over every component of the wave
        for (i in wave.indices) {
            //if we're past the end of the current wave
            if (poolWaveIndex >= poolWave.size) {

                //increase the index by 1
                poolIndex += 1
                //grab the next pool
                poolWave = pool[poolIndex % pool.size]
                //set the position to the beginning of that wave
                poolWaveIndex = 0
            }

            //write to the current index of the current pool.
            //The wave is averaged with the existing wave, which allows it to become polyphonic, or playing two notes simultaniously.
            poolWave[poolWaveIndex] = ((wave[i].toFloat() + poolWave[poolWaveIndex].toFloat()) / 2f).toInt().toShort()

            //increase the position within the current WavePool wave
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

        //thread runs forever
        thread {

            //holding the current wave being addressed
            var wave: ShortArray

            //once started, the WavePool plays forever, it just plays silence when no notes are played
            while (true) {
                //iterate through each wave in the pool
                while (playingIndex < pool.size) {
                    //the current wave being addressed
                    wave = pool[playingIndex]

                    //play the current wave from the pool
                    audioDevice.writeSamples(wave, 0, wave.size)

                    //each wave is only played once, so set the current wave to silence
                    silence.copyInto(wave, 0, 0, wave.size)


                    //skip to the next wave in the pool
                    playingIndex++
                }
                playingIndex = 0
            }
        }
    }
}
