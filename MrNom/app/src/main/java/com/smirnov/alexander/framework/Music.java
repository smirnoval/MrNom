package com.smirnov.alexander.framework;

public interface Music {
    public void play();

    public void stop();

    public void pause();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isMute();

    public void setMute(boolean isMute);

    public float getVolume();

    public boolean isPlaying();

    public boolean isStopped();

    public boolean isLooping();

    public void dispose();

    public void reset();
}
