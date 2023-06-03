package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents a map of game instances.
 * It is used to store the data of all game instances.
 * A game instance is identified by the player name and the start date time.
 * There are several attributes that can be stored for a game instance.
 * Implements Serializable to be able to save the data.
 * Data is saved in JSON format but encrypted.
 */
public class GamesMap implements Serializable {

    /**
     * The instance of the singleton class.
     */
    private static GamesMap gameMapInstance = null;


    // should be stored in a more secure way :)
    private static final String SECRET_KEY = "mIäm!##S!c##(!!!";

    // file names for encrypted and decrypted data
    private static final String FILENAME_ENCRYPTED = "files/game-history-encrypted.ejson";
    private static final String FILENAME_DECRYPTED = "files/game-history.json";


    /**
     * The map that stores the game instances.
     * The key is the hashcode of the corresponding GameInstance.
     * The value is the GameInstance itself.
     */
    // String is not the best choice for a key, but it is the easiest to use for now - Integer makes problems with reading and writing from file
    // as hash we use the hashcode of the playername as a string
    private final Map<String, GameInstance> gameMap = new HashMap<>();

    /**
     * private constructor to prevent instantiation from outside. Use getPlayerMap() instead.
     */
    private GamesMap() {
        readGameData();
    }

    /**
     * @return the instance of the singleton class.
     */
    public static GamesMap getPlayerMap() {
        if(gameMapInstance == null) {
            gameMapInstance = new GamesMap();
        }
        return gameMapInstance;
    }

    /**
     * Adds a game to the map.
     * Then writes the data to the file.
     * @param g the game to add.
     */
    void addGame(GameInstance g) {
        gameMap.put(String.valueOf(g.hashCode()), g);
        writeGameData();
    }

    /**
     * @return List of all games with the given player name.
     */
    public List<GameInstance> getGamesByPlayerName(String name) {
        ArrayList<GameInstance> games = new ArrayList<>();
        for(GameInstance g : gameMap.values()) {
            if(g.getPlayerName().equals(name)) {
                games.add(g);
            }
        }
        return games;
    }

    /**
     * Reads the data from the encrypted file and stores it in to gameMap.
     * If the file does not exist, nothing happens. gameMap stays empty.
     * If the file is corrupted, it is deleted. gameMap stays empty.
     * If the file is empty, gameMap stays empty.
     */
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
            System.err.println("Error decrypting file " + in.getName());
            in.delete();
            out.delete();
            return;
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

    /**
     * Writes the data from gameMap to the encrypted file.
     */
    private void writeGameData() {
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);
        String jsonStr = json.toJson(this.gameMap);
        FileHandle file = Gdx.files.local(FILENAME_DECRYPTED);
        file.writeString(jsonStr, false);

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



    /*--------------GETTERS FOR SORTED SETS OF GAMES-----------------*/

    /**
     * @return SortedSet of all games sorted by duration descending, then score descending, then start date time ascending.
     */
    public SortedSet<GameInstance> getGamesByDuration() {
        SortedSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
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
                return o1.getStartDateTime().compareToIgnoreCase(o2.getStartDateTime());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }

    /**
     * @return SortedSet of all games sorted by name ascending, then score descending, then start date time ascending.
     */
    public SortedSet<GameInstance> getGamesSortedByPlayerName() {
        SortedSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
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
                return o1.getStartDateTime().compareToIgnoreCase(o2.getStartDateTime());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }

    /**
     * @return SortedSet of all games sorted by score descending, then start date time ascending.
     */
    public SortedSet<GameInstance> getGamesSortedByScore() {
        SortedSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
            @Override
            public int compare(GameInstance o1, GameInstance o2) {
                if(o1.getScore() > o2.getScore()) {
                    return -1;
                } else if(o1.getScore() < o2.getScore()) {
                    return 1;
                }
                return o1.getStartDateTime().compareToIgnoreCase(o2.getStartDateTime());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }

    /**
     * @return SortedSet of all games sorted by level descending, then score descending, then start date time ascending.
     */
    public SortedSet<GameInstance> getGamesSortedByLevels() {
        SortedSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
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
                return o1.getStartDateTime().compareToIgnoreCase(o2.getStartDateTime());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }

    /**
     * @return SortedSet of all games sorted by rooms beaten descending, then score descending, then start date time ascending.
     */
    public SortedSet<GameInstance> getGamesSortedByBeatenRooms() {
        SortedSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
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
                return o1.getStartDateTime().compareToIgnoreCase(o2.getStartDateTime());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }

    /**
     * @return SortedSet of all games sorted by startdatetime descending, then score descending, then name ascending.
     */
    public SortedSet<GameInstance> getGamesSortedByDateTime() {
        SortedSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
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

    /**
     * @return SortedSet of all games sorted by kills descending, then score descending, then start date time ascending.
     */
    public SortedSet<GameInstance> getGameSortedByKills() {
        SortedSet<GameInstance> games = new TreeSet<>(new Comparator<GameInstance>() {
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
                return o1.getStartDateTime().compareToIgnoreCase(o2.getStartDateTime());
            }
        });
        games.addAll(gameMap.values());
        return games;
    }

}