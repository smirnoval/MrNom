package com.smirnov.alexander.mrnom;

import java.util.List;

import com.smirnov.alexander.framework.Game;
import com.smirnov.alexander.framework.Graphics;
import com.smirnov.alexander.framework.Input.TouchEvent;
import com.smirnov.alexander.framework.Screen;

public class SettingsScreen extends Screen {
    public SettingsScreen(Game game) {
        super(game);
        if(Settings.soundEnabled) {
            Assets.musicSettings.setVolume(1);
            Assets.musicSettings.setLooping(true);
            Assets.musicSettings.play();
        }
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(Settings.soundEnabled && Assets.musicSettings.isPlaying() == false) {
            Assets.musicSettings.play();
        }

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 256 && event.y > 416 ) {
                    game.setScreen(new MainMenuScreen(game));
                    if(Settings.soundEnabled){
                        Assets.click.play(1);
                        Assets.musicSettings.stop();
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.settingsscreen, 64, 100);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }

    @Override
    public void pause() {
        if(Settings.soundEnabled) {
            Assets.musicSettings.pause();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}