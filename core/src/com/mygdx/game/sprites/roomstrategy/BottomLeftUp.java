package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

/**
 * A room strategy that aligns the rooms from the bottom left to right and up one row
 * Looks like this: (roomsPerRow = 4, roomsPerColumn = 3)
 * 9 10 11 12
 * 8 7 6 5
 * 1 2 3 4
 */
public class BottomLeftUp extends RoomStrategy {

    /**
     * Constructor for the BottomLeftUp class
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid
     */
    public BottomLeftUp(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(final int gridRows, final int gridCols) {
        alignRoomsBottomLeftUp(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    /**
     * Aligns the rooms from the bottom left to right and up one row
     * @param gridRows number of rows in the grid
     * @param gridCols number of columns in the grid
     */
    private void alignRoomsBottomLeftUp(final int gridRows, final int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random random = new Random();

        for(int row = 0; row < roomsPerColumn; row++){
            boolean isLeftToRight = row % 2 == 0;
            int col = isLeftToRight ? 0 : roomsPerRow - 1;
            while(col < roomsPerRow && col >= 0){

                int roomWidth = clamp(random.nextInt(parcelCols), parcelCols / roomsPerColumn + 2, parcelCols);
                int roomHeight = clamp(random.nextInt(parcelRows), parcelRows / roomsPerRow + 2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row + 1, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];

                if(isLeftToRight){
                    col++;
                } else {
                    col--;
                }
                roomCounter++;

            }
        }

    }

    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "BottomLeftUp strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
