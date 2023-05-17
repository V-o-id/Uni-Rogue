package com.mygdx.game.data;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Playerdata {

    private final String name; // acts as id
    private String playerCharacter;
    //private DateFormat creationDate;
    private int totalScore;
    private int totalKills;
    private int totalDeaths;
    private int totalGamesPlayed;
    private int totalGamesWon;
    private int totalGamesLost;
    private long playTimeInSeconds;

    public Playerdata(String name) {
        this.name = name;
        initPlayerdata();
    }

    private void initPlayerdata() {

        //get data from file
      /*  Json json = new Json();

        FileHandle file = Gdx.files.local("playerdata.json");
        String text = file.readString();
        Array playerdata = json.fromJson(Array.class, Playerdata.class, text);
        System.out.println(playerdata.size);*/

        //dummy data
        this.totalScore = 0;
        this.totalKills = 0;
        this.totalDeaths = 0;
        this.totalGamesPlayed = 0;
        this.totalGamesWon = 0;
        this.totalGamesLost = 0;
        this.playTimeInSeconds = 0;

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



    public void savePlayerdata() {
        // save data to file
        PlayerList playerList = new PlayerList();
        playerList.writePlayerdata();
        /*Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        String jsonStr = json.prettyPrint(this); // Create a list containing the two objects
        FileHandle file = Gdx.files.local("playerdata.json");
        file.writeString(jsonStr, false);*/
    }


}
