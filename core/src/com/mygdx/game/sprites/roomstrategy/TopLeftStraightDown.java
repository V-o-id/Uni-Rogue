package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

public class TopLeftStraightDown extends RoomStrategy{

    public TopLeftStraightDown(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        System.out.println("TopLeftStraightDown");

        alignRoomsTopLeftStraightDown(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    private void alignRoomsTopLeftStraightDown(int gridRows, int gridCols) {
        int parcelRows = gridRows / roomsPerColumn;
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random random = new Random();

        for(int col = 0; col<this.roomsPerRow; col++) {
            boolean isTopToBottom = col % 2 == 0;
            int row = !isTopToBottom ? 0 : roomsPerRow - 1; // flip this line

            while(row < this.roomsPerColumn && row >= 0) {

                int roomWidth = clamp(random.nextInt(parcelCols), parcelCols / roomsPerColumn + 2, parcelCols);
                int roomHeight = clamp(random.nextInt(parcelRows), parcelRows / roomsPerRow + 2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];

                if(isTopToBottom){ // flip this line to make it bottom to top
                    row--;
                } else {
                    row++;
                }
                roomCounter++;
            }
            System.out.println(roomCounter);


        }

    }

    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "TopLeftStraightDown strategy requires at least 1 room per row and 1 room per column.";
        return roomsPerRow > 0 && roomsPerColumn > 0;
    }

}
