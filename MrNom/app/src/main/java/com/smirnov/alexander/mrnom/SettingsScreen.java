package com.smirnov.alexander.mrnom;

import android.graphics.Color;

import java.util.List;

import com.smirnov.alexander.framework.Game;
import com.smirnov.alexander.framework.Graphics;
import com.smirnov.alexander.framework.Input.TouchEvent;
import com.smirnov.alexander.framework.Screen;
import com.smirnov.alexander.framework.impl.AndroidMusic;

public class SettingsScreen extends Screen {

    public SettingsScreen(Game game) {
        super(game);
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
                if(event.x > 500 && event.y < 128 ) {
                    Assets.click.play();
                    Assets.musicSettings.stop();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
                if(event.x > 250 && event.y > 240 && event.x < 390 && event.y < 370 ) {
                    Assets.click.play();
                    Settings.showFPS = !Settings.showFPS;
                    return;
                }
                if(event.x > 50 && event.y > 500 && event.x < 190 && event.y < 630 ) {
                    Assets.click.play();
                    float volume = Assets.musicSettings.getVolume();
                    if (volume < 1)
                        Assets.musicSettings.setVolume(volume+0.1f);
                    return;
                }
                if(event.x > 450 && event.y > 500 && event.x < 600 && event.y < 630 ) {
                    Assets.click.play();
                    float volume = Assets.musicSettings.getVolume();
                    if (volume > 0.1f)
                        Assets.musicSettings.setVolume(volume-0.1f);
                    return;
                }
                if(event.x > 50 && event.y > 750 && event.x < 190 && event.y < 880 ) {
                    Assets.click.play();
                    float volume = Assets.click.getVolume();
                    if (volume < 1)
                        Assets.click.setVolume(volume+0.1f);
                    return;
                }
                if(event.x > 450 && event.y > 750 && event.x < 600 && event.y < 880 ) {
                    Assets.click.play();
                    float volume = Assets.click.getVolume();
                    if (volume > 0.1f)
                        Assets.click.setVolume(volume-0.1f);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        String musicVolume = String.format("%.1f", Assets.musicSettings.getVolume());
        String soundVolume = String.format("%.1f", Assets.click.getVolume());
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.mainMenu, 130, 40, 0, 200, 400, 100);
        g.drawPixmap(Assets.buttons, 500, 0, 0, 128, 128, 128);
        g.drawPixmap(Assets.settingsMenu, 120, 140, 0, 0, 400, 110);
        if(Settings.showFPS) {
            g.drawPixmap(Assets.fpsOn, 250, 240);
            g.drawDebugText(20, 20, Color.BLACK, String.format("FPS: %.0f", 1/deltaTime));
        }
        else
            g.drawPixmap(Assets.fpsOff, 250, 240);
        g.drawPixmap(Assets.settingsMenu, 120, 400, 0, 110, 400, 100);
        g.drawPixmap(Assets.plus, 50, 500);
        g.drawPixmap(Assets.minus, 450, 500);
        g.drawText(g, Assets.numbers, musicVolume, 250, 530);
        g.drawPixmap(Assets.settingsMenu, 120, 650, 0, 200, 400, 100);
        g.drawPixmap(Assets.plus, 50, 750);
        g.drawPixmap(Assets.minus, 450, 750);
        g.drawText(g, Assets.numbers, soundVolume, 250, 780);
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