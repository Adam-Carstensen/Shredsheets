package com.bm

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences


class OptionsAndSettings {

    enum class Difficulty {
        EASY, NORMAL, HARD
    }

    companion object {
        private var prefs: Preferences? = null

        private val preferences: Preferences?
            get() {
                if (prefs == null) {
                    prefs = Gdx.app.getPreferences("GameOptions")
                }
                return prefs
            }

        var isMusicEnabled: Boolean
            get() = preferences!!.getBoolean("MusicEnabled", false)
            set(value) {
                preferences!!.putBoolean("MusicEnabled", value)
            }

        var isSoundEnabled: Boolean
            get() = preferences!!.getBoolean("SoundEnabled", false)
            set(value) {
                preferences!!.putBoolean("SoundEnabled", value)
            }
    }
}