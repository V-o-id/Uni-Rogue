package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.LevelLabel;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel;
import com.mygdx.game.sprites.gameObjects.enemies.enemyTypes.Bat;
import com.mygdx.game.sprites.gameObjects.enemies.enemyTypes.Goblin;
import com.mygdx.game.sprites.gameObjects.enemies.enemyTypes.Snake;
import com.mygdx.game.sprites.gameObjects.items.itemTypes.HealthLabel;
import com.mygdx.game.sprites.gameObjects.items.itemTypes.SwordLabel;
import com.mygdx.game.sprites.roomstrategy.RoomStrategy;
import com.mygdx.game.sprites.roomstrategy.RoomStrategyException;
import com.mygdx.game.sprites.roomstrategy.Strategies;
import com.mygdx.game.states.State;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that represents a grid.
 * The grid can be seen as the playing field.
 * Mostly it consists of rooms.
 */
public class Grid {
    private static final int ROOMS_PER_ROW = 3;
    private static final int ROOMS_PER_COLUMN = 3;
    public final static int ROWS = 45;
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

    /**
     * Constructor for a grid
     * @param playerHealth health of the player
     * @param playerAttackDamage attack damage of the player
     * @param playerGold gold of the player
     * @param level level of the player
     * @param gameInstanceData game instance data
     */
    public Grid(int playerHealth, int playerAttackDamage, int playerGold, int level, GameInstance gameInstanceData)  {
        this.grid = new GameObjectLabel[ROWS][COLUMNS];
        this.level = level;
        gameInstanceData.setLevel(level);
        style = new LabelStyle(Font.getBitmapFont(), Color.WHITE);

        int numberOfStrategies = Strategies.values().length; // number of strategies
        while(roomStrategy == null){
            int randomStrategy = (int) (Math.random() * numberOfStrategies);
            try {
                roomStrategy = Strategies.getStrategy(randomStrategy, ROOMS_PER_ROW, ROOMS_PER_COLUMN);
            } catch (RoomStrategyException e) {
                roomStrategy = null;
            }
        }

        generateRooms(style);

        //place Player in the grid
        Vector2 playerPos = new Vector2(getRooms()[0].getX(), getRooms()[0].getY());
        this.playerLabel = new PlayerLabel(this, style, (int) playerPos.x, (int) playerPos.y, getRooms()[0], playerHealth, playerAttackDamage, playerGold, gameInstanceData);
        //place Items (Sword, Health) in the grid
        placeGameObjects(4, 7, 2, 0);
        //place Enemys (Snake, Goblin, Bat) in the grid
        placeGameObjects(15, 25, 3, 1);

        //place level label object to enter new level; 8 = last room
        Vector2 levelPos = setRandomPosition(ROOMS_PER_COLUMN*ROOMS_PER_ROW-1);
        setGridCharacter((int) levelPos.y, (int) levelPos.x, new LevelLabel(Constants.STYLE));

    }

    public GameObjectLabel[][] getGrid() {
        return grid;
    }

    public void setGridCharacter(int y, int x, GameObjectLabel gameObjectLabel) {
        grid[y][x] = gameObjectLabel;
        gameObjectLabel.setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
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

    //TODO comment
    /**
     * Method for randomly generating rooms
     * @param style
     */
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

            Path p = new Path(fromX, fromY, toX, toY);
            p.drawPath(grid, style, SPACE_BETWEEN_CHARACTERS, START_POSX_GRID, START_POSY_GRID);

            r.setHasOutboundPath(true);
            r2.setHasInboundPath(true);

        }
    }


    /**
     * @param minObjects number of minimum placed objects in the grid of one typ
     * @param maxObjects number of maximum placed objects in the grid of one typ
     * @param amountOfPlaceableObjects number of different types to place
     * @param type defines type (item or enemy)
     */
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
                    case 0: new SwordLabel(this, style, itemPosX, itemPosY, getRandomNumber(level)); break;
                    case 1: new HealthLabel(this, style, itemPosX, itemPosY, getRandomNumber(level)); break;
                    default: return;
                }
            } else if(type == 1) {
                switch(objectType) {
                    case 0: new Snake(this, itemPosX, itemPosY); break;
                    case 1: new Goblin(this, itemPosX, itemPosY); break;
                    case 2: new Bat(this, itemPosX, itemPosY); break;
                    default: return;
                }
            }
        }
    }

    /**
     * returns a number be in a range, e.g. input = 1 return 1 - 10, input = 2 return 11 - 19
     * @param input range of the random number (from 1 to <input>)
     * @return random number
     */
    private static int getRandomNumber (int input) {
        int min = (input - 1) * 10 + 1; // Calculate minimum
        int max = input * 10; // Calculate maximum
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    /**
     * Places GameObject in random position inside room
     * @param roomNumber room ID to place GameObject in
     * @return Position of Object as Vector2
     */
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
