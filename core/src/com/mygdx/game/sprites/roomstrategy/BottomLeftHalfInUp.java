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

        int leftHalf = (int) Math.ceil(this.roomsPerRow/2f);
        int rightHalf = (int) Math.floor(this.roomsPerRow/2f);

        while(row >= 0 && row < this.roomsPerColumn) {

            if(isOnLeftSite && goIn) {
                col = 0;
            } else if(isOnLeftSite && !goIn) {
                col = leftHalf-1;
            } else if(!isOnLeftSite && goIn) {
                col = this.roomsPerRow-1;
            } else if(!isOnLeftSite && !goIn) {
                col = leftHalf;
            }

            while(col >= 0 && col < this.roomsPerRow) {
                if(roomCounter >= this.roomsPerColumn * this.roomsPerRow) {
                    break;
                }
                int roomWidth = clamp(r.nextInt(parcelCols), parcelCols / roomsPerColumn, parcelCols);
                int roomHeight = clamp(r.nextInt(parcelRows), parcelRows / roomsPerRow, parcelRows);
                roomMatrix[row][col] = new Room(parcelCols * col, parcelRows * row, roomWidth, roomHeight, roomCounter);
                roomsInOrder[roomCounter] = roomMatrix[row][col];
                roomCounter++;

                if(row == this.roomsPerColumn-1) {
                    col++;
                } else {

                    if(isOnLeftSite && goIn && col >= leftHalf-1) {
                        break;
                    } else if(isOnLeftSite && !goIn && col <= 0) {
                        break;
                    } else if (!isOnLeftSite && goIn && col <= leftHalf) {
                        break;
                    } else if (!isOnLeftSite && !goIn && col >= this.roomsPerRow-1) {
                        break;
                    }

                    if(isOnLeftSite){
                        col = col + (goIn ? 1 : -1);
                    } else {
                        col = col + (goIn ? -1 : 1);
                    }

                }

            }

            goIn = !goIn;

            if(row == this.roomsPerColumn-1 && isOnLeftSite) {
                isOnLeftSite = false;
                goIn = true;
            }
            if(isOnLeftSite){
                row++;
            } else {
                row--;
            }

            
        }

    }


    @Override
    public boolean isValidCombination(int roomsPerRow, int roomsPerColumn) {
        this.errorMessage = "BottomLeftHalfInUp needs a roomsPerColumn that is odd and roomsPerColumn and roomsPerRow that are greater than 0.";
        return roomsPerRow > 0 && roomsPerColumn > 0 && roomsPerColumn % 2 == 1;
    }
}
