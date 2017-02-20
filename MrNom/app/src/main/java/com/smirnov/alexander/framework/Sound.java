package com.smirnov.alexander.framework;

public interface Sound {
    public void play(float volume);

    public void dispose();

    public boolean isMute();

    public void setMute(boolean isMute);
}
