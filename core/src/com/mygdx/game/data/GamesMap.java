package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.*;

public class GamesMap {

    private static GamesMap gameMapInstance = null;

    // String is not the best choice for a key, but it is the easiest to use for now
    // as hash we use the hashcode of the playername as a string
    private final Map<String, GameInstance> gameMap = new HashMap<>();

    private GamesMap() {
        readGameData();
    }

    public static GamesMap getPlayerMap() {
        if(gameMapInstance == null) {
            gameMapInstance = new GamesMap();
        }
        return gameMapInstance;
    }

    void addGame(GameInstance g) {
        gameMap.put(String.valueOf(g.hashCode()), g);
        writeGameData();
    }

    public ArrayList<GameInstance> getGamesByPlayerName(String name) {
        ArrayList<GameInstance> games = new ArrayList<>();
        for(GameInstance g : gameMap.values()) {
            if(g.getPlayerName().equals(name)) {
                games.add(g);
            }
        }
        return games;
    }

    private void readGameData() {

        FileHandle file = Gdx.files.local("files/gamehistory.json");
        if(!file.exists()) { // if file does not exist there is no data to read
            return;
        }
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        try {
            Map<String, GameInstance> map = json.fromJson(HashMap.class, file);
            gameMap.putAll(map);
        } catch (Exception e) {
            // todo: handle exception
            System.out.println("WRONG DATA FORMAT. WHAT SHOULD WE DO?");
            System.err.println(e.getMessage());
        }

    }

    public TreeSet<GameInstance> getGamesByHighestPlayTime() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.getDurationInSeconds() > o2.getDurationInSeconds()) {
                    return -1;
                } else if(o1.getDurationInSeconds() < o2.getDurationInSeconds()) {
                    return 1;
                }
                if(o1.getScore() > o2.getScore()) {
                    return -1;
                } else if(o1.getScore() < o2.getScore()) {
                    return 1;
                }
                return o1.getStartDateTime().compareTo(o2.getStartDateTime());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }


    private void writeGameData() {
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);
        String jsonStr = json.prettyPrint(this.gameMap);
        FileHandle file = Gdx.files.local("files/gamehistory.json");
        file.writeString(jsonStr, false); // TODO : try to append instead of overwrite - so we dont have to write the whole file every time
    }

}