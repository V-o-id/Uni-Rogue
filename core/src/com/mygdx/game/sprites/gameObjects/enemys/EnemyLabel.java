package com.mygdx.game.sprites.gameObjects.enemys;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.sprites.font.Font;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;

import java.util.Random;

import static com.mygdx.game.sprites.gameObjects.enemys.EnemyLabel.EnemyState.*;
import static com.mygdx.game.sprites.gameObjects.RoomLabel.ROOM_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.PathLabel.PATH_CHARACTER;

public class EnemyLabel extends GameObjectLabel {
    enum EnemyState {
        IDLE, AWAKE, ATTACKING
    }

    private final Grid grid;
    private int gridPosX;
    private int gridPosY;
    private final Random random = new Random();
    private EnemyState state = IDLE;

    public EnemyLabel(String enemyCharacter, Grid grid, int gridPosX, int gridPosY) {
        super(enemyCharacter, new LabelStyle(new Font().setBitmapFont(), Color.WHITE));
        this.grid = grid;
        this.gridPosX = gridPosX;
        this.gridPosY = gridPosY;
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
        if (randInt == 1 && (gridPosY + 1 < Grid.ROWS) && (grid.getGrid()[gridPosY + 1][gridPosX].getText().toString().equals(ROOM_CHARACTER) || grid.getGrid()[gridPosY + 1][gridPosX].getText().toString().equals(PATH_CHARACTER))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY + 1][gridPosX]);
            gridPosY++;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
        if (randInt == 2 && (gridPosY > 0) && (grid.getGrid()[gridPosY - 1][gridPosX].getText().toString().equals(ROOM_CHARACTER) || grid.getGrid()[gridPosY - 1][gridPosX].getText().toString().equals(PATH_CHARACTER))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY - 1][gridPosX]);
            gridPosY--;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
        if (randInt == 3 && (gridPosX > 0) && (grid.getGrid()[gridPosY][gridPosX - 1].getText().toString().equals(ROOM_CHARACTER) || grid.getGrid()[gridPosY][gridPosX - 1].getText().toString().equals(PATH_CHARACTER))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX - 1]);
            gridPosX--;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
        if (randInt == 4 && (gridPosX + 1 < Grid.COLUMNS) && (grid.getGrid()[gridPosY][gridPosX + 1].getText().toString().equals(ROOM_CHARACTER) || grid.getGrid()[gridPosY][gridPosX + 1].getText().toString().equals(PATH_CHARACTER))) {
            grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX + 1]);
            gridPosX++;
            grid.setGridCharacter(gridPosY, gridPosX, this);
        }
    }

    private void moveTowardsPlayer() {

    }
}