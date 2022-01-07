package com.bm.automik.music

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.sound.midi.Synthesizer

//import javax.sound.midi.Instrument
//import javax.sound.midi.Synthesizer

class DynamicSong(var synth: Synthesizer) {

    var position: Float = 0f

    var timeSignature: TimeSignature = TimeSignature(4,4)

    var tracks = LinkedList<Track>()

    init {
        createRhythmTracks()

    }

    private fun createRhythmTracks() {
        var percussionChannel = synth.channels[10]
        var instrument = synth.loadedInstruments[35]

        tracks.add(Track(percussionChannel, instrument, timeSignature))
    }

    fun play() {

        runBlocking {

            for (track in tracks) {
                launch {
                    track.play(position)
                }
            }

        }

    }



}