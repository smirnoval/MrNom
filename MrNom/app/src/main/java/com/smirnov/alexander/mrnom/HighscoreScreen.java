package com.smirnov.alexander.mrnom;

import android.graphics.Color;

import java.util.List;

import com.smirnov.alexander.framework.Game;
import com.smirnov.alexander.framework.Graphics;
import com.smirnov.alexander.framework.Screen;
import com.smirnov.alexander.framework.Input.TouchEvent;

public class HighscoreScreen extends Screen {
    String lines[] = new String[5];

    public HighscoreScreen(Game game) {
        super(game);
        Assets.musicHighscore.play();

        for (int i = 0; i < 5; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(!Assets.musicHighscore.isPlaying()) {
            Assets.musicHighscore.play();
        }

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 128 && event.y > 820) {
                    Assets.click.play();
                    Assets.musicHighscore.stop();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.mainMenu, 140, 40, 0, 110, 400, 100);
        if(Settings.showFPS)
            g.drawDebugText(20, 20, Color.BLACK, String.format("FPS: %.0f", 1/deltaTime));

        int y = 200;
        for (int i = 0; i < 5; i++) {
            g.drawText(g, Assets.numbers, lines[i], 30, y);
            y += 100;
        }

        g.drawPixmap(Assets.buttons, 0, 820, 128, 128, 128, 128);
    }

    @Override
    public void pause() {
        Assets.musicHighscore.pause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
