package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class GamesMap {

    private static GamesMap gameMapInstance = null;


    private static final String SECRET_KEY = "mIäm!##S!c##(!!!";
    private static final String FILENAME_ENCRYPTED = "files/gamehistoryEncrypted.json";
    private static final String FILENAME_DECRYPTED = "files/gamehistory.json";


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

        File in = new File(FILENAME_ENCRYPTED);
        if(!in.exists()) { // if file does not exist there is no data to read
            return;
        }
        File out = new File(FILENAME_DECRYPTED);
        try {
            // decrypt the file to read
            FileEncrypter.decrypt(SECRET_KEY, in, out);
        } catch (CryptoException e) {
            System.out.println("Error decrypting file " + in.getName());
        }

        FileHandle file = Gdx.files.local(FILENAME_DECRYPTED);
        if(!file.exists()) { // if file does not exist there is no data to read
            return;
        }
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        try {
            Map<String, GameInstance> map = json.fromJson(HashMap.class, file);
            if(map == null) { // empty file
                return;
            }
            gameMap.putAll(map);
        } catch (Exception e) {
            // file is corrupted
            file.writeString("", false);
        }

        out.delete(); // delete the decrypted file, we dont need it anymore

    }

    private void writeGameData() {

        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);
        String jsonStr = json.toJson(this.gameMap);
        FileHandle file = Gdx.files.local(FILENAME_DECRYPTED);
        file.writeString(jsonStr, false); // TODO : try to append instead of overwrite - so we dont have to write the whole file every time

        File in = new File(FILENAME_DECRYPTED);
        File out = new File(FILENAME_ENCRYPTED);
        try{
            // encrypt the data
            FileEncrypter.encrypt(SECRET_KEY, in, out);
        } catch (CryptoException e) {
            System.out.println("Error encrypting file " + in.getName());
        }
        // delete the unencrypted file
        in.delete();

    }



    /*
     SORTING METHODS
     */

    public TreeSet<GameInstance> getGamesByDuration() {
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

    public TreeSet<GameInstance> getGamesSortedByPlayerName() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.getPlayerName().compareToIgnoreCase(o2.getPlayerName()) > 0) {
                    return 1;
                } else if(o1.getPlayerName().compareToIgnoreCase(o2.getPlayerName()) < 0) {
                    return -1;
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

    public TreeSet<GameInstance> getGamesSortedByGameWon() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.isGameWon() && !o2.isGameWon()) {
                    return -1;
                } else if(!o1.isGameWon() && o2.isGameWon()) {
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

    public TreeSet<GameInstance> getGamesSortedByScore() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
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

    public TreeSet<GameInstance> getGamesSortedByLevels() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.getLevel() > o2.getLevel()) {
                    return -1;
                } else if(o1.getLevel() < o2.getLevel()) {
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

    public TreeSet<GameInstance> getGamesSortedByBeatenRooms() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.getBeatenRooms() > o2.getBeatenRooms()) {
                    return -1;
                } else if(o1.getBeatenRooms() < o2.getBeatenRooms()) {
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

    public TreeSet<GameInstance> getGamesSortedByDateTime() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.getStartDateTime().compareTo(o2.getStartDateTime()) > 0) {
                    return -1;
                } else if(o1.getStartDateTime().compareTo(o2.getStartDateTime()) < 0) {
                    return 1;
                }
                if(o1.getScore() > o2.getScore()) {
                    return -1;
                } else if(o1.getScore() < o2.getScore()) {
                    return 1;
                }
                return o1.getPlayerName().compareToIgnoreCase(o2.getPlayerName());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }

    public TreeSet<GameInstance> getGameSortedByKills() {
        TreeSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.getKills() > o2.getKills()) {
                    return -1;
                } else if(o1.getKills() < o2.getKills()) {
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



}