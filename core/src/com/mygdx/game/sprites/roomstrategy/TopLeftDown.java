package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

public class TopLeftDown extends RoomStrategy {


    public TopLeftDown(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(final int gridRows, final int gridCols) {
        System.out.println("TopLeftDown");

        alignRoomsTopLeftDown(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

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
