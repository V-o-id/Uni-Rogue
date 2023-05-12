package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Random;

public class Enemy extends Label {
    enum EnemyState {
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

    public Enemy(String enemyCharacter, Grid grid, int gridPosX, int gridPosY, Color color) {
        super(enemyCharacter, new LabelStyle(new Font().setBitmapFont(), Color.WHITE));
        this.grid = grid;
        this.gridPosX = gridPosX;
        this.gridPosY = gridPosY;
        this.setColor(color);
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
        if (randInt == 1 && (gridPosY + 1 < Grid.ROWS) && (grid.getGrid()[gridPosY + 1][gridPosX].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY + 1][gridPosX].getText().toString().equals(grid.getPathCharacter()))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY + 1][gridPosX]);
            gridPosY++;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
        if (randInt == 2 && (gridPosY > 0) && (grid.getGrid()[gridPosY - 1][gridPosX].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY - 1][gridPosX].getText().toString().equals(grid.getPathCharacter()))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY - 1][gridPosX]);
            gridPosY--;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
        if (randInt == 3 && (gridPosX > 0) && (grid.getGrid()[gridPosY][gridPosX - 1].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY][gridPosX - 1].getText().toString().equals(grid.getPathCharacter()))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX - 1]);
            gridPosX--;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
        if (randInt == 4 && (gridPosX + 1 < Grid.COLUMNS) && (grid.getGrid()[gridPosY][gridPosX + 1].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY][gridPosX + 1].getText().toString().equals(grid.getPathCharacter()))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX + 1]);
            gridPosX++;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
    }

    private void moveTowardsPlayer() {

    }
  
    protected void setHealth(int hp) {
        health = hp;
    }
    protected void setDamage(int damage) {
        this.damage = damage;
    }
    void damage(int damage) {
        health -= damage;
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
