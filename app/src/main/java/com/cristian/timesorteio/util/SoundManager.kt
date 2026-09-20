package com.cristian.timesorteio.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator

object SoundManager {
    private var toneGenerator: ToneGenerator? = null

    fun init(context: Context) {
        try {
            if (toneGenerator == null) {
                toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 15)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playClick() {
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 30)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun release() {
        toneGenerator?.release()
        toneGenerator = null
    }
}
