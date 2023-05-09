package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.sprites.gameObjects.enemys.EnemyLabel;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.items.HealthLabel;
import com.mygdx.game.sprites.gameObjects.items.ItemLabel;
import com.mygdx.game.sprites.gameObjects.items.SwordLabel;
import com.mygdx.game.sprites.roomstrategy.*;
import com.mygdx.game.states.State;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import java.util.ArrayList;
import java.util.List;


public class Grid {
    private static final int ROOMS_PER_ROW = 3;
    private static final int ROOMS_PER_COLUMN = 3;
    public final static int ROWS = 54;
    public final static int COLUMNS = 90;
    private final static int SPACE_BETWEEN_CHARACTERS = 20;
    private final static int START_POSX_GRID = (State.WIDTH - (COLUMNS * SPACE_BETWEEN_CHARACTERS)) / 2;
    private final static int START_POSY_GRID = (State.HEIGHT - (ROWS * SPACE_BETWEEN_CHARACTERS)) / 2;
    private final GameObjectLabel[][] grid;
    private Room[] roomsInOrder = new Room[ROOMS_PER_ROW * ROOMS_PER_COLUMN];
    private Room[][] roomMatrix = new Room[ROOMS_PER_ROW][ROOMS_PER_COLUMN];
    private RoomStrategy roomStrategy = null;
    private final LabelStyle style;
    private final PlayerLabel playerLabel;
    List<EnemyLabel> enemyLabelList = new ArrayList<>();
    List<ItemLabel> itemLabelList = new ArrayList<>();


    public Grid() {
        this.grid = new GameObjectLabel[ROWS][COLUMNS];
        style = new LabelStyle(new Font().setBitmapFont(), Color.WHITE);

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





        //set player into grid
        int playerX = getRooms()[0].getX();
        int playerY = getRooms()[0].getY();
        this.playerLabel = new PlayerLabel(this, style, playerX, playerY);

        //only for testing
        int swordX = getRooms()[1].getX();
        int swordY = getRooms()[1].getY();

        int healthX = getRooms()[2].getX();
        int healthY = getRooms()[2].getY();

        itemLabelList.add(new SwordLabel(this, style, swordX, swordY, 10));
        itemLabelList.add(new HealthLabel(this, style, healthX, healthY, 100));
        //
    }

    public List<ItemLabel> getItemList() {
        return itemLabelList;
    }

    public GameObjectLabel[][] getGrid() {
        return grid;
    }

    public void setGridCharacter(int y, int x, GameObjectLabel gameObjectLabel) {
        grid[y][x] = gameObjectLabel;
        gameObjectLabel.setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
    }

    public void setGridCharacter(int y, int x, String gridObjectCharacter) {
        GameObjectLabel gameObjectLabel = new GameObjectLabel(gridObjectCharacter, style);
        this.setGridCharacter(y, x, gameObjectLabel);
    }

    public PlayerLabel getPlayer() {
        return playerLabel;
    }

    public Room[] getRooms() {
          return roomsInOrder;
     }

    public void updateEnemies() {
        for (EnemyLabel e : enemyLabelList) {
            e.updateMovement();
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
                    grid[y][x] = new GameObjectLabel(" ", style);
                    grid[y][x].setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
                }
            }
        }

        enemyLabelList.add(new EnemyLabel("\uD83D\uDC0D", this, 30, 30)); //TODO: remove, just for debugging
        enemyLabelList.add(new EnemyLabel("B", this, roomsInOrder[1].getX()+2, roomsInOrder[1].getY()+2)); //TODO: remove, just for debugging

    }

    private void drawRoomMatrixToGrid(LabelStyle style){
        for(Room[] rArr : this.roomMatrix) {
            for(Room r : rArr){
                r.drawRoom(grid, style, SPACE_BETWEEN_CHARACTERS, START_POSX_GRID, START_POSY_GRID);
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
            p.drawPath(grid, style, SPACE_BETWEEN_CHARACTERS, START_POSX_GRID, START_POSY_GRID);

            r.setHasOutboundPath(true);
            r2.setHasInboundPath(true);

        }


    }


}
