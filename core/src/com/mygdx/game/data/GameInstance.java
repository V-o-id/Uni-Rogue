package com.mygdx.game.data;

import java.util.Date;

public class GameInstance {

    private Playerdata player;
    private String startDateTime;
    private int score = 0; // TODO: CALCULATE SCORE BASED ON KILLS, TIME, DEATHS, GOLD, LEVEL, ETC.
    private int gold = 0;
    private int kills = 0;
    private long durationInSeconds = 0;
    private boolean isGameWon = false;
    private boolean isGameFinished = false;
    private int level = 0;

    public void setGold(int gold) {
        this.gold = gold;
    }

    public GameInstance() {}

    public GameInstance(Playerdata player) {
        this.player = player;
        this.startDateTime = String.valueOf(new Date());
    }

    public Playerdata getPlayer() {
        return player;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDurationInSeconds(long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
    public void setGameWon(boolean gameWon) {
        isGameWon = gameWon;
    }
    public boolean isGameWon() {
        return isGameWon;
    }
    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
        if(isGameFinished) {
            //TODO: SAVE GAMEINSTANCE TO FILE
            System.out.println(this);
        }
    }
    public void incrementLevel() {
        level++;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public String getStartDateTime() {
        return startDateTime;
    }
    public int getScore() {
        return score;
    }
    public int getKills() {
        return kills;
    }

    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    public static int calculateScore() {
        //todo
        return -1;
    }

    private void saveGameInstance() {
        this.player.playedGame(this);
    }

    @Override
    public String toString() {
        return "GameInstance{" +
                "player=" + player +
                ", startDateTime='" + startDateTime + '\'' +
                ", score=" + score +
                ", gold=" + gold +
                ", kills=" + kills +
                ", durationInSeconds=" + durationInSeconds +
                ", isGameWon=" + isGameWon +
                ", isGameFinished=" + isGameFinished +
                ", level=" + level +
                '}';
    }



}
