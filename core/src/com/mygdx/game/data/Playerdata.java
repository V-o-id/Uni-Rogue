package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Date;
import java.util.Objects;

public class Playerdata {

    private String name; // acts as id
    private String playerCharacter;
    private String creationDate;
    private int totalScore;
    private int totalKills;
    private int totalGamesPlayed;
    private int totalGamesWon;
    private int totalGamesLost;
    private long playTimeInSeconds;
    private int totalLevelsCompleted;

    public String getName() {
        return name;
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
        FileHandle file = Gdx.files.local("selectedCharacter.txt");
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
            //pMap.addPlayer(currentPlayer);
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
    }

    public void setPlayerCharacter(String playerCharacter) {
        this.playerCharacter = playerCharacter;
    }
    public String getPlayerCharacter() {
        return playerCharacter;
    }
    public String getPlayerName() {
        return name;
    }


    public void playedGame(GameInstance g) {
        incrementTotalGamesPlayed();
        changeTotalScore(totalScore + g.getScore());
        changeTotalKillCount(totalKills + g.getKills());
        if(!g.isGameWon()){
            incrementTotalGamesLost();
        } else {
            incrementTotalGamesWon();
        }
        changePlayTimeInSeconds(playTimeInSeconds + g.getDurationInSeconds());
        changeTotalLevelsCompleted(totalLevelsCompleted + g.getLevel());
        commitPlayerdata();
    }

    private void commitPlayerdata() {
        PlayerMap pMap = PlayerMap.getPlayerMap();
        pMap.addPlayer(this);
    }
    

    public void savePlayerdata() {
        // save data to file
        PlayerMap playerList = PlayerMap.getPlayerMap();
        playerList.addPlayer(this);
        playerList.writePlayerdata();
        /*Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        String jsonStr = json.prettyPrint(this); // Create a list containing the two objects
        FileHandle file = Gdx.files.local("playerdata.json");
        file.writeString(jsonStr, false);*/
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


}
