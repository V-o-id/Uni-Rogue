package com.mygdx.game.data;

/**
 * This class represents the current player.
 * It is a singleton class to make access to the current player easier.
 * The current player is the player that is currently playing the game.
 */
public class CurrentPlayer {

    private static PlayerData currentPlayer;

    private CurrentPlayer() {}

    /**
     * Returns the current player. If no player is set, a new player with the default name "Player1" is created.
     * @return the current player
     */
    public static PlayerData getCurrentPlayer() {
        if(currentPlayer == null) {
            currentPlayer = new PlayerData("Player1");
        }
        return currentPlayer;
    }

    /**
     * Sets the current player.
     * Can be called whenever the player changes.
     * @param playerdata the player to set as current player
     */
    public static void setCurrentPlayer(PlayerData playerdata) {
        currentPlayer = playerdata;
    }

}
