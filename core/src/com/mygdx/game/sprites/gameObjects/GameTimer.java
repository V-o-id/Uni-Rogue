package com.mygdx.game.sprites.gameObjects;

import com.mygdx.game.data.GameInstance;
import com.mygdx.game.states.PlayState;

public class GameTimer implements Runnable {

    private long seconds;
    private boolean running = true;
    private final PlayState playState;
    GameInstance gameInstanceData;

    public GameTimer(long seconds, PlayState playState, GameInstance gameInstanceData) {
        this.seconds = seconds;
        this.playState = playState;
        this.gameInstanceData = gameInstanceData;
    }
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                if(running) {
                    seconds++;
                    gameInstanceData.setDurationInSeconds(seconds);
                    playState.setGameTimerText("Time: " + seconds);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
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