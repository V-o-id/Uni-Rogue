package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.states.State;

public class Grid extends Label {

  private Label[][] grid;
  public final static int ROWS = 60;
  public final static int COLUMS = 120;
  private final static int SPACE_BETWEEN_CHARACTERS = 15;
  private final static int START_GRID_POSX = (State.WIDTH - (COLUMS * SPACE_BETWEEN_CHARACTERS))/2;
  private final static int START_GRID_POSY = (State.HEIGHT - (ROWS * SPACE_BETWEEN_CHARACTERS))/2;
  private final Color color;
  private final BitmapFont font;

  //optional: gridCharacter just to fill all zells with an element with the chosen character
  public Grid(String gridCharacter, Color color){
    super(gridCharacter, new LabelStyle(new Font().getBitmapFont(), color));
    //color of the label elements
    this.color = color;
    //font of the label elements
    this.font = new Font().getBitmapFont();

    this.grid = new Label[ROWS][COLUMS];

    Label.LabelStyle style = new Label.LabelStyle(font, color);

    for(int y = 0; y < ROWS; y++){
      for(int x = 0; x < COLUMS; x++){
        grid[y][x] = new Label(gridCharacter, style);
        grid[y][x].setPosition(x * SPACE_BETWEEN_CHARACTERS + START_GRID_POSX, y * SPACE_BETWEEN_CHARACTERS + START_GRID_POSY);
      }
    }
  }

  public Label[][] getGrid() {
    return grid;
  }

  public void setGridCharacter(int gridPosY, int gridPosX, Label label) {
    grid[gridPosY][gridPosX] = label;
    label.setPosition(gridPosX * SPACE_BETWEEN_CHARACTERS + START_GRID_POSX, gridPosY * SPACE_BETWEEN_CHARACTERS + START_GRID_POSY);
  }

  public void setGridCharacter(int gridPosY, int gridPosX, String labelCharacter) {
    Label.LabelStyle style = new Label.LabelStyle(font, color);
    Label label = new Label(labelCharacter, style);
    setGridCharacter(gridPosY, gridPosX, label);
  }

  public String getGridCharacter(int gridPosY, int gridPosX) {
    return grid[gridPosY][gridPosX].getText().toString();
  }
}
