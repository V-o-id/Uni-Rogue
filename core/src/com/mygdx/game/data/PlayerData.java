package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a player and its data.
 * It is used to store the data of a player.
 * A player is identified by its name.
 * There are several attributes that can be stored for a player.
 * Implements Serializable to be able to save the data.
 */
public class PlayerData implements Serializable {

    /**
     * The name of the player. Acts as id, thus must be unique.
     */
    private String name;
    /**
     * The character the player has selected.
     */
    private String playerCharacter;
    /**
     * The date the player was created.
     */
    private String creationDate;
    /**
     * The total score of the player.
     */
    private long totalScore;
    /**
     * The total kills of the player.
     */
    private long totalKills;
    /**
     * The total games played by the player.
     */
    private long totalGamesPlayed;
    /**
     * The total play time of the player in seconds.
     */
    private long playTimeInSeconds;
    /**
     * The total levels completed by the player.
     */
    private long totalLevelsCompleted;
    /**
     * The total rooms beaten by the player.
     */
    private long totalRoomsBeaten;

    /*
    -------------GETTER----------------
     */

    public String getName() {
        return name;
    }
    public long getTotalScore() {
        return totalScore;
    }
    public long getTotalKills() {
        return totalKills;
    }
    public long getTotalGamesPlayed() {
        return totalGamesPlayed;
    }
    public long getPlayTimeInSeconds() {
        return playTimeInSeconds;
    }
    public long getTotalLevelsCompleted() {
        return totalLevelsCompleted;
    }
    public long getTotalRoomsBeaten() {
        return totalRoomsBeaten;
    }
    public String getCreationDate() {
        return creationDate;
    }


    /**
     * Two players are equal if they have the same name.
     * @param o the object to compare to
     * @return true if the players are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        PlayerData that = (PlayerData) o;
        return name.equalsIgnoreCase(that.name);
    }

    /**
     * The hash code of a player is the hash code of its name.
     * @return the hash code of the player
     */
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /**
     * needed for json serialization and deserialization
     */
    public PlayerData() {}

    /**
     * Creates a new player with default values and given name.
     * @param name the name of the player
     * @return the new player
     */
    private PlayerData createNewPlayer(String name) {
        PlayerData p = new PlayerData();
        FileHandle file = Gdx.files.local("files/selectedCharacter.txt");
        if(file.exists() && !file.readString().equals("")) {
            p.playerCharacter = file.readString();
        } else {
            p.playerCharacter = "*";
        }
        p.name = name;
        p.creationDate = String.valueOf(new Date());
        p.totalScore = 0;
        p.totalKills = 0;
        p.totalGamesPlayed = 0;
        p.playTimeInSeconds = 0;
        p.totalLevelsCompleted = 0;
        p.totalRoomsBeaten = 0;
        return p;
    }

    /**
     * Creates a new player if the player does not exist, otherwise loads the player from the file
     * @param name the name of the player
     */
    public PlayerData(String name) {
        PlayerMap pMap = PlayerMap.getPlayerMap();
        PlayerData currentPlayer = pMap.getPlayerByName(name);
        if(currentPlayer == null) { // if player does not exist, create new player
            currentPlayer = createNewPlayer(name);
        }
        this.name = currentPlayer.name;
        this.playerCharacter = currentPlayer.playerCharacter;
        this.creationDate = currentPlayer.creationDate;
        this.totalScore = currentPlayer.totalScore;
        this.totalKills = currentPlayer.totalKills;
        this.totalGamesPlayed = currentPlayer.totalGamesPlayed;
        this.playTimeInSeconds = currentPlayer.playTimeInSeconds;
        this.totalLevelsCompleted = currentPlayer.totalLevelsCompleted;
        this.totalRoomsBeaten = currentPlayer.totalRoomsBeaten;
    }

    /**
     * Sets the player character of the player.
     * @param playerCharacter the player character to set as string
     */
    public void setPlayerCharacter(String playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    /**
     * @return the player character as string
     */
    public String getPlayerCharacter() {
        return playerCharacter;
    }


    /**
     * Updates the player data with the data of the given game instance.
     * Is then saved to the file.
     * Should be called after a game has ended or when the player wants to save the game.
     * @param g the game instance to update the player data with
     */
    public void playedGame(GameInstance g) {
        incrementTotalGamesPlayed();
        changeTotalScore(g.getScore());
        changeTotalKillCount(g.getKills());
        changeRoomsBeaten(g.getBeatenRooms());
        changePlayTimeInSeconds(g.getDurationInSeconds());
        changeTotalLevelsCompleted(g.getLevel()-1); // -1 because here it is beaten levels, not played levels
        savePlayerdata();
    }

    /**
     * Saves the player data with the current data to the file.
     */
    public void savePlayerdata() {
        PlayerMap playerList = PlayerMap.getPlayerMap();
        playerList.addPlayer(this);
    }

    private void incrementTotalGamesPlayed() {
        totalGamesPlayed++;
    }

    private void changeTotalKillCount(int amount) {
        totalKills += amount;
    }
    private void changeTotalScore(int amount) {
        totalScore += amount;
    }
    private void changePlayTimeInSeconds(long amount) {
        playTimeInSeconds += amount;
    }

    private void changeTotalLevelsCompleted(int amount) {
        totalLevelsCompleted += amount;
    }

    private void changeRoomsBeaten(int amount) {
        totalRoomsBeaten += amount;
    }

}
