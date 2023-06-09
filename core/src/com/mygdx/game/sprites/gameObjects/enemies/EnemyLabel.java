package com.mygdx.game.sprites.gameObjects.enemies;

import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Room;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.RoomLabel;

import java.util.Random;

import static com.mygdx.game.sprites.gameObjects.RoomLabel.ROOM_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel.EnemyState.IDLE;

/**
 * Implementation of Enemies.
 */
public class EnemyLabel extends GameObjectLabel {
    public enum EnemyState {
        IDLE, AWAKE
    }

    public static final int NUM_OF_ENEMY_TYPES = 3; //UPDATE ON NEW ENEMY TYPES
    private final Grid grid;
    private int gridPosX;
    private int gridPosY;
    private int health;
    private int damage;
    private final Random random = new Random();
    private EnemyState state = IDLE;

    /**
     * Constructor for enemies. Only used as super();
     * @param enemyCharacter: The character the enemy should be represented as
     * @param style: Style of the Label. (Font, etc.)
     * @param grid: Grid the enemy is in
     * @param gridPosX: x position on the grid
     * @param gridPosY: y position on the grid
     */
    public EnemyLabel(String enemyCharacter, LabelStyle style, Grid grid, int gridPosX, int gridPosY) {
        super(enemyCharacter,style );
        this.grid = grid;
        this.gridPosX = gridPosX;
        this.gridPosY = gridPosY;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }

    /**
     *  Movement controller depending on if the Enemy is
     *  idle or a player has entered the room.
     */
    public void updateMovement() {
        switch (state) {
            case IDLE:
                moveRandomly();
                break;

            case AWAKE:
                moveTowardsPlayer();
                break;
        }
    }

    /**
     * Implementation of randomized movement.
     */
    private void moveRandomly() {
        int randInt = random.nextInt(5);
        if (randInt == 1 && (gridPosY + 1 < Grid.ROWS) && grid.getGrid()[gridPosY + 1][gridPosX] instanceof RoomLabel) {
            moveUp();
        }
        if (randInt == 2 && (gridPosY > 0) && grid.getGrid()[gridPosY - 1][gridPosX] instanceof RoomLabel) {
            moveDown();
        }
        if (randInt == 3 && (gridPosX > 0) && grid.getGrid()[gridPosY][gridPosX - 1] instanceof RoomLabel) {
            moveLeft();
        }
        if (randInt == 4 && (gridPosX + 1 < Grid.COLUMNS) && grid.getGrid()[gridPosY][gridPosX + 1] instanceof RoomLabel) {
            moveRight();
        }
    }

    /**
     * Implementation of targeted movement.
     */
    private void moveTowardsPlayer() {
        if((int)(Math.random() * 9) < 3) return; //33% chance the enemy doesn't move
        if(grid.getPlayer().getGridPosX() < gridPosX && grid.getGrid()[gridPosY][gridPosX - 1] instanceof RoomLabel) {
            moveLeft();
            return;
        }
        if(grid.getPlayer().getGridPosX() > gridPosX && grid.getGrid()[gridPosY][gridPosX + 1] instanceof RoomLabel) {
            moveRight();
            return;
        }
        if(grid.getPlayer().getGridPosY() < gridPosY && grid.getGrid()[gridPosY - 1][gridPosX] instanceof RoomLabel) {
            moveDown();
            return;
        }
        if(grid.getPlayer().getGridPosY() > gridPosY && grid.getGrid()[gridPosY + 1][gridPosX] instanceof RoomLabel) {
            moveUp();
        }
    }

    /**
     * Setter for health.
     * @param hp: Amount of Health Points an enemy has
     */
    protected void setHealth(int hp) {
        health = hp;
    }

    /**
     * Setter for damage.
     * @param damage: Amount of damage
     */
    protected void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Damages the enemy.
     * @param damage: Amount of damage
     */
    public void damage(int damage) {
        health -= damage;
    }

    /**
     * Attacks a player.
     *
     * @param player: Targeted player
     * @param damage: Amount of damage
     */
    public void attack(PlayerLabel player, int damage) {
        player.damage(damage);
    }

    /**
     * Getter for health.
     * @return health of the enemy
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for damage.
     * @return damage the enemy deals
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter for the x Position of the enemy.
     * @return x Position of the enemy on the grid
     */
    public int getGridPosX() {
        return gridPosX;
    }

    /**
     * Getter for the y Position of the enemy.
     * @return y Position of the enemy on the grid
     */
    public int getGridPosY() {
        return gridPosY;
    }

    /**
     * Sets the state the enemy is in
     * @param state: either IDLE or AWAKE
     */
    public void setState(EnemyState state) {
        this.state = state;
    }

    /**
     * Helper method for moving to the left.
     */
    private void moveLeft() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX - 1]);
        gridPosX--;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }

    /**
     * Helper method for moving to the right.
     */
    private void moveRight() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX + 1]);
        gridPosX++;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }

    /**
     * Helper method for moving up.
     */
    private void moveUp() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY + 1][gridPosX]);
        gridPosY++;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }

    /**
     * Helper method for moving down.
     */
    private void moveDown() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY - 1][gridPosX]);
        gridPosY--;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }
}
