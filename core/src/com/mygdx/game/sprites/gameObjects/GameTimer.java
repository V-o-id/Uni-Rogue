package com.mygdx.game.sprites.gameObjects;

import com.mygdx.game.data.GameInstance;
import com.mygdx.game.states.PlayState;

/**
 * Class that represents a timer for the game.
 * The timer is used to keep track of the time that has passed since the game started.
 * The timer is implemented as a thread and is started when the game starts.
 * Can be paused.
 */
public class GameTimer implements Runnable {

    /**
     * seconds that have passed since the game started
     */
    private long seconds;
    /**
     * boolean that indicates if the timer is running
     */
    private boolean running = true;

    private final PlayState playState;
    private final GameInstance gameInstanceData;

    /**
     * Constructor for the game timer
     * @param seconds value the timer should start with
     * @param playState the play state
     * @param gameInstanceData the game instance data
     */
    public GameTimer(long seconds, PlayState playState, GameInstance gameInstanceData) {
        this.seconds = seconds;
        this.playState = playState;
        this.gameInstanceData = gameInstanceData;
    }

    /**
     * Method that is called when the thread is started.
     * The timer is increased by one every second.
     * If the game is finished, the timer is stopped.
     * If the timer is paused, the timer is stopped. NOTE: if it is paused, it can not be resumed, create a new timer instead.
     * If the timer is paused, the thread is interrupted and the timer will only stop at full seconds.
     */
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
                if(running) {
                    if(gameInstanceData.getIsGameFinished()) { // if game is finished, stop timer
                        this.pause();
                        running = false;
                        return;
                    }
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

    /**
     * @return seconds that have passed since the game started
     */
    public long getSeconds() {
        return seconds;
    }

    /**
     * pauses the timer
     */
    public void pause() {
        running = false;
    }

}