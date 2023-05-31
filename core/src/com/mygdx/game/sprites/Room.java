package com.mygdx.game.sprites;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.RoomLabel;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a room.
 * A room is a rectangle on the grid.
 * Rooms can be connected to each other by paths.
 */
public class Room {

    /**
     * x coordinate of the bottom left position of the room
     */
    private final int x;
    /**
     * y coordinate of the bottom left position of the room
     */
    private final int y;
    /**
     * width of the room in grid cells
     */
    private final int width;
    /**
     * height of the room in grid cells
     */
    private final int height;
    /**
     * number of the room
     */
    private final int roomNumber;
    /**
     * boolean that indicates if the room has a path that leads to it
     */
    private boolean hasInboundPath = false;
    /**
     * boolean that indicates if the room has a path that leads from it
     */
    private boolean hasOutboundPath = false;
    List<EnemyLabel> enemyLabelList = new ArrayList<>();

    /**
     * Constructor for a room
     * @param x x coordinate of the bottom left position of the room
     * @param y y coordinate of the bottom left position of the room
     * @param width width of the room in grid cells
     * @param height height of the room in grid cells
     * @param roomNumber number of the room
     * if roomNumber is 0, the room has an inbound path
     */
    public Room(int x, int y, int width, int height, int roomNumber) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.roomNumber = roomNumber;
        if (roomNumber == 0) {
            hasInboundPath = true;
        }
    }

    /**
     * Checks if the given coordinates are inside the room
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the coordinates are inside the room, false otherwise
     */
    public boolean isIn(int x, int y) {
        return (x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height);
    }

    /**
     * @return x coordinate of the bottom left position of the room
     */
    public int getX() {
        return x;
    }

    /**
     * @return y coordinate of the bottom left position of the room
     */
    public int getY() {
        return y;
    }

    /**
     * @return width of the room in grid cells
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of the room in grid cells
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return number of the room
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * @return true if the room has a path that leads to it, false otherwise
     */
    public boolean getHasInboundPath() {
        return hasInboundPath;
    }

    /**
     * @return true if the room has a path that leads from it, false otherwise
     */
    public boolean getHasOutboundPath() {
        return hasOutboundPath;
    }

    /**
     * @param hasInboundPath boolean that indicates if the room has a path that leads to it
     */
    public void setHasInboundPath(boolean hasInboundPath) {
        this.hasInboundPath = hasInboundPath;
    }

    /**
     * @param hasOutboundPath boolean that indicates if the room has a path that leads from it
     */
    public void setHasOutboundPath(boolean hasOutboundPath) {
        this.hasOutboundPath = hasOutboundPath;
    }

    /**
     * Draws the room on the specified grid
     * @param grid grid to draw the room on
     * @param style labelstyle to use
     * @param SPACE_BETWEEN_CHARACTERS space between the characters in the grid
     * @param START_POSX_GRID x coordinate of the bottom left position of the grid
     * @param START_POSY_GRID y coordinate of the bottom left position of the grid
     */
    public void drawRoom(GameObjectLabel[][] grid, LabelStyle style, final int SPACE_BETWEEN_CHARACTERS, final int START_POSX_GRID, final int START_POSY_GRID) {
        for (int y = this.y; y < height + this.y; y++) {
            for (int x = this.x; x < width + this.x; x++) {
                grid[y][x] = new RoomLabel(style);
                grid[y][x].setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
            }
        }
    }

    /**
     * Checks if the room is left of the given room
     * @param r2 other room
     * @return true if this room is left of the other room, false otherwise
     */
    boolean isLeftOf(Room r2) {
        return r2.getX() > this.getX() + this.getWidth();
    }

    /**
     * Checks if the room is right of the given room
     * @param r2 other room
     * @return true if this room is right of the other room, false otherwise
     */
    boolean isRightOf(Room r2) {
        return r2.getX() + r2.getWidth() < this.getX();
    }

    /**
     * Checks if the room is above the given room
     * @param r2 other room
     * @return true if this room is above the other room, false otherwise
     */
    boolean isAbove(Room r2) {
        return this.getY() > r2.getY() + r2.getHeight();
    }

    /**
     * Checks if the room is below the given room
     * @param r2 other room
     * @return true if this room is below the other room, false otherwise
     */
    boolean isBelow(Room r2) {
        return this.getY() + this.getHeight() < r2.getY();
    }

    /**
     * calculates the distance between this room and the given room
     * @param r2 other room
     * @return manhattan distance between the rooms
     */
    int getDistance(Room r2) {
        int xDistance = Math.abs(this.getX() - r2.getX());
        int yDistance = Math.abs(this.getY() - r2.getY());
        return xDistance + yDistance;
    }

    /**
     * @return list of enemies in the room
     */
    public List<EnemyLabel> getEnemies() {
        return enemyLabelList;
    }

}
