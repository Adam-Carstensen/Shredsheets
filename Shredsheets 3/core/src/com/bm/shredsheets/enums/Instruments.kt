package com.bm.shredsheets.enums

enum class Instruments(val value: Int, val cleanName: String) {
    Guitar6(0, "Guitar"), Guitar7(1, "7 String Guitar"), Guitar8(2, "8 String Guitar"), Bass4(3, "Bass"), Bass5(4, "5 String Bass"), Ukulele(5, "Ukulele");

    companion object {
        var OrderedKeys = arrayOf(Guitar6, Guitar7, Guitar8, Bass4, Bass5, Ukulele)
    }
}
