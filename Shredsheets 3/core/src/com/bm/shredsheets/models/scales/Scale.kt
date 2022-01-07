package com.bm.shredsheets.models.scales

import com.bm.shredsheets.TextModule
import com.bm.shredsheets.models.SessionModel

abstract class Scale {
    private var modeNames: Array<String>? = null

    protected val defaultMode: Int
        protected get() = 0

    abstract val scaleIntervals: IntArray
    val intervals: IntArray
        get() {
            val intervals = scaleIntervals
            val modalIntervals = IntArray(intervals.size)
            var modalOffset = 0
            val modeIndex = mode
            if (modeIndex == 0) return intervals
            for (i in intervals.indices) {
                if (intervals[i] == 0) modalOffset++
                if (i - modalOffset == modeIndex) break
            }
            for (i in modalIntervals.indices) {
                val modalPosition = i + modeIndex + modalOffset
                modalIntervals[i] = intervals[modalPosition % intervals.size]
            }
            return modalIntervals
        }

    fun GetDegree(degree: Int): Int {
        return degree
    }

    //Degree names are all based on a comparison to the Ionian's intervals, which is the default mode of Major
    private val ionianCumulativeIntervals = intArrayOf(0, 2, 4, 5, 7, 9, 11)

    private var degreeNamesValid = false
    var degreeNames: Array<String> = Array(scaleIntervals.count()) { "" }
        get() {
            //TODO: Document and refactor

            if (degreeNamesValid) return field

            val intervals = intervals
            var intervalCumulative = 0 //intervals[0];
            val degrees = modalDegrees // getDegrees();

            for (i in degrees.indices) {
                val degree = degrees[i]
                if (degree == 0) {
                    field[i] = ""
                    intervalCumulative += intervals[i]
                    continue
                }
                val ionianCumulative = ionianCumulativeIntervals[degree - 1]
                var degreeName = ""
                if (intervalCumulative < ionianCumulative) for (j in 0 until ionianCumulative - intervalCumulative) degreeName += "♭" else if (intervalCumulative > ionianCumulative) for (j in 0 until intervalCumulative - ionianCumulative) degreeName += "♯"
                degreeName += TextModule.getOrdinallySuffexedNumber(degree) //0 based array, 1 based degree naming system
                field[i] = degreeName
                val intervalStepSize = intervals[i]
                //if (intervalStepSize == 0) degreeNames[i] = "";
                intervalCumulative += intervalStepSize
            }

            degreeNamesValid = true

            return field!!
        }


    val intervalNames: Array<String>
        get() {
            return getIntervalNames(intervals)
        }

    fun getIntervalNames(intervals: IntArray): Array<String> {
        val names = Array<String>(intervals.size) { i : Int -> getIntervalName(intervals[i]) }
        return names
    }

    fun getIntervalName(i: Int): String {
        return when (i) {
            1 -> "h"
            2 -> "W"
            3 -> "W+h"
            4 -> "W+W"
            else -> i.toString()
        }
    }

    abstract val name: String

    abstract fun getModeNames(): Array<String>

    val currentModeName: String
        get() {
            modeNames = getModeNames()
            return if (modeNames == null) TextModule.getOrdinallySuffexedNumber(mode) + " mode" else modeNames!![mode % modeNames!!.size]
        }

    override fun toString(): String {
        return name
    } // + (((modeNames = getModeNames()) == null) ? "" : " (" + modeNames[mode] + ")");

    var mode: Int = defaultMode
        set(value) {

        //if (this.mode == mode) return;
        field = value
        degreeNamesValid = false
        val modalHighlighting = modalHighlighting[value]
        SessionModel.instance.theme.degreeHighlightingVector = modalHighlighting

//        FragmentActivity fretboardActivity = SessionModel.getInstance().fretboardActivity;
//        if (fretboardActivity != null) fretboardActivity.getIntent().putExtra("mode", mode);
        SessionModel.instance.invalidateViews()
    }

    abstract val getEnum: Scales

    protected open val modalHighlighting: Array<BooleanArray>
        protected get() = arrayOf(booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false), booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false), booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false), booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false), booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false), booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false), booleanArrayOf(true, false, true, false, true, false, true, false, false, false, false, false))
    abstract val degrees: Array<IntArray>
    //        if (mode == 0) return degrees;
