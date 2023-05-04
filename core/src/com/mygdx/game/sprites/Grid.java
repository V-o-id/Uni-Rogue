package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.roomstrategy.*;
import com.mygdx.game.states.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid extends Label {

    private final Label[][] grid;
    private final int ROOMS_PER_ROW = 3;
    private final int ROOMS_PER_COLUMN = 3;
    private Room[] roomsInOrder = new Room[ROOMS_PER_ROW * ROOMS_PER_COLUMN];
    private Room[][] roomMatrix = new Room[ROOMS_PER_ROW][ROOMS_PER_COLUMN];
    public final static int ROWS = 54;
    public final static int COLUMNS = 90;
    private final static int SPACE_BETWEEN_CHARACTERS = 20;
    private final static int START_POSX_GRID = (State.WIDTH - (COLUMNS * SPACE_BETWEEN_CHARACTERS)) / 2;
    private final static int START_POSY_GRID = (State.HEIGHT - (ROWS * SPACE_BETWEEN_CHARACTERS)) / 2;
    private final Color color;

    private final String gridCharacter;
    private final String pathCharacter;
    List<Enemy> enemyList = new ArrayList<>();

    private RoomStrategy roomStrategy = null;

    public Grid(String gridCharacter, Color color, String pathCharacter) {
        super(gridCharacter, new LabelStyle(new Font().setBitmapFont(), color));
        this.gridCharacter = gridCharacter;
        this.color = color;
        this.pathCharacter = pathCharacter;
        this.grid = new Label[ROWS][COLUMNS];
        Label.LabelStyle style = new Label.LabelStyle(new Font().setBitmapFont(), color);

        int numberOfStrategies = Strategies.values().length; //get all values from enum
        while(roomStrategy == null){
            int randomStrategy = (int) (Math.random() * numberOfStrategies);
            try {
                roomStrategy = Strategies.getStrategy(randomStrategy, ROOMS_PER_ROW, ROOMS_PER_COLUMN);
            } catch (RoomStrategyException e) {
                roomStrategy = null;
            }
        }

        generateRooms(style);

    }

    public Label[][] getGrid() {
        return grid;
    }

    public void setGridCharacter(int y, int x, Label label) {
        grid[y][x] = label;
        label.setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
    }

    public void setGridCharacter(int y, int x, String labelCharacter) {
        Label.LabelStyle style = new Label.LabelStyle(new Font().setBitmapFont(), color);
        Label label = new Label(labelCharacter, style);
        this.setGridCharacter(y, x, label);
    }

    public String getGridCharacter() {
        return gridCharacter;
    }

    public String getPathCharacter() {
        return pathCharacter;
    }

    public Room[] getRooms() {
          return roomsInOrder;
     }

    public void updateEnemies() {
        for (Enemy e : enemyList) {
            e.updateMovement(this);
        }
    }

    private void generateRooms(LabelStyle style) {

        roomMatrix = roomStrategy.alignRooms(ROWS, COLUMNS);
        roomsInOrder = roomStrategy.getRoomsInOrder();
        drawRoomMatrixToGrid(style);
        connectRooms(style);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                if (grid[y][x] == null) {
                    grid[y][x] = new Label(" ", style);
                    grid[y][x].setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
                }
            }
        }

        enemyList.add(new Enemy("A", this, 30, 30)); //TODO: remove, just for debugging

        enemyList.add(new Enemy("B", this, roomsInOrder[1].getX()+2, roomsInOrder[1].getY()+2)); //TODO: remove, just for debugging

    }

    private void drawRoomMatrixToGrid(LabelStyle style){
        for(Room[] rArr : this.roomMatrix) {
            for(Room r : rArr){
                r.drawRoom(gridCharacter, grid, style, SPACE_BETWEEN_CHARACTERS, START_POSX_GRID, START_POSY_GRID);
            }
        }
    }


    private void connectRooms(LabelStyle style) {

        int roomCounter = ROOMS_PER_COLUMN * ROOMS_PER_ROW;

        for (int i = 0; i < roomCounter - 1; i++) {

            Room r = roomsInOrder[i];
            Room r2 = roomsInOrder[i+1];

            int fromX = r.getX();
            int fromY = r.getY();
            int toX = r2.getX();
            int toY = r2.getY();

            if (r.isBelow(r2)) {
                fromX = r.getX() + (r.getWidth() / 2);
                fromY = r.getY() + r.getHeight() - 1;
                toX = r2.getX() + (r2.getWidth() / 2);
                toY = r2.getY();
            } else if (r.isAbove(r2)) {
                fromX = r.getX() + (r.getWidth() / 2);
                fromY = r.getY();
                toX = r2.getX() + (r2.getWidth() / 2);
                toY = r2.getY() + r2.getHeight();
            } else if (r.isLeftOf(r2)) {
                fromX = r.getX() + r.getWidth() - 1;
                fromY = r.getY() + (r.getHeight() / 2);
                toX = r2.getX();
                toY = r2.getY() + (r2.getHeight() / 2);
            } else if (r.isRightOf(r2)) {
                fromX = r.getX();
                fromY = r.getY() + (r.getHeight() / 2);
                toX = r2.getX() + r2.getWidth();
                toY = r2.getY() + (r2.getHeight() / 2);
            }

            Path p = new Path(fromX, fromY, toX, toY, r.getRoomNumber(), r.getRoomNumber(), r2.getRoomNumber());
            p.drawPath(pathCharacter, grid, style, SPACE_BETWEEN_CHARACTERS, START_POSX_GRID, START_POSY_GRID);

            r.setHasOutboundPath(true);
            r2.setHasInboundPath(true);

        }


    }


}
