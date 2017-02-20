package com.smirnov.alexander.mrnom;

import java.util.List;

import com.smirnov.alexander.framework.Game;
import com.smirnov.alexander.framework.Graphics;
import com.smirnov.alexander.framework.Input.TouchEvent;
import com.smirnov.alexander.framework.Screen;

public class SettingsScreen extends Screen {
    public SettingsScreen(Game game) {
        super(game);
        Assets.musicSettings.setVolume(1);
        Assets.musicSettings.setLooping(true);
        Assets.musicSettings.play();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(!Assets.musicSettings.isPlaying()) {
            Assets.musicSettings.play();
        }

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 512 && event.y > 820 ) {
                    game.setScreen(new MainMenuScreen(game));
                    Assets.click.play(1);
                    Assets.musicSettings.stop();
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.settingsscreen, 128, 200);
        g.drawPixmap(Assets.buttons, 512, 820, 0, 128, 128, 128);
    }

    @Override
    public void pause() {
        Assets.musicSettings.pause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}