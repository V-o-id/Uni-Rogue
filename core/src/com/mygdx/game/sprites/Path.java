package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.mygdx.game.sprites.gameObjects.GameObjectLabel;
import com.mygdx.game.sprites.gameObjects.PathLabel;

/**
 * Class that represents a path.
 * A path is a line on the grid that connects two rooms.
 */
public class Path {
    /**
     * x coordinate of the starting position of the path
     */
    private final int fromX;
    /**
     * y coordinate of the starting position of the path
     */
    private final int fromY;
    /**
     * x coordinate of the ending position of the path
     */
    private final int toX;
    /**
     * y coordinate of the ending position of the path
     */
    private final int toY;

    /**
     * Constructor for a path
     * @param fromX x coordinate of the starting position of the path
     * @param fromY y coordinate of the starting position of the path
     * @param toX x coordinate of the ending position of the path
     * @param toY y coordinate of the ending position of the path
     */
    public Path(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    /**
     * Draws the path on the grid by adding PathLabels to the grid
     * The path is drawn by moving from the starting position to the ending position
     * @param grid the grid that the path is drawn on
     * @param style the style of the Label
     * @param SPACE_BETWEEN_CHARACTERS the space between the characters of the grid
     * @param START_POSX_GRID the x coordinate of the starting position of the grid
     * @param START_POSY_GRID the y coordinate of the starting position of the grid
     */
    void drawPath(GameObjectLabel[][] grid, LabelStyle style, final int SPACE_BETWEEN_CHARACTERS, final int START_POSX_GRID, final int START_POSY_GRID) {

        int currX = fromX;
        int currY = fromY;

        do {
            if(currX != toX && currY != toY){
                //both have to move. make a random decision, because we can't move diagonally
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
                    // if the position is empty, add a PathLabel to the grid
                    grid[currY][currX] = new PathLabel(style);
                    grid[currY][currX].setPosition(currX * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, currY * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
                    grid[currY][currX].setColor(Color.RED);
            }

        } while (currX != toX || currY != toY);

    }

}
