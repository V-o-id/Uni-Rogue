package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.PathLabel;

public class Path {
    private final int fromX;
    private final int fromY;
    private final int toX;
    private final int toY;
    private final int pathNumber;
    private final int roomN1;
    private final int roomN2;

    public Path(int fromX, int fromY, int toX, int toY, int pathNumber, int roomN1, int roomN2) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.pathNumber = pathNumber;
        this.roomN1 = roomN1;
        this.roomN2 = roomN2;
    }


    void drawPath(GameObjectLabel[][] grid, LabelStyle style, final int SPACE_BETWEEN_CHARACTERS, final int START_POSX_GRID, final int START_POSY_GRID) {

        int currX = fromX;
        int currY = fromY;

        do {
            if(currX != toX && currY != toY){
                //both have to move. make a random decision
                if(Math.random() > 0.5){
                    if (currX < toX) {
                        currX++;
                    } else {
                        currX--;
                    }
                }else{
                    if (currY < toY) {
                        currY++;
                    } else {
                        currY--;
                    }
                }
            } else {
                //only one has to move
                if (currX < toX) {
                    currX++;
                } else if (currX > toX) {
                    currX--;
                }
                if (currY < toY) {
                    currY++;
                } else if (currY > toY) {
                    currY--;
                }
            }

            if (grid[currY][currX] == null) {
                    grid[currY][currX] = new PathLabel(style);
                    grid[currY][currX].setPosition(currX * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, currY * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
                    grid[currY][currX].setColor(Color.RED);
            }

        } while (currX != toX || currY != toY);





    }

}
