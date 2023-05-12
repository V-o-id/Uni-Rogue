package com.mygdx.game.sprites.gameObjects;

import com.mygdx.game.states.PlayState;

public class GameTimer implements Runnable {

    private long seconds;
    private boolean running = true;
    private final PlayState playState;

    public GameTimer(long seconds, PlayState playState) {
        this.seconds = seconds;
        this.playState = playState;
    }
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                if(running) {
                    seconds++;
                    playState.setGameTimerText("Time: " + seconds);
                }
            } catch (InterruptedException e) {
                // do nothing
            }
        }
    }
    public long getSeconds() {
        return seconds;
    }
    public void pause() {
        running = false;
    }
    public void resume() {
        running = true;
        run();
    }

}