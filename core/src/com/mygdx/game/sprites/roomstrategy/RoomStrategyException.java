package com.mygdx.game.sprites.roomstrategy;


/**
 * this exception will be thrown when number of rooms per row or per column is not valid / is not suitable for the strategy
 * if you need to choose a random strategy loop until you get a valid one
 * strategies also have a method to check if they are valid for a given number of rooms per room and per column
 */
public class RoomStrategyException extends Exception {

    /**
     * the strategy that was chosen as a string
     */
    public final String roomStrategy;
    /**
     * number of rooms per row
     * note: this corresponds to the number of columns, not the number of rows
     */
    public final int roomsPerRow;
    /**
     * number of rooms per column
     * note: this corresponds to the number of rows, not the number of columns
     */
    public final int roomsPerColumn;

    public RoomStrategyException(String message) {
        super(message);
        this.roomStrategy = null;
        this.roomsPerRow = -1;
        this.roomsPerColumn = -1;
    }
    public RoomStrategyException(String message, String strategy, int roomsPerRow, int roomsPerColumn) {
        super(message);
        this.roomStrategy = strategy;
        this.roomsPerRow = roomsPerRow;
        this.roomsPerColumn = roomsPerColumn;
    }


}
