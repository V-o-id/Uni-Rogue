package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.HashMap;
import java.util.Map;

public class PlayerMap {

    private static PlayerMap playerMapInstance = null;

    // String is not the best choice for a key, but it is the easiest to use for now
    // as hash we use the hashcode of the playername as a string
    private final Map<String, Playerdata> playerMap = new HashMap<>();

    private PlayerMap() {
        readPlayerdata();
    }

    static PlayerMap getPlayerMap() {
        if(playerMapInstance == null) {
            playerMapInstance = new PlayerMap();
        }
        return playerMapInstance;
    }

    /**
     * Adds a player to the map, if the player is in the map, it will be overwritten
     * @param playerdata the player to add
     */
    void addPlayer(Playerdata playerdata) {
        playerMap.put(String.valueOf(playerdata.hashCode()), playerdata);
        writePlayerdata();
    }

    public Playerdata getPlayerByName(String name) {
        if(name == null) {
            return null;
        }
        return playerMap.get(String.valueOf(name.hashCode()));
    }

    private void readPlayerdata() {

        FileHandle file = Gdx.files.local("playerdata.json");
        if(!file.exists()) { // if file does not exist there is no data to read
            return;
        }
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        try {
            Map<String, Playerdata> map = json.fromJson(HashMap.class, file);
            playerMap.putAll(map);
        } catch (Exception e) {
            // todo: handle exception
            System.out.println("WRONG DATA FORMAT. WHAT SHOULD WE DO?");
            System.err.println(e.getMessage());
        }

    }

    public void writePlayerdata() {

        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        String jsonStr = json.prettyPrint(this.playerMap);
        FileHandle file = Gdx.files.local("playerdata.json");
        file.writeString(jsonStr, false); // TODO : try to append instead of overwrite - so we dont have to write the whole file every time

    }

}