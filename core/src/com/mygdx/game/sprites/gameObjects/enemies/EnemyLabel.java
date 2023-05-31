package com.mygdx.game.sprites.gameObjects.enemies;

import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.Room;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.PlayerLabel;
import com.mygdx.game.sprites.gameObjects.RoomLabel;

import java.util.Random;

import static com.mygdx.game.sprites.gameObjects.RoomLabel.ROOM_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.enemies.EnemyLabel.EnemyState.IDLE;

public class EnemyLabel extends GameObjectLabel {
    public enum EnemyState {
        IDLE, AWAKE, ATTACKING
    }

    public static final int NUM_OF_ENEMY_TYPES = 3; //UPDATE ON NEW ENEMY TYPES
    private final Grid grid;
    private int gridPosX;
    private int gridPosY;
    private int health;
    private int damage;
    private final Random random = new Random();
    private EnemyState state = IDLE;

    public EnemyLabel(String enemyCharacter, LabelStyle style, Grid grid, int gridPosX, int gridPosY) {
        super(enemyCharacter,style );
//        super(enemyCharacter, color, grid, gridPosX, gridPosY);
        this.grid = grid;
        this.gridPosX = gridPosX;
        this.gridPosY = gridPosY;
//        this.setColor(color);
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }

    public void updateMovement() {

        switch (state) {
            case IDLE:
                moveRandomly();
                break;

            case AWAKE:
                moveTowardsPlayer();
                break;

            case ATTACKING:
                break;
        }

    }

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

    protected void setHealth(int hp) {
        health = hp;
    }
    protected void setDamage(int damage) {
        this.damage = damage;
    }
    public void damage(int damage) {
        health -= damage;
    }
    public void attack(PlayerLabel player, int damage) {
        player.damage(damage);
    }
    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }
    public int getGridPosX() {
        return gridPosX;
    }
    public int getGridPosY() {
        return gridPosY;
    }
    public void setState(EnemyState state) {
        this.state = state;
    }

    private void moveLeft() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX - 1]);
        gridPosX--;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }
    private void moveRight() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX + 1]);
        gridPosX++;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }
    private void moveUp() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY + 1][gridPosX]);
        gridPosY++;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }
    private void moveDown() {
        grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY - 1][gridPosX]);
        gridPosY--;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }
}
