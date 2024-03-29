package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;


/**
 * A room strategy that aligns the rooms from the bottom right to the left and up one row
 * Looks like this: (roomsPerRow = 4, roomsPerColumn = 3)
 * 12 11 10 9
 * 5 6 7 8
 * 4 3 2 1
 */
public class BottomRightUp extends RoomStrategy {

    /**
     * Constructor for the BottomRightUp class
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid
     */
    public BottomRightUp(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        alignRoomsBottomRightUp(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    /**
     * Aligns the rooms from the bottom right to the left and up one row
     * @param gridRows number of rows in the grid
     * @param gridCols number of columns in the grid
     */
    private void alignRoomsBottomRightUp(final int gridRows, final int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random r = new Random();

        for(int row = 0; row < roomsPerColumn; row++) {
            boolean isRightToLeft = row % 2 == 0;
            int col = isRightToLeft ? roomsPerRow - 1 : 0;

            while(col < roomsPerRow && col >= 0) {

                int roomWidth = clamp(r.nextInt(parcelCols), parcelCols / roomsPerColumn + 2, parcelCols);
                int roomHeight = clamp(r.nextInt(parcelRows), parcelRows / roomsPerRow + 2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row + 1, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];

                if(isRightToLeft){
                    col--;
                } else {
                    col++;
                }
                roomCounter++;

            }

        }

    }


    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "BottomRightUp strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }
}
