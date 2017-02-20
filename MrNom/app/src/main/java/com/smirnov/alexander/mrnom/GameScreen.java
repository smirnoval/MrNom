package com.smirnov.alexander.mrnom;

import java.util.List;
import android.graphics.Color;
import com.smirnov.alexander.framework.Game;
import com.smirnov.alexander.framework.Graphics;
import com.smirnov.alexander.framework.Input.TouchEvent;
import com.smirnov.alexander.framework.Pixmap;
import com.smirnov.alexander.framework.Screen;

public class GameScreen extends Screen {
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState state = GameState.Ready;
    World world;
    int oldScore = 0;
    String score = "0";

    public GameScreen(Game game) {
        super(game);
        world = new World();
        if(Settings.soundEnabled) {
            Assets.musicGame.setVolume(1);
            Assets.musicGame.setLooping(true);
            Assets.musicGame.play();
        }
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(Settings.soundEnabled && Assets.musicGame.isPlaying() == false && state == GameState.Running) {
            Assets.musicGame.play();
        }

        if(state == GameState.Ready)
            updateReady(touchEvents);
        if(state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if(state == GameState.Paused)
            updatePaused(touchEvents);
        if(state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 128 && event.y < 128) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 128 && event.y > 820) {
                    world.snake.turnLeft();
                }
                if(event.x > 512 && event.y > 820) {
                    world.snake.turnRight();
                }
            }
        }

        world.update(deltaTime);
        if(world.gameOver) {
            if(Settings.soundEnabled)
                Assets.bitten.play(1);
            state = GameState.GameOver;
        }
        if(oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
            if(Settings.soundEnabled)
                Assets.eat.play(1);
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        if(Settings.soundEnabled) {
            Assets.musicGame.pause();
        }
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 160 && event.x <= 480) {
                    if(event.y > 200 && event.y <= 300) {
                        if(Settings.soundEnabled) {
                            Assets.click.play(1);
                            Assets.musicGame.play();
                        }
                        state = GameState.Running;
                        return;
                    }
                    if(event.y > 310 && event.y < 400) {
                        if(Settings.soundEnabled) {
                            Assets.click.play(1);
                            Assets.musicGame.stop();
                        }
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 256 && event.x <= 384 &&
                        event.y >= 400 && event.y <= 528) {
                    if(Settings.soundEnabled) {
                        Assets.click.play(1);
                        Assets.musicGame.stop();
                    }
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
        drawWorld(world);
        if(state == GameState.Ready)
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver)
            drawGameOverUI();

        drawText(g, score, g.getWidth() / 2 - score.length()*40 / 2, g.getHeight() - 90);
    }

    private void drawWorld(World world) {
        Graphics g = game.getGraphics();
        Snake snake = world.snake;
        SnakePart head = snake.parts.get(0);
        Stain stain = world.stain;


        Pixmap stainPixmap = null;
        if(stain.type == Stain.TYPE_1)
            stainPixmap = Assets.stain1;
        if(stain.type == Stain.TYPE_2)
            stainPixmap = Assets.stain2;
        if(stain.type == Stain.TYPE_3)
            stainPixmap = Assets.stain3;
        int x = stain.x * 64;
        int y = stain.y * 64;
        g.drawPixmap(stainPixmap, x, y);

        int len = snake.parts.size();
        for(int i = 1; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            x = part.x * 64;
            y = part.y * 64;
            g.drawPixmap(Assets.tail, x, y);
        }

        Pixmap headPixmap = null;
        if(snake.direction == Snake.UP)
            headPixmap = Assets.headUp;
        if(snake.direction == Snake.LEFT)
            headPixmap = Assets.headLeft;
        if(snake.direction == Snake.DOWN)
            headPixmap = Assets.headDown;
        if(snake.direction == Snake.RIGHT)
            headPixmap = Assets.headRight;
        x = head.x * 64 + 32;
        y = head.y * 64 + 32;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.ready, 100, 200);
        g.drawLine(0, 830, 960, 830, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.buttons, 0, 0, 128, 256, 128, 128);
        g.drawLine(0, 830, 960, 830, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 832, 128, 128, 128, 128);
        g.drawPixmap(Assets.buttons, 512, 832, 0, 128, 128, 128);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.pause, 160, 200);
        g.drawLine(0, 830, 960, 830, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.gameOver, 124, 200);
        g.drawPixmap(Assets.buttons, 256, 400, 0, 256, 128, 128);
        g.drawLine(0, 830, 960, 830, Color.BLACK);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 40;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 400;
                srcWidth = 20;
            } else {
                srcX = (character - '0') * 40;
                srcWidth = 40;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 70);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if(state == GameState.Running)
            state = GameState.Paused;

        if(world.gameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
        if(Settings.soundEnabled) {
            Assets.musicGame.pause();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}


