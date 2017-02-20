package com.smirnov.alexander.framework.impl;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.smirnov.alexander.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener {
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;
    static boolean mute = false;
    static float musicVolume = 1;

    public AndroidMusic(AssetFileDescriptor assetDescriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
                    assetDescriptor.getStartOffset(),
                    assetDescriptor.getLength());
            mediaPlayer.prepare();
            isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
            this.setLooping(true);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't load music");
        }
    }

    public void dispose() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    public float getVolume() {
        return AndroidMusic.musicVolume;
    }

    public boolean isMute() {
        return AndroidMusic.mute;
    }

    public void setMute(boolean isMute) {
        AndroidMusic.mute = isMute;
        this.pause();
    }

    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isStopped() {
        return !isPrepared;
    }
    public void pause() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }
    public void play() {
        if (this.isMute())
            return;
        else if (mediaPlayer.isPlaying())
            return;
        try {
            synchronized (this) {
                if (!isPrepared)
                    mediaPlayer.prepare();
                this.setVolume(musicVolume);
                mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        AndroidMusic.musicVolume = volume;
        mediaPlayer.setVolume(volume, volume);
    }

    public void stop() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            synchronized (this) {
                isPrepared = false;
            }
        }

    }

    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared = false;
        }
    }

    public void reset() {
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }
}
