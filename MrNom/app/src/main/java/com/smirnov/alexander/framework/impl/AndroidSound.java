package com.smirnov.alexander.framework.impl;

import android.media.SoundPool;

import com.smirnov.alexander.framework.Sound;

public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;
    static boolean mute = false;
    static float soundVolume = 1;

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

    public void play() {
        if (!this.isMute())
            soundPool.play(soundId, soundVolume, soundVolume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }

    public float getVolume() {
        return AndroidSound.soundVolume;
    }

    public void setVolume(float volume) {
        AndroidSound.soundVolume = volume;
    }
}

