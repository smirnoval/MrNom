package com.smirnov.alexander.mrnom;

import java.util.List;

import com.smirnov.alexander.framework.Game;
import com.smirnov.alexander.framework.Graphics;
import com.smirnov.alexander.framework.Input.TouchEvent;
import com.smirnov.alexander.framework.Screen;

public class MainMenuScreen extends Screen {
    public MainMenuScreen(Game game) {
        super(game);
        if(Settings.soundEnabled) {
            Assets.musicMainMenu.setVolume(1);
            Assets.musicMainMenu.setLooping(true);
            Assets.musicMainMenu.play();
        }

    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(Settings.soundEnabled && Assets.musicMainMenu.isPlaying() == false) {
            Assets.musicMainMenu.play();
        }
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event, 0, g.getHeight() - 128, 128, 128)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    else if(Settings.soundEnabled == false && Assets.musicMainMenu.isPlaying())
                        Assets.musicMainMenu.pause();
                }
                if(inBounds(event, 128, 440, 384, 84) ) {
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled){
                        Assets.musicMainMenu.stop();
                        Assets.click.play(1);
                    }
                    return;
                }
                if(inBounds(event, 128, 440 + 82, 384, 84) ) {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled){
                        Assets.musicMainMenu.stop();
                        Assets.click.play(1);
                    }
                    return;
                }
                if(inBounds(event, 128, 440 + 180, 384, 90) ) {
                    game.setScreen(new SettingsScreen(game));
                    if(Settings.soundEnabled){
                        Assets.musicMainMenu.stop();
                        Assets.click.play(1);
                    }
                    return;
                }
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.logo, 64, 40);
        g.drawPixmap(Assets.mainMenu, 128, 440);
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 0, 820, 0, 0, 128, 128);
        else
            g.drawPixmap(Assets.buttons, 0, 820, 128, 0, 128, 128);
    }

    public void pause() {
        Settings.save(game.getFileIO());
        if(Settings.soundEnabled && Assets.musicMainMenu.isPlaying()) {
            Assets.musicMainMenu.pause();
        }
    }

    public void resume() {
        if(Settings.soundEnabled) {
            Assets.musicMainMenu.play();
        }
    }

    public void dispose() {

    }
}
