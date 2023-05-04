package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

public class TopRightStraightDown extends RoomStrategy {

    public TopRightStraightDown(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        System.out.println("TopRightStraightDown");

        alignRoomsTopRightStraightDown(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    private void alignRoomsTopRightStraightDown(int gridRows, int gridCols) {
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

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row, roomWidth, roomHeight, roomCounter);
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
        this.errorMessage = "TopRightStraightDown strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
