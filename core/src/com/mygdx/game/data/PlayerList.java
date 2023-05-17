package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;

public class PlayerList {

    ArrayList<Playerdata> playerList = new ArrayList<Playerdata>();

    public PlayerList() {
        playerList.add(new Playerdata("Player1"));
        playerList.add(new Playerdata("Player2"));
        playerList.add(new Playerdata("Player3"));
        playerList.add(new Playerdata("Player4"));
        playerList.add(new Playerdata("Player5"));
        playerList.add(new Playerdata("Player6"));
        playerList.add(new Playerdata("Player7"));
        playerList.add(new Playerdata("Player8"));
        playerList.add(new Playerdata("Player9"));
        playerList.add(new Playerdata("Player10"));
    }

    public void writePlayerdata() {

        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        String jsonStr = json.prettyPrint(this); // Create a list containing the two objects
        FileHandle file = Gdx.files.local("playerdata.json");
        file.writeString(jsonStr, false);

    }

}
