package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;


/**
 * A room strategy that aligns the rooms from the top left straight down
 * Looks like this: (roomsPerRow = 4, roomsPerColumn = 3)
 * 1 6 7 12
 * 2 5 8 11
 * 3 4 9 10
 */
public class TopLeftStraightDown extends RoomStrategy{

    /**
     * Constructor for the TopLeftStraightDown class
     * @param roomsPerRow number of rooms per row
     * @param roomsPerColumn number of rooms per column
     * @throws RoomStrategyException if the combination of roomsPerRow and roomsPerColumn is not valid
     */
    public TopLeftStraightDown(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        alignRoomsTopLeftStraightDown(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    /**
     * Aligns the rooms from the top left straight down
     * @param gridRows number of rows in the grid
     * @param gridCols number of columns in the grid
     */
    private void alignRoomsTopLeftStraightDown(final int gridRows, final int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random random = new Random();

        for(int col = 0; col<this.roomsPerRow; col++) {
            boolean isTopToBottom = col % 2 == 0;
            int row = !isTopToBottom ? 0 : roomsPerColumn - 1;

            while(row < this.roomsPerColumn && row >= 0) {

                int roomWidth = clamp(random.nextInt(parcelCols), parcelCols / roomsPerColumn + 2, parcelCols);
                int roomHeight = clamp(random.nextInt(parcelRows), parcelRows / roomsPerRow + 2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row + 1, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];

                if(isTopToBottom){
                    row--;
                } else {
                    row++;
                }
                roomCounter++;
            }

        }

    }

    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "TopLeftStraightDown strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
