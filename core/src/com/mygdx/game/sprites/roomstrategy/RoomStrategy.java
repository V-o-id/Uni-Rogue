package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

/**
 * Abstract class for the room strategies
 * This class will be extended by the different room strategies
 * will be used to align the rooms
 */
public abstract class RoomStrategy {

    /**
     * 2d array of rooms, depends on the chosen strategy
     */
    protected Room[][] roomMatrix;
    /**
     * array of rooms in order: first room is the start room, last room is the end room
     */
    protected Room[] roomsInOrder;
    /**
     * number of rooms per row
     * note: this corresponds to the number of columns, not the number of rows
     */
    protected int roomsPerRow;
    /**
     * number of rooms per column
     * note: this corresponds to the number of rows, not the number of columns
     */
    protected int roomsPerColumn;
    /**
     * error message if the combination of roomsPerRow and roomsPerColumn is not valid
     * this will be set in the extending class
     */
    protected String errorMessage = null;

    /**
     * Constructor for the RoomStrategy class
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid
     */
    public RoomStrategy(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        if(isValidCombination(roomsPerRow, roomsPerColumn)){
            this.roomsPerRow = roomsPerRow;
            this.roomsPerColumn = roomsPerColumn;
            this.roomMatrix = new Room[roomsPerColumn][roomsPerRow];
            this.roomsInOrder = new Room[roomsPerRow * roomsPerColumn];
        } else {
            throw new RoomStrategyException(errorMessage, this.getClass().getSimpleName(), roomsPerRow, roomsPerColumn);
        }
    }


    /**
     * this method will align the rooms in the roomMatrix
     * this method will also set the roomsInOrder array
     * will fill the roomMatrix and roomsInOrder arrays
     * @param gridRows number of rows in the grid
     * @param gridCols number of columns in the grid
     * @return the roomMatrix as a 2D Room array
     */
    public abstract Room[][] alignRooms(final int gridRows, final int gridCols);

    /**
     * @return the roomsInOrder array
     */
    public Room[] getRoomsInOrder() {
        return roomsInOrder;
    }

    /**
     * @return the roomMatrix as a 2D Room array
     */
    public Room[][] getRoomMatrix() {
        return roomMatrix;
    }

    /**
     * this method will check if the number of rooms per row and per column is valid for the chosen strategy
     * @param roomsPerRow    number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @return true if the number of rooms per row and per column is valid for the chosen strategy
     */
    public abstract boolean isValidCombination(int roomsPerRow, int roomsPerColumn);

    /**
     * this method will clamp the value between min and max
     * helper method for the alignRooms method
     * @param value the value to clamp
     * @param min  the minimum value
     * @param max the maximum value
     * @return the clamped value as an int
     */
    protected static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        } else return Math.min(value, max);
    }


}
