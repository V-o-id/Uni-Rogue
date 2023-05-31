package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

/**
 * A room strategy that aligns the rooms from the top left to the right, and down one row
 * Looks like this: (roomsPerRow = 4, roomsPerColumn = 3)
 * 1 2 3 4
 * 8 7 6 5
 * 9 10 11 12
 */
public class TopLeftDown extends RoomStrategy {


    /**
     * Constructor for the TopLeftDown class
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid
     */
    public TopLeftDown(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(final int gridRows, final int gridCols) {
        alignRoomsTopLeftDown(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    /**
     * Aligns the rooms from the top left to the right, and down one row
     * @param gridRows number of rows in the grid
     * @param gridCols number of columns in the grid
     */
    private void alignRoomsTopLeftDown(final int gridRows, final int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random r = new Random();

        boolean isLeftToRight = true;

        for(int row = this.roomsPerColumn - 1; row >= 0; row--) {
            int col = isLeftToRight ? 0 : roomsPerRow - 1;

            while(col < this.roomsPerRow && col >= 0){

                int roomWidth = clamp(r.nextInt(parcelCols), parcelCols / roomsPerColumn + 2, parcelCols);
                int roomHeight = clamp(r.nextInt(parcelRows), parcelRows / roomsPerRow + 2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row + 1, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];

                if(isLeftToRight){
                    col++;
                } else {
                    col--;
                }
                roomCounter++;

            }

            isLeftToRight = !isLeftToRight;

        }

    }

    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "TopLeftDown strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
