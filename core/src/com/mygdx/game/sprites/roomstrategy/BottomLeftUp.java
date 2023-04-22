package com.mygdx.game.sprites.roomstrategy;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.Room;

import java.util.Random;

public class BottomLeftUp extends RoomStrategy {

    public BottomLeftUp(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(final int gridRows, final int gridCols) {
        System.out.println("BottomLeftUp");

        alignRoomsBottomLeftUp(gridRows, gridCols);

        roomMatrix[0][0].setHasInboundPath(true);
        roomsInOrder[0].setHasInboundPath(true);
        roomMatrix[roomsPerColumn - 1][roomsPerRow - 1].setHasOutboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    private void alignRoomsBottomLeftUp(final int gridRows, final int gridCols) {
        int parcelRows = gridRows / roomsPerColumn;
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random random = new Random();

        for(int row = 0; row < roomsPerColumn; row++){
            boolean isLeftToRight = row % 2 == 0;
            int col = isLeftToRight ? 0 : roomsPerRow - 1;
            while(col < roomsPerRow && col >= 0){

                int roomWidth = clamp(random.nextInt(parcelCols), parcelCols / roomsPerColumn + 2, parcelCols);
                int roomHeight = clamp(random.nextInt(parcelRows), parcelRows / roomsPerRow + 2, parcelRows);

                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row, roomWidth, roomHeight, roomCounter);
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
