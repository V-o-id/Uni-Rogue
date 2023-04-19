package com.mygdx.game.sprites;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Room {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int roomNumber;
    private boolean hasInboundPath = false;
    private boolean hasOutboundPath = false;


    Room (int x, int y, int width, int height, int roomNumber) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roomNumber = roomNumber;
        if(roomNumber == 0){
            hasInboundPath = true;
        }

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
    public boolean getHasInboundPath() {
        return hasInboundPath;
    }
    public boolean getHasOutboundPath() {
        return hasOutboundPath;
    }
    public void setHasInboundPath(boolean hasInboundPath) {
        this.hasInboundPath = hasInboundPath;
    }
    public void setHasOutboundPath(boolean hasOutboundPath) {
        this.hasOutboundPath = hasOutboundPath;
    }

    void drawRoom(final String roomCharacter, Label[][] grid, final int heightRoom, final int widthRoom, final Label.LabelStyle style, final int SPACE_BETWEEN_CHARACTERS, final int START_POSX_GRID, final int START_POSY_GRID) {
        for (int y = this.y; y < height + heightRoom; y++) {
            for (int x = this.x; x < width + widthRoom; x++) {
               // grid[y][x] = new Label(roomCharacter, style);
                    grid[y][x] = new Label(roomCharacter, style);
                    grid[y][x].setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
            }
        }
    }

    boolean isLeftOf(Room r2) {
        return r2.getX() > this.getX() + this.getWidth();
    }
    boolean isRightOf(Room r2) {
        return r2.getX() + r2.getWidth() < this.getX();
    }
    boolean isAbove(Room r2) {
        return this.getY() > r2.getY() + r2.getHeight();
    }
    boolean isBelow(Room r2) {
        return this.getY() + this.getHeight() < r2.getY();
    }

    int getDistance(Room r2) {
        int xDistance = Math.abs(this.getX() - r2.getX());
        int yDistance = Math.abs(this.getY() - r2.getY());
        return xDistance + yDistance;
    }




}
