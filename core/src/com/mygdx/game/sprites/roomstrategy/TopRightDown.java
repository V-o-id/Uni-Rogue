package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

/**
 * A room strategy that aligns the rooms from the top right to the left, then down one row, then to the right, then down one row, etc.
 * Looks like this: (roomsPerRow = 4, roomsPerColumn = 3)
 * 4  3  2  1
 * 5  6  7  8
 * 12 11 10 9
 */
public class TopRightDown extends RoomStrategy {

    /**
     * Constructor for the TopRightDown class
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid
     */
    public TopRightDown(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        alignRoomsTopRightDown(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    /**
     * Aligns the rooms from the top right to the left, then down one row, then to the right, then down one row, etc.
     * @param gridRows number of rows in the grid
     * @param gridCols number of columns in the grid
     */
    private void alignRoomsTopRightDown(final int gridRows, final int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random r = new Random();

        boolean isRightToLeft = true;

        for(int row = this.roomsPerColumn-1; row >= 0; row--) {
            int col = isRightToLeft ? roomsPerRow - 1 : 0;

            while(col < this.roomsPerRow && col >= 0){

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

            isRightToLeft = !isRightToLeft;

        }

    }


    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "TopRightDown strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
