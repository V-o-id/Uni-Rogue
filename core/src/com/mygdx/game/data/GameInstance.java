package com.mygdx.game.data;

import java.util.Date;
import java.util.Objects;

public class GameInstance {

    private String playerName;
    private String startDateTime;
    private int score = 0;
    private int gold = 0;
    private int kills = 0;
    private long durationInSeconds = 0;
    private boolean isGameWon = false;
    private boolean isGameFinished = false;
    private int level = 0;
    private int beatenRooms = 0;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameInstance that = (GameInstance) o;

        if (!Objects.equals(playerName, that.playerName)) {
            return false;
        }
        return Objects.equals(startDateTime, that.startDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, startDateTime);
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    public void incrementBeatenRooms() {
        beatenRooms++;
    }

    public int getBeatenRooms() {
        return beatenRooms;
    }

    public GameInstance() {}

    public GameInstance(Playerdata player) {
        this.playerName = player.getPlayerName();
        this.startDateTime = String.valueOf(new Date());
    }

    public String getPlayerName() {
        return playerName;
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

    /**
     * if the game is finished, the score is calculated and the game is saved to the file ("gameHistory.json")
     * @param gameFinished true if the game is finished, false otherwise
     */
    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
        if(isGameFinished) {
            this.score = calculateScore(kills, gold, level, durationInSeconds);
            GamesMap gamesMap = GamesMap.getPlayerMap();
            gamesMap.addGame(this);
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

    public static int calculateScore(int kills, int gold, int level, long durationInSeconds) {
            int killsScore = kills * 10;  // Each kill is worth 10 points
            int goldScore = gold / 100;  // Each 100 gold is worth 1 point
            int levelScore = level * 50;  // Each level is worth 50 points
            int timeScore = (int) (durationInSeconds / 10) * -2;  // Each 10 seconds is worth -2 points
            return  killsScore + goldScore + levelScore + timeScore;
    }


    @Override
    public String toString() {
        return "GameInstance{" +
                "player=" + playerName +
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
