package com.bm.automik.music

import com.bm.maths.extensions.getIndex
import java.util.*
import javax.sound.midi.Instrument
import javax.sound.midi.MidiChannel

class Track(channel: MidiChannel, instrument: Instrument, timeSignature: TimeSignature) {
    var bars: LinkedList<Bar> = LinkedList()

    init {
        for (i in 0..10) {
            var bar = Bar(timeSignature)
            bars.add(bar)
        }
    }

    fun play(position: Float) {

        var barIndex = position.getIndex(bars.size - 1);

        for (index: Int in barIndex..bars.size)
        {
            var bar = bars[index]
            bar.play()
        }
    }

}