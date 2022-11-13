package com.bm.shredsheets.fretboard

import com.badlogic.gdx.math.Interpolation
import com.bm.maths.InterpolationPair
import com.bm.maths.extensions.vary
import com.bm.shredsheets.models.SessionModel
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin

object WaveForm {
    var sampleRate: Int = 44100

    fun getSilence(seconds: Float): ShortArray {
        return ShortArray((sampleRate * seconds).toInt())
    }

    fun getSawWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        return getInterpolationWave(seconds, frequency, Interpolation.linear, gain)
    }

    fun getInterpolationWave(seconds: Float, frequency: Float, interpolation: Interpolation, gain: Float): ShortArray {
        var wave = ShortArray((sampleRate * seconds).toInt())

        var segmentsPerRepeat: Float = sampleRate / frequency

        var segmentProgress: Float
        for (i in wave.indices) {
            segmentProgress = (i % segmentsPerRepeat) / segmentsPerRepeat
            wave[i] = interpolation.apply(Short.MIN_VALUE.toFloat() * gain, Short.MAX_VALUE.toFloat() * gain, segmentProgress).toInt().toShort()
        }
        return wave
    }

    fun getSinWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        var wave = ShortArray((sampleRate * seconds).toInt())

        var segmentsPerRepeat: Float = sampleRate / frequency

        var segmentProgress: Float
        for (i in wave.indices) {
            segmentProgress = (i % segmentsPerRepeat) / segmentsPerRepeat
            wave[i] = (sin(segmentProgress * Math.PI * 2f) * Short.MAX_VALUE * gain).toInt().toShort()
        }
        return wave
    }


    fun getSquareWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        var wave = ShortArray((sampleRate * seconds).toInt())

        var segmentsPerRepeat: Float = sampleRate / frequency

        var segmentProgress: Float
        for (i in wave.indices) {
            segmentProgress = (i % segmentsPerRepeat) / segmentsPerRepeat
            wave[i] = (sin(segmentProgress * Math.PI * 2f).roundToInt() * Short.MAX_VALUE * gain).toInt().toShort()
        }
        return wave
    }

    var initialWaveImpact = .6f
    var lowerWaveImpact = .1f
    var evenLowerWaveImpact = .03f
    var higherWaveImpact = .1f
    var evenHigherWaveImpact = .03f

    fun getGuitarWave(seconds: Float, frequency: Float, gain: Float): ShortArray {

        var sinWave = getReinforcedSinWave(seconds, frequency, gain)
        var squareWave = getReinforcedSquareWave(seconds, frequency, gain * .3f).vary(.1f)
        var sawWave = getReinforcedSawWave(seconds, frequency, gain * .2f).vary(.1f)

        var minorThirdWave = getReinforcedSinWave(seconds, frequency * 2f.pow(3/12f), gain * .05f)
        var thirdWave = getReinforcedSinWave(seconds, frequency * 2f.pow(4/12f), gain * .15f)
        var fifthWave = getReinforcedSinWave(seconds, frequency * 2f.pow(7/12f), gain * .1f)

        var intWave = IntArray(sinWave.size)
        for (i in intWave.indices) {
            intWave[i] = sinWave[i] + squareWave[i] + minorThirdWave[i] + thirdWave[i] + fifthWave[i] + sawWave[i]
        }

        var highestPeak = intWave.max() ?: 1

        var gainControlledMax = Short.MAX_VALUE * gain;
        var scalingQuotient = gainControlledMax / highestPeak.toFloat()

        var shortWave = ShortArray(intWave.size)

        for (i in shortWave.indices)
            shortWave[i] = (intWave[i] * scalingQuotient).roundToInt().toShort()

        return attackWave(shortWave)
    }


    fun getReinforcedSquareWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        var wave = getSquareWave(seconds, frequency, gain * initialWaveImpact)

        var lowerWave = getSquareWave(seconds, frequency * .5f, gain * lowerWaveImpact)
        var evenLowerWave = getSquareWave(seconds, frequency * .25f, gain * evenLowerWaveImpact)
        var higherWave = getSquareWave(seconds, frequency * 2f, gain * higherWaveImpact)
        var evenHigherWave = getSquareWave(seconds, frequency * 4f, gain * evenHigherWaveImpact)

        for (i in wave.indices) {
            wave[i] = (wave[i] + lowerWave[i]).toShort()
            wave[i] = (wave[i] + evenLowerWave[i]).toShort()
            wave[i] = (wave[i] + higherWave[i]).toShort()
            wave[i] = (wave[i] + evenHigherWave[i]).toShort()
        }
        return attackWave(wave)
    }

    fun getReinforcedSinWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        var wave = getSinWave(seconds, frequency, gain * initialWaveImpact)

        var lowerWave = getSinWave(seconds, frequency * .5f, gain * lowerWaveImpact)
        var evenLowerWave = getSinWave(seconds, frequency * .25f, gain * evenLowerWaveImpact)
        var higherWave = getSinWave(seconds, frequency * 2f, gain * higherWaveImpact)
        var evenHigherWave = getSinWave(seconds, frequency * 4f, gain * evenHigherWaveImpact)

        for (i in wave.indices) {
            wave[i] = (wave[i] + lowerWave[i]).toShort()
            wave[i] = (wave[i] + evenLowerWave[i]).toShort()
            wave[i] = (wave[i] + higherWave[i]).toShort()
            wave[i] = (wave[i] + evenHigherWave[i]).toShort()
        }
        return attackWave(wave)
    }

    fun getReinforcedSawWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        var wave = getSawWave(seconds, frequency, gain * initialWaveImpact)

        var lowerWave = getSawWave(seconds, frequency * .5f, gain * evenLowerWaveImpact)
        var evenLowerWave = getSawWave(seconds, frequency * .25f, gain * lowerWaveImpact)
        var higherWave = getSawWave(seconds, frequency * 2f, gain * higherWaveImpact)
        var evenHigherWave = getSawWave(seconds, frequency * 4f, gain * evenHigherWaveImpact)

        for (i in wave.indices) {
            wave[i] = (wave[i] + lowerWave[i]).toShort()
            wave[i] = (wave[i] + evenLowerWave[i]).toShort()
            wave[i] = (wave[i] + higherWave[i]).toShort()
            wave[i] = (wave[i] + evenHigherWave[i]).toShort()
        }
        return attackWave(wave)
    }

    var smoothInterpolation = InterpolationPair(Interpolation.smooth, Interpolation.smooth)
    fun smoothWave(wave: ShortArray): ShortArray {
        for (i in wave.indices) {
            val smoothingCoefficient = smoothInterpolation.apply(i / wave.size.toFloat())
            wave[i] = (wave[i] * smoothingCoefficient).toShort()
        }
        return wave
    }

    var attackInterpolation = InterpolationPair(Interpolation.elasticOut, Interpolation.smooth)
    fun attackWave(wave: ShortArray): ShortArray {
        for (i in wave.indices) {
            val smoothingCoefficient = attackInterpolation.apply(i / wave.size.toFloat())
            wave[i] = (wave[i] * smoothingCoefficient).toInt().toShort()
        }
        return wave
    }

    fun getAliasedSinWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        var wave = getSinWave(seconds, frequency, gain)
        return smoothWave(wave)
    }

    fun getFifthChordWave(seconds: Float, frequency: Float, gain: Float): ShortArray {
        var wave = getSinWave(seconds, frequency, gain)
        var fifthWave = getSinWave(seconds, (frequency * 2f.pow(7f/12f)), gain)

        for (i in wave.indices) {
            wave[i] = (wave[i] * (1f - Interpolation.smoother.apply(i / wave.size.toFloat()))).toShort()
            wave[i] = (wave[i] + (fifthWave[i] * (1f - Interpolation.smoother.apply(i / wave.size.toFloat())))).toShort()
        }

        return wave
    }

    fun getTriadChordWave(seconds: Float, frequency: Float, scalePosition: Int, gain: Float): ShortArray {
        var wave = getSinWave(seconds, frequency, gain * .5f)

        var stepsToThird = 0
        for (i in scalePosition until scalePosition + 4)
            stepsToThird += SessionModel.instance.scale.intervals[i % SessionModel.instance.scale.intervals.size]

        var thirdWave = getSinWave(seconds, frequency * 2f.pow(stepsToThird/12f), gain * .5f)
        var fifthWave = getSinWave(seconds, frequency * 2f.pow(7f/12f), gain * .5f)

        for (i in wave.indices) {
            wave[i] = (wave[i] + thirdWave[i]).toShort()
            wave[i] = (wave[i] + fifthWave[i]).toShort()

            wave[i] = (wave[i] * (1f - Interpolation.smoother.apply(i / wave.size.toFloat()))).toShort()

        }

        return wave
    }



    fun getSeventhChordWave(seconds: Float, frequency: Float, scalePosition: Int, gain: Float): ShortArray {

        var stepsToThird = 0
        for (i in scalePosition until scalePosition + 2)
            stepsToThird += SessionModel.instance.scale.intervals[i % SessionModel.instance.scale.intervals.size]

        var thirdHz = frequency * 2f.pow(stepsToThird/12f)

        var stepsToFifth = 0
        for (i in scalePosition until scalePosition + 4)
            stepsToFifth += SessionModel.instance.scale.intervals[i % SessionModel.instance.scale.intervals.size]

        var fifthHz = frequency * 2f.pow(stepsToFifth/12f)

        var stepsToSeventh = 0
        for (i in scalePosition until scalePosition + 6)
            stepsToSeventh += SessionModel.instance.scale.intervals[i % SessionModel.instance.scale.intervals.size]

        var seventhHz = frequency * 2f.pow(stepsToSeventh/12f)

        var wave = getReinforcedSinWave(seconds, frequency, gain * .5f)
        var thirdWave = getReinforcedSinWave(seconds, thirdHz, gain * .5f)
        var fifthWave = getReinforcedSinWave(seconds, fifthHz, gain * .5f)
        var seventhWave = getReinforcedSinWave(seconds, seventhHz, gain * .5f)

        for (i in wave.indices) {
            wave[i] = (wave[i] + thirdWave[i]).toShort()
            wave[i] = (wave[i] + fifthWave[i]).toShort()
            wave[i] = (wave[i] + seventhWave[i]).toShort()
        }

        wave = attackWave(wave)

        return wave
    }



    fun getSeventhGuitarChordWave(seconds: Float, frequency: Float, scalePosition: Int, gain: Float): ShortArray {

        var stepsToThird = 0
        for (i in scalePosition until scalePosition + 2)
            stepsToThird += SessionModel.instance.scale.intervals[i % SessionModel.instance.scale.intervals.size]

        var thirdHz = frequency * 2f.pow(stepsToThird/12f)

        var stepsToFifth = 0
        for (i in scalePosition until scalePosition + 4)
            stepsToFifth += SessionModel.instance.scale.intervals[i % SessionModel.instance.scale.intervals.size]

        var fifthHz = frequency * 2f.pow(stepsToFifth/12f)

        var stepsToSeventh = 0
        for (i in scalePosition until scalePosition + 6)
            stepsToSeventh += SessionModel.instance.scale.intervals[i % SessionModel.instance.scale.intervals.size]

        var seventhHz = frequency * 2f.pow(stepsToSeventh/12f)

        var wave = getGuitarWave(seconds, frequency, gain * .3f)
        var thirdWave = getGuitarWave(seconds, thirdHz, gain * .3f)
        var fifthWave = getGuitarWave(seconds, fifthHz, gain * .3f)
        var seventhWave = getGuitarWave(seconds, seventhHz, gain * .3f)

        for (i in wave.indices) {
            wave[i] = (wave[i] + thirdWave[i]).toShort()
            wave[i] = (wave[i] + fifthWave[i]).toShort()
            wave[i] = (wave[i] + seventhWave[i]).toShort()
        }

        wave = attackWave(wave)

        return wave
    }



}