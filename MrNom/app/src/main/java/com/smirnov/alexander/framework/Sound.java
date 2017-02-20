package com.smirnov.alexander.framework;

public interface Sound {
    public void play();

    public void dispose();

    public boolean isMute();

    public void setMute(boolean isMute);

    public float getVolume();

    public void setVolume(float volume);
}
