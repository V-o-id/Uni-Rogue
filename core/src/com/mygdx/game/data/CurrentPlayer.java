package com.mygdx.game.data;

public class CurrentPlayer {

    private static Playerdata currentPlayer;

    private CurrentPlayer() {}

    /**
     * Returns the current player. If no player is set, a new player is created.
     * @return the current player
     */
    public static Playerdata getCurrentPlayer() {
        if(currentPlayer == null) {
            currentPlayer = new Playerdata("Player1");
        }
        return currentPlayer;
    }

    public static void setCurrentPlayer(Playerdata playerdata) {
        currentPlayer = playerdata;
    }

}
