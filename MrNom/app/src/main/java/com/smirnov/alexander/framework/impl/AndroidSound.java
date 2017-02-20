package com.smirnov.alexander.framework.impl;

import android.media.SoundPool;

import com.smirnov.alexander.framework.Sound;

public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;
    static boolean mute = false;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public boolean isMute() {
        return AndroidSound.mute;
    }

    public void setMute(boolean isMute) {
        AndroidSound.mute = isMute;
    }

    public void play(float volume) {
        if (!this.isMute())
            soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }
}

