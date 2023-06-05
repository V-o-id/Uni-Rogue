package com.mygdx.game.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * This class represents a game instance.
 * It is used to store the data of a game instance.<br>
 * A game instance is identified by the player name and the start date time.<br>
 * There are several attributes that can be stored for a game instance.<br>
 * Implements Serializable to be able to save the data.
 */
public class GameInstance implements Serializable {

    /**
     * name of the player that played the game
     */
    private String playerName;
    /**
     * date and time the game was started as a String - otherwise it would not be serializable
     */
    private String startDateTime;
    /**
     * score of the game - is calculated based on other attributes
     */
    private int score = 0;
    /**
     * gold collected in the game
     */
    private int gold = 0;
    /**
     * kills in the game
     */
    private int kills = 0;
    /**
     * duration of the game in seconds
     */
    private long durationInSeconds = 0;
    /**
     * boolean that indicates if the game was finished
     */
    private boolean isGameFinished = false;
    /**
     * level reached in the game
     */
    private int level = 0;
    /**
     * rooms beaten in the game
     */
    private int beatenRooms = 0;

    /**
     * Checks if two game instances are equal.<br>
     * Two game instances are equal if the player name and the start date time are equal.
     * @param o the other game instance
     * @return true if the game instances are equal, false otherwise
     */
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

    /**
     * Calculates the hash code of the game instance based on the player name and the start date time.
     * @return the hash code of the game instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(playerName, startDateTime);
    }

    /**
     * sets the gold of the game instance
     * @param gold the gold to be set
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * used to increment the beaten rooms
     */
    public void incrementBeatenRooms() {
        beatenRooms++;
    }

    /**
     * @return the number of beaten rooms
     */
    public int getBeatenRooms() {
        return beatenRooms;
    }

    /**
     * empty constructor for serialization
     */
    public GameInstance() {}

    /**
     * Constructor that sets the player name and the start date time.
     * @param player the player that played the game as PlayerData - can be extracted from CurrentPlayer.getCurrentPlayer()
     */
    public GameInstance(PlayerData player) {
        this.playerName = player.getName();
        this.startDateTime = String.valueOf(new Date());
    }

    /**
     * @return the player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param kills the kills to be set
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * @param durationInSeconds the duration in seconds to be set
     */
    public void setDurationInSeconds(long durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }


    /**
     * if the game is finished, the score is calculated and the game is saved to the file ("gameHistory.json")
     * @param gameFinished true if the game is finished, false otherwise
     */
    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
        if(isGameFinished) {
            this.score = calculateScore(kills, gold, level, durationInSeconds);
            GamesMap gamesMap = GamesMap.getGamesMap();
            gamesMap.addGame(this);
        }
    }

    /**
     * increments the level by 1
     */
    public void incrementLevel() {
        level++;
    }

    /**
     * @param level the level to be set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the start date time as a String
     */
    public String getStartDateTime() {
        return startDateTime;
    }

    /**
     * @return the score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * @return the kills of the game
     */
    public int getKills() {
        return kills;
    }

    /**
     * @return the duration of the game in seconds as a long
     */
    public long getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * @return true if the game is finished, false otherwise
     */
    public boolean getIsGameFinished() {
        return isGameFinished;
    }

    /**
     * static method to calculate the score of a game instance based on the kills, gold, level and duration
     * @param kills the kills of the game
     * @param gold the gold of the game
     * @param level the level of the game
     * @param durationInSeconds the duration of the game in seconds
     * @return the score of the game as an int
     * score = kills * 10 + gold / 10 + level * 50 + durationInSeconds / 10 * -2
     */
    public static int calculateScore(int kills, int gold, int level, long durationInSeconds) {
            int killsScore = kills * 10;  // Each kill is worth 10 points
            int goldScore = gold / 10;  // Each 10 gold is worth 1 point
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
                ", isGameFinished=" + isGameFinished +
                ", level=" + level +
                '}';
    }

}
