package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.sprites.gameObjects.RoomLabel;
import com.mygdx.game.sprites.gameObjects.enemys.EnemyLabel;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.gameObjects.enemies.enemyTypes.*;
import com.mygdx.game.sprites.gameObjects.items.HealthLabel;
import com.mygdx.game.sprites.gameObjects.items.ItemLabel;
import com.mygdx.game.sprites.gameObjects.items.SwordLabel;
import com.mygdx.game.sprites.roomstrategy.BottomLeftHalfInUp;
import com.mygdx.game.sprites.roomstrategy.RoomStrategy;
import com.mygdx.game.sprites.roomstrategy.RoomStrategyException;
import com.mygdx.game.sprites.roomstrategy.Strategies;
import com.mygdx.game.states.State;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.mygdx.game.sprites.gameObjects.LevelLabel.LEVEL_CHARACTER;


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
    private final int level;
    List<EnemyLabel> enemyLabelList = new ArrayList<>();
    List<ItemLabel> itemLabelList = new ArrayList<>();


    public Grid(int playerHealth, int playerAttackDamage, int playerGold, int level)  {
        this.grid = new GameObjectLabel[ROWS][COLUMNS];
        this.level = level;
        style = new LabelStyle(Font.getBitmapFont(), Color.WHITE);

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

        Vector2 playerPos = new Vector2(getRooms()[0].getX(), getRooms()[0].getY());
        this.playerLabel = new PlayerLabel(this, style, (int) playerPos.x, (int) playerPos.y, getRooms()[0], playerHealth, playerAttackDamage, playerGold);

        // 2 - two item types: sword, health
        placeGameObjects(4, 7, 2, 0);

        //place level label object to enter new level; 8 = last room
        Vector2 levelPos = setRandomPosition(8);
        setGridCharacter((int) levelPos.y, (int) levelPos.x, LEVEL_CHARACTER);
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

        //Spawn 1-3 random enemy types in every room
        for(Room room: roomsInOrder) {
            for(int i = (int)(Math.random() * 3); i < 3; i++){
                if ((int) (Math.random() * EnemyLabel.NUM_OF_ENEMY_TYPES) == 0) {
                    enemyLabelList.add(new Snake(this, room.getX() + (int) (Math.random() * room.getWidth()), room.getY() + (int) (Math.random() * room.getHeight())));
                } else if ((int) (Math.random() * EnemyLabel.NUM_OF_ENEMY_TYPES) == 1) {
                    enemyLabelList.add(new Goblin(this, room.getX() + (int) (Math.random() * room.getWidth()), room.getY() + (int) (Math.random() * room.getHeight())));
                } else {
                    enemyLabelList.add(new Bat(this, room.getX() + (int) (Math.random() * room.getWidth()), room.getY() + (int) (Math.random() * room.getHeight())));
                }
            }
        }
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


    //param type: zero = item; one = enemy
    public void placeGameObjects(int minObjects, int maxObjects, int amountOfPlaceableObjects, int type) {

        int amountGameObjects = (int) Math.floor(Math.random() * ((maxObjects-minObjects)+1) + minObjects);

        for(int i = 0; i < amountGameObjects; i++) {
            int roomNumber = (int) Math.floor(Math.random() * (8+1));
            int roomWidthFromGround = roomsInOrder[roomNumber].getWidth() + roomsInOrder[roomNumber].getX() - 1;
            int roomHeightFromGround = roomsInOrder[roomNumber].getHeight() + roomsInOrder[roomNumber].getY() - 1;

            int itemPosX = (int) Math.floor(Math.random() * ((roomWidthFromGround-roomsInOrder[roomNumber].getX())+1) + roomsInOrder[roomNumber].getX());
            int itemPosY = (int) Math.floor(Math.random() * ((roomHeightFromGround-roomsInOrder[roomNumber].getY())+1) + roomsInOrder[roomNumber].getY());

            int objectType = (int) Math.floor(Math.random() * (amountOfPlaceableObjects));

            if(type == 0) {
                switch(objectType) {
                    case 0: new SwordLabel(this, style, itemPosX, itemPosY, 30); break;
                    case 1: new HealthLabel(this, style, itemPosX, itemPosY, 50); break;
                    default: return;
                }

            } else if(type == 1) {
                //enemy switch
            }

        }
    }

    //returns a number be in a range, e.g. input = 1 return 1 - 10, input = 2 return 11 - 19
    private static int getRandomNumber (int input) {
        int min = (input - 1) * 10 + 1; // Berechne den Mindestwert
        int max = input * 10; // Berechne den Höchstwert
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public Vector2 setRandomPosition(int roomNumber) {
        int roomWidthFromGround = roomsInOrder[roomNumber].getWidth() + roomsInOrder[roomNumber].getX() - 1;
        int roomHeightFromGround = roomsInOrder[roomNumber].getHeight() + roomsInOrder[roomNumber].getY() - 1;

        float itemPosX = (float) Math.floor(Math.random() * ((roomWidthFromGround-roomsInOrder[roomNumber].getX())+1) + roomsInOrder[roomNumber].getX());
        float itemPosY = (float) Math.floor(Math.random() * ((roomHeightFromGround-roomsInOrder[roomNumber].getY())+1) + roomsInOrder[roomNumber].getY());

        return new Vector2(itemPosX, itemPosY);
    }

    public void removeEnemy(EnemyLabel enemy) {
        enemyLabelList.remove(enemy);
    }
}
