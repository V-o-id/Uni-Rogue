package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Date;

public class Playerdata {

    private String name; // acts as id
    private String playerCharacter;
    private String creationDate;
    private long totalScore;
    private long totalKills;
    private long totalGamesPlayed;
    private long totalGamesWon;
    private long totalGamesLost;
    private long playTimeInSeconds;
    private long totalLevelsCompleted;
    private long totalRoomsBeaten;

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
    public long getTotalGamesWon() {
        return totalGamesWon;
    }
    public long getTotalGamesLost() {
        return totalGamesLost;
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


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Playerdata that = (Playerdata) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /**
     * needed for json serialization and deserialization
     */
    public Playerdata() {}

    private Playerdata createNewPlayer(String name) {
        Playerdata p = new Playerdata();
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
        p.totalGamesWon = 0;
        p.totalGamesLost = 0;
        p.playTimeInSeconds = 0;
        p.totalLevelsCompleted = 0;
        p.totalRoomsBeaten = 0;
        return p;
    }

    /**
     * Creates a new player if the player does not exist, otherwise loads the player from the file
     * @param name the name of the player
     */
    public Playerdata(String name) {
        PlayerMap pMap = PlayerMap.getPlayerMap();
        Playerdata currentPlayer = pMap.getPlayerByName(name);
        if(currentPlayer == null) { // if player does not exist, create new player
            currentPlayer = createNewPlayer(name);
        }
        this.name = currentPlayer.name;
        this.playerCharacter = currentPlayer.playerCharacter;
        this.creationDate = currentPlayer.creationDate;
        this.totalScore = currentPlayer.totalScore;
        this.totalKills = currentPlayer.totalKills;
        this.totalGamesPlayed = currentPlayer.totalGamesPlayed;
        this.totalGamesWon = currentPlayer.totalGamesWon;
        this.totalGamesLost = currentPlayer.totalGamesLost;
        this.playTimeInSeconds = currentPlayer.playTimeInSeconds;
        this.totalLevelsCompleted = currentPlayer.totalLevelsCompleted;
        this.totalRoomsBeaten = currentPlayer.totalRoomsBeaten;
    }

    public void setPlayerCharacter(String playerCharacter) {
        this.playerCharacter = playerCharacter;
    }
    public String getPlayerCharacter() {
        return playerCharacter;
    }


    public void playedGame(GameInstance g) {
        incrementTotalGamesPlayed();
        changeTotalScore(g.getScore());
        changeTotalKillCount(g.getKills());
        if(!g.isGameWon()){
            incrementTotalGamesLost();
        } else {
            incrementTotalGamesWon();
        }
        changeRoomsBeaten(g.getBeatenRooms());
        changePlayTimeInSeconds(g.getDurationInSeconds());
        changeTotalLevelsCompleted(g.getLevel()-1); // -1 because here it is beaten levels, not played levels
        savePlayerdata();
    }

    public void savePlayerdata() {
        PlayerMap playerList = PlayerMap.getPlayerMap();
        playerList.addPlayer(this);
    }

    private void incrementTotalGamesPlayed() {
        totalGamesPlayed++;
    }
    private void incrementTotalGamesWon() {
        totalGamesWon++;
    }
    private void incrementTotalGamesLost() {
        totalGamesLost++;
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