//        int[] rotatedDegrees = new int[degrees.length];
//        for (int i = 0; i < degrees.length; i++) {
//            rotatedDegrees[i] = degrees[(i + mode) % degrees.length];
//        }
//
//        int[] modalDegrees = new int[degrees.length];
//
//        modalDegrees[0] = 1;
//
//        boolean second = intervals[0] != 0;
//        if (second) modalDegrees[1] = 2;
//
//        boolean third = intervals[1] != 0;
//
//        boolean fourth = intervals[2] != 0;
//
////        int fourthDegreePosition = addUntilIndex(intervals, 4);
////        boolean flatFifth = fourthDegreePosition == 6;
////        if (flatFifth) {
////            if (fourth) modalDegrees[4] = 5;
////            else modalDegrees[4] = 4;
////        }
//
//        //int fifthDegreePosition = addUntilIndex(intervals, 6);
//        boolean fifth = intervals[3] != 0;
//
//        if (fourth && !fifth) {
//            int fourthDegreePosition = addUntilIndex(intervals, 4);
//            if (fourthDegreePosition == 7) {
//                fourth = false;
//                fifth = true;
//            }
//        }
//
//        if (third && !fourth) {
//            int fourthDegreePosition = addUntilIndex(intervals, 3);
//            if (fourthDegreePosition == 5) {
//                third = false;
//                fourth = true;
//            }
//        }
//
//        if (third) modalDegrees[2] = 3;
//        if (fourth) modalDegrees[3] = 4;
//        if (fifth) modalDegrees[4] = 5;
//
//        boolean sixth = intervals[4] != 0;
//        if (sixth) {
//            int sixthDegreePosition = addUntilIndex(intervals, 5);
//            if (sixthDegreePosition == 7) {
//                modalDegrees[5] = 5;
//                sixth = false;
//            }
//        }
//
//        boolean seventh = intervals[5] != 0;
//
//        if ((sixth & !seventh) || intervals.length == 6) {
//            int sixthDegreePosition = addUntilIndex(intervals, 5);
//            if (sixthDegreePosition >= 10) {
//                sixth = false;
//                seventh = true;
//            }
//        }
//
//        if (sixth && seventh && intervals[6] == 0) {
//            int finalDegreePosition = addUntilIndex(intervals, 6);
//            if (finalDegreePosition >= 10) {
//                sixth = false;
//                seventh = true;
//            } else {
//                sixth = true;
//                seventh = false;
//            }
//
//        }
//
////        if (intervals.length == 6) {
////            int finalDegreePosition = addUntilIndex(intervals, intervals.length);
////            if (finalDegreePosition >= 10)
////        }
//
//
//        if (sixth) modalDegrees[5] = 6;
//        if (seventh) modalDegrees[6] = 7;
//
//        return modalDegrees;

    //
//        int mode = getMode();
//        for (int i = 0; i < modalDegrees.length; i++) {
//            modalDegrees[i] = degrees[(mode + i) % (modalDegrees.length)] - mode;
//        }
//        return modalDegrees;
    val modalDegrees: IntArray
        get() {
            val intervals = intervals
            val degrees = degrees
            return degrees[mode]
            //        if (mode == 0) return degrees;
            //        int[] rotatedDegrees = new int[degrees.length];
            //        for (int i = 0; i < degrees.length; i++) {
            //            rotatedDegrees[i] = degrees[(i + mode) % degrees.length];
            //        }
            //
            //        int[] modalDegrees = new int[degrees.length];
            //
            //        modalDegrees[0] = 1;
            //
            //        boolean second = intervals[0] != 0;
            //        if (second) modalDegrees[1] = 2;
            //
            //        boolean third = intervals[1] != 0;
            //
            //        boolean fourth = intervals[2] != 0;
            //
            ////        int fourthDegreePosition = addUntilIndex(intervals, 4);
            ////        boolean flatFifth = fourthDegreePosition == 6;
            ////        if (flatFifth) {
            ////            if (fourth) modalDegrees[4] = 5;
            ////            else modalDegrees[4] = 4;
            ////        }
            //
            //        //int fifthDegreePosition = addUntilIndex(intervals, 6);
            //        boolean fifth = intervals[3] != 0;
            //
            //        if (fourth && !fifth) {
            //            int fourthDegreePosition = addUntilIndex(intervals, 4);
            //            if (fourthDegreePosition == 7) {
            //                fourth = false;
            //                fifth = true;
            //            }
            //        }
            //
            //        if (third && !fourth) {
            //            int fourthDegreePosition = addUntilIndex(intervals, 3);
            //            if (fourthDegreePosition == 5) {
            //                third = false;
            //                fourth = true;
            //            }
            //        }
            //
            //        if (third) modalDegrees[2] = 3;
            //        if (fourth) modalDegrees[3] = 4;
            //        if (fifth) modalDegrees[4] = 5;
            //
            //        boolean sixth = intervals[4] != 0;
            //        if (sixth) {
            //            int sixthDegreePosition = addUntilIndex(intervals, 5);
            //            if (sixthDegreePosition == 7) {
            //                modalDegrees[5] = 5;
            //                sixth = false;
            //            }
            //        }
            //
            //        boolean seventh = intervals[5] != 0;
            //
            //        if ((sixth & !seventh) || intervals.length == 6) {
            //            int sixthDegreePosition = addUntilIndex(intervals, 5);
            //            if (sixthDegreePosition >= 10) {
            //                sixth = false;
            //                seventh = true;
            //            }
            //        }
            //
            //        if (sixth && seventh && intervals[6] == 0) {
            //            int finalDegreePosition = addUntilIndex(intervals, 6);
            //            if (finalDegreePosition >= 10) {
            //                sixth = false;
            //                seventh = true;
            //            } else {
            //                sixth = true;
            //                seventh = false;
            //            }
            //
            //        }
            //
            ////        if (intervals.length == 6) {
            ////            int finalDegreePosition = addUntilIndex(intervals, intervals.length);
            ////            if (finalDegreePosition >= 10)
            ////        }
            //
            //
            //        if (sixth) modalDegrees[5] = 6;
            //        if (seventh) modalDegrees[6] = 7;
            //
            //        return modalDegrees;

            //
            //        int mode = getMode();
            //        for (int i = 0; i < modalDegrees.length; i++) {
            //            modalDegrees[i] = degrees[(mode + i) % (modalDegrees.length)] - mode;
            //        }
            //        return modalDegrees;
        }

    fun addUntilIndex(items: IntArray, endExclusive: Int): Int {
        var sum = 0
        for (i in 0 until endExclusive) sum += items[i]
        return sum
    }

    //public abstract String[] getChords();
    open val chords: Array<String>
        get() = arrayOf("M7", "m7", "m7", "M7", "7", "m7", "m7ø")

    fun getChord(i: Int): String {
        val chords = chords
        return chords[(i + mode) % chords.size]
    }
}
