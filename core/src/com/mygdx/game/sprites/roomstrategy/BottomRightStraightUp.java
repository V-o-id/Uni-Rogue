package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

/**
 * A room strategy that aligns the rooms from the bottom right straight up
 * Looks like this: (roomsPerRow = 4, roomsPerColumn = 3)
 * 10  9  4  3
 * 11  8  5  2
 * 12  7  6  1
 */
public class BottomRightStraightUp extends RoomStrategy {

    /**
     * Constructor for the BottomRightStraightUp class
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid
     */
    public BottomRightStraightUp(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        alignBottomRightStraightUp(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    /**
     * Aligns the rooms from the bottom right straight up
     * @param gridRows number of rows in the grid
     * @param gridCols number of columns in the grid
     */
    private void alignBottomRightStraightUp(int gridRows, int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random r = new Random();

        boolean isTopToBottom = true;

        for(int col = this.roomsPerRow - 1; col >= 0; col--) {
            int row = isTopToBottom ? 0 : roomsPerColumn - 1;

            while(row < this.roomsPerColumn && row >= 0) {

                int roomWidth = clamp(r.nextInt(parcelCols), parcelCols / roomsPerColumn + 2, parcelCols);
                int roomHeight = clamp(r.nextInt(parcelRows), parcelRows / roomsPerRow + 2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row + 1, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];

                if(isTopToBottom){
                    row++;
                } else {
                    row--;
                }
                roomCounter++;
            }
            isTopToBottom = !isTopToBottom;
        }

    }

    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "BottomRightStraightUp strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
