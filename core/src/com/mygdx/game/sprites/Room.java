package com.mygdx.game.sprites;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Room {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int roomNumber;


    Room (int x, int y, int width, int height, int roomNumber) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roomNumber = roomNumber;
        System.out.println("x: " + x + "  y " + y + ",  width  " + width + ",  height  " + height + ",  roomNumber  " + roomNumber);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getRoomNumber() {
        return roomNumber;
    }


    void drawRoom(final String roomCharacter, Label[][] grid, final int heightRoom, final int widthRoom, final Label.LabelStyle style, final int SPACE_BETWEEN_CHARACTERS, final int START_POSX_GRID, final int START_POSY_GRID) {
        for (int y = this.y; y < height + heightRoom; y++) {
            for (int x = this.x; x < width + widthRoom; x++) {
                grid[y][x] = new Label(roomCharacter, style);
                grid[y][x].setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
            }
        }
    }



}
