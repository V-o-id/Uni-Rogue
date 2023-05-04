package com.mygdx.game.sprites.roomstrategy;

import com.mygdx.game.sprites.Room;

import java.util.Random;

public class BottomLeftHalfInUp extends RoomStrategy {

    public BottomLeftHalfInUp(int roomsPerRow, int roomsPerColumn) throws RoomStrategyException {
        super(roomsPerRow, roomsPerColumn);
    }

    @Override
    public Room[][] alignRooms(int gridRows, int gridCols) {
        System.out.println("BottomLeftHalfInUp");

        alignRoomsBottomLeftHalfInUp(gridRows, gridCols);

        roomsInOrder[0].setHasInboundPath(true);
        roomsInOrder[roomsPerColumn * roomsPerRow - 1].setHasOutboundPath(true);

        return this.roomMatrix;
    }

    private void alignRoomsBottomLeftHalfInUp(final int gridRows, final int gridCols) {
        int parcelRows = (int) Math.floor(gridRows / (roomsPerColumn+0.7));
        int parcelCols = gridCols / roomsPerRow;

        int roomCounter = 0;
        Random r = new Random();

        boolean isOnLeftSite = true;
        boolean goIn = true;

        int row = 0;
        int col = 0;

        while(checkRow(row, isOnLeftSite, goIn)) {
            
        }




    }

    private boolean checkRow(int row, boolean isOnLeftSite, boolean goIn) {

        if(isOnLeftSite) {
            if(goIn) {
                return row < Math.ceil(roomsPerColumn / 2);
            } else {
                return row >= 0;
            }
        } else {


        }
        return false;
    }

    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "BottomLeftHalfInUp needs a roomsPerColumn that is odd and roomsPerColumn and roomsPerRow that are greater than 0.";
        return roomsPerRow > 0 && roomsPerColumn > 0 && roomsPerColumn % 2 == 1;
    }
}
