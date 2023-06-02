package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents a map of players.
 * It is used to store the data of all players.
 * There are several attributes that can be stored for a player.
 * Implements Serializable to be able to save the data.
 * The player map is a singleton, so it can be accessed from everywhere.
 * The playermap will be saved in JSON format - but encrypted.
 */
public class PlayerMap implements Serializable {

    /**
     * The instance of the player map. It is a singleton.
     */
    private static PlayerMap playerMapInstance = null;

    // should be stored in a more secure way :)
    private static final String SECRET_KEY = "mIam!##Sec)))!!!";

    //filenames
    private static final String FILENAME_ENCRYPTED = "files/playerdataEncrypted.ejson";
    private static final String FILENAME_DECRYPTED = "files/playerdata.json";


    /**
     * The map that stores the players.
     * The key is the hashcode of the playername as a string.
     * The value is the PlayerData class.
     */
    // String is not the best choice for a key, but it is the easiest to use - Integer makes problems with reading and writing from file
    // as hash we use the hashcode of the playername as a string
    private final Map<String, PlayerData> playerMap = new HashMap<>();

    /**
     * Private constructor, because it is a singleton.
     */
    private PlayerMap() {
        readPlayerdata();
    }

    /**
     * Returns the instance of the player map.
     * If the instance does not exist, it will be created from the file.
     * @return the instance of the player map
     */
    public static PlayerMap getPlayerMap() {
        if(playerMapInstance == null) {
            playerMapInstance = new PlayerMap();
        }
        return playerMapInstance;
    }

    /**
     * Adds a player to the map, if the player is in the map, it will be overwritten
     * @param playerdata the player to add
     */
    void addPlayer(PlayerData playerdata) {
        playerMap.put(String.valueOf(playerdata.hashCode()), playerdata);
        writePlayerdata();
    }

    /**
     * Returns PlayerData of a player by the name of the player.
     * @param name the name of the player
     * @return the PlayerData of the player, or null if the player is not in the map
     */
    public PlayerData getPlayerByName(String name) {
        if(name == null) {
            return null;
        }
        return playerMap.get(String.valueOf(name.hashCode()));
    }

    /**
     * reads the PlayerData from the file and stores it in the map
     * if the file does not exist, nothing will be done
     * if the file is corrupted, it will be overwritten and the map will be empty
     */
    private void readPlayerdata() {

        File in = new File(FILENAME_ENCRYPTED);
        if(!in.exists()) { // if file does not exist there is no data to read
            return;
        }
        File out= new File(FILENAME_DECRYPTED);
        try {
            // decrypt the file to read
            FileEncrypter.decrypt(SECRET_KEY, in, out);
        } catch (CryptoException e) {
            System.err.println("Error decrypting file " + in.getName());
            in.delete(); // delete the corrupted file
            return;
        }

        FileHandle file = Gdx.files.local(FILENAME_DECRYPTED);
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        try {
            Map<String, PlayerData> map = json.fromJson(HashMap.class, file);
            if(map == null) { // empty file
                return;
            }
            playerMap.putAll(map);
        } catch (Exception e) {
            // file is corrupted
            file.writeString("", false);
            playerMap.clear();
        }

        out.delete(); // delete the decrypted file, we don't need it anymore

    }

    /**
     * writes the playerdata to the file FILENAME_ENCRYPTED
     * if the file does not exist, it will be created
     * encrypts the file
     */
    private void writePlayerdata() {

        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        String jsonStr = json.toJson(this.playerMap);
        FileHandle file = Gdx.files.local(FILENAME_DECRYPTED);
        file.writeString(jsonStr, false);

        File in = new File(FILENAME_DECRYPTED);
        File out = new File(FILENAME_ENCRYPTED);
        try{
            // encrypt the data
            FileEncrypter.encrypt(SECRET_KEY, in, out);
        } catch (CryptoException e) {
            System.err.println("Error encrypting file " + in.getName());
            out.delete(); // delete the corrupted file
            in.delete();  // delete the unencrypted file because it is corrupted
            return;
        }

        in.delete();  // delete the unencrypted file

    }




    /*-------------GETTER FOR SORTED SETS OF PLAYERS----------------*/

    /**
     * @return a sorted set of all players sorted by name
     * Ascending order
     */
    public SortedSet<PlayerData> getPlayersSortedByName() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                // name is unique, so we can use it as only comparison
                return o2.getName().compareToIgnoreCase(o1.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }

    /**
     *  @return a sorted set of all players sorted by score, if equal, the name will be used
     *  Descending order
     */
    public SortedSet<PlayerData> getPlayersSortedByScore() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                long l = Long.compare(o1.getTotalScore(), o2.getTotalScore());
                if(l > 0) {
                    return -1;
                } else if(l < 0) {
                    return 1;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }

    /**
     * @return a sorted set of all players sorted by kills, if equal, the name will be used
     * Descending order
     */
    public SortedSet<PlayerData> getPlayersSortedByKills() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                long l = Long.compare(o1.getTotalKills(), o2.getTotalKills());
                if(l > 0) {
                    return -1;
                } else if(l < 0) {
                    return 1;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }

    /**
     * @return a sorted set of all players sorted by games played, if equal, the name will be used
     * Descending order
     */
    public SortedSet<PlayerData> getPlayersSortedByGamesPlayed() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                long l = Long.compare(o1.getTotalGamesPlayed(), o2.getTotalGamesPlayed());
                if(l > 0) {
                    return -1;
                } else if(l < 0) {
                    return 1;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }

    /**
     * @return a sorted set of all players sorted by levels completed, if equal, the name will be used
     * Descending order
     */
    public SortedSet<PlayerData> getPlayersSortedByLevels() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                long l = Long.compare(o1.getTotalLevelsCompleted(), o2.getTotalLevelsCompleted());
                if(l > 0) {
                    return -1;
                } else if(l < 0) {
                    return 1;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }


    /**
     * @return a sorted set of all players sorted by playtime, if equal, the name will be used
     * Descending order
     */
    public SortedSet<PlayerData> getPlayersSortedByPlaytime() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                long l = Long.compare(o1.getPlayTimeInSeconds(), o2.getPlayTimeInSeconds());
                if(l > 0) {
                    return -1;
                } else if(l < 0) {
                    return 1;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }

    /**
     * @return a sorted set of all players sorted by beaten rooms, if equal, the name will be used
     * Descending order
     */
    public SortedSet<PlayerData> getPlayersSortedByBeatenRooms() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                long l = Long.compare(o1.getTotalRoomsBeaten(), o2.getTotalRoomsBeaten());
                if(l > 0) {
                    return -1;
                } else if(l < 0) {
                    return 1;
                }
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }

    /**
     * @return a sorted set of all players sorted by creation date, if equal, the name will be used
     * Descending order
     */
    public SortedSet<PlayerData> getPlayersSortedByDateTime() {
        SortedSet<PlayerData> players = new TreeSet<>(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                if(o1.getCreationDate().compareTo(o2.getCreationDate()) > 0) {
                    return 1;
                } else if(o1.getCreationDate().compareTo(o2.getCreationDate()) < 0) {
                    return -1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }


}