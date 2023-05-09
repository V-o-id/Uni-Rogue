package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

public abstract class RoomStrategy {

    protected Room[][] roomMatrix;
    protected Room[] roomsInOrder;
    protected int roomsPerRow;
    protected int roomsPerColumn;
    protected String errorMessage = null;

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
     * @return the roomMatrix as a 2D Room array
     */
    public abstract Room[][] alignRooms(final int gridRows, final int gridCols);

    public Room[] getRoomsInOrder() {
        return roomsInOrder;
    }
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

    protected static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        } else return Math.min(value, max);
    }


}
