package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class PlayerMap {

    private static PlayerMap playerMapInstance = null;


    private static final String SECRET_KEY = "mIam!##Sec)))!!!";
    private static final String FILENAME_ENCRYPTED = "files/playerdataEncrypted.json";
    private static final String FILENAME_DECRYPTED = "files/playerdata.json";

    // String is not the best choice for a key, but it is the easiest to use for now
    // as hash we use the hashcode of the playername as a string
    private final Map<String, Playerdata> playerMap = new HashMap<>();

    private PlayerMap() {
        readPlayerdata();
    }

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

        File in = new File(FILENAME_ENCRYPTED);
        if(!in.exists()) { // if file does not exist there is no data to read
            return;
        }
        File out= new File(FILENAME_DECRYPTED);
        try {
            // decrypt the file to read
            FileEncrypter.decrypt(SECRET_KEY, in, out);
        } catch (CryptoException e) {
            System.out.println("Error decrypting file " + in.getName());
        }

        FileHandle file = Gdx.files.local(FILENAME_DECRYPTED);
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        try {
            Map<String, Playerdata> map = json.fromJson(HashMap.class, file);
            if(map == null) { // empty file
                return;
            }
            playerMap.putAll(map);
        } catch (Exception e) {
            // file is corrupted
            file.writeString("", false);
        }

        out.delete(); // delete the decrypted file, we dont need it anymore

    }

    private void writePlayerdata() {

        Json json = new Json();
        json.setUsePrototypes(false);
        json.setOutputType(JsonWriter.OutputType.json);

        String jsonStr = json.toJson(this.playerMap);
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
     get sorted sets
     */
    
    public TreeSet<Playerdata> getPlayersSortedByName() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
                // name is unique, so we can use it as only comparison
                return o2.getName().compareToIgnoreCase(o1.getName());
            }
        });
        players.addAll(playerMap.values());
        return players;
    }

    public TreeSet<Playerdata> getPlayersSortedByScore() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
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

    public TreeSet<Playerdata> getPlayersSortedByKills() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
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

    public TreeSet<Playerdata> getPlayersSortedByGamesPlayed() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
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
    public TreeSet<Playerdata> getPlayersSortedByGamesWon() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
                long l = Long.compare(o1.getTotalGamesWon(), o2.getTotalGamesWon());
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

    public TreeSet<Playerdata> getPlayersSortedByLevels() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
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
    
    public TreeSet<Playerdata> getPlayersSortedByPlaytime() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
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

    public TreeSet<Playerdata> getPlayersSortedByBeatenRooms() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
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

    //getPlayersSortedByDateTime
    public TreeSet<Playerdata> getPlayersSortedByDateTime() {
        TreeSet<Playerdata> players = new TreeSet<>(new Comparator<Playerdata>() {
            @Override
            public int compare(Playerdata o1, Playerdata o2) {
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