package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

public class BottomLeftStraightUp extends RoomStrategy {

    public BottomLeftStraightUp(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        System.out.println("BottomLeftStraightUp");

        alignBottomLeftStraightUp(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    private void alignBottomLeftStraightUp(int gridRows, int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random random = new Random();

        for(int col = 0; col<this.roomsPerRow; col++) {
            boolean isTopToBottom = col % 2 == 0;
            int row = isTopToBottom ? 0 : roomsPerRow - 1;

            while(row < this.roomsPerColumn && row >= 0) {

                int roomWidth = clamp(random.nextInt(parcelCols), parcelCols / roomsPerColumn +2, parcelCols);
                int roomHeight = clamp(random.nextInt(parcelRows), parcelRows / roomsPerRow +2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row + 1, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];

                if(isTopToBottom){
                    row++;
                } else {
                    row--;
                }
                roomCounter++;
            }

        }

    }

    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "BottomLeftStraightUp strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
