package com.bm.automik.music

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.sound.midi.MidiSystem
import javax.sound.midi.Synthesizer
import kotlin.concurrent.thread

class Bar(timeSignature: TimeSignature) {

    fun play() {


    }

    private var midiSynth: Synthesizer = MidiSystem.getSynthesizer().apply { open() }


    public fun loadMusic() {
        return
        thread(true) {
            (0..128).forEach {
                println("it: $it")
                PlayPhrase(it)
            }
        }
    }

    private fun PlayPhrase(instrumentNumber: Int) {
        runBlocking {

            launch {
                playNote(instrumentNumber, 0, 60, 100, 200)
                playNote(instrumentNumber, 0, 69, 50, 333)
            }

            playNote(instrumentNumber, 1, 43, 89, 470)
            playNote(instrumentNumber, 1, 37, 134, 733)
        }
    }

    suspend fun playNote(instrumentNumber: Int, midiChannelNumber: Int, noteNumber: Int, noteVelocity: Int, holdNoteTime: Long) {
        val instrumentsArray = midiSynth.defaultSoundbank.instruments
        val instrument = instrumentsArray[instrumentNumber]
        val midiChannels = midiSynth.channels
        midiSynth.loadInstrument(instrument)
        var channel = midiChannels[midiChannelNumber]

        channel.programChange(instrument.patch.program)
        channel.noteOn(noteNumber, noteVelocity)
        println("Playing instrument: ${instrument.name}")

        delay(holdNoteTime)
        channel.noteOff(noteNumber)
    }

}