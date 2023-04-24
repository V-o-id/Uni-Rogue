package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Random;

public class Enemy extends Label {
    enum enemyStates {
        IDLE {

        },
        AWAKE {

        },
        ATTACKING {

        }
    }

    private int gridPosX;
    private int gridPosY;

    public Enemy(String enemyCharacter, Grid grid, int gridPosX, int gridPosY) {
        super(enemyCharacter, new LabelStyle(new Font().setBitmapFont(), Color.WHITE));
        this.gridPosX = gridPosX;
        this.gridPosY = gridPosY;
        grid.setGridCharacter(gridPosY, gridPosX, this);
    }

    public void updateMovement(Grid grid) {
        Random random = new Random();
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
}
