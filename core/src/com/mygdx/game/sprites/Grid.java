package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Application;
import sun.jvm.hotspot.gc.shared.Space;

public class Grid extends Label {

  private Label[][] grid;
  public final static int ROWS = 50;
  public final static int COLUMS = 100;
  private final static int SPACE_BETWEEN_CHARACTERS = 15;
  private final static int START_POSX_GRID = (Application.WIDTH - (COLUMS * SPACE_BETWEEN_CHARACTERS))/2;
  private final static int START_POSY_GRID = (Application.HEIGHT - (ROWS * SPACE_BETWEEN_CHARACTERS))/2;

  private String gridCharacter;

  public Grid(String gridCharacter, Color color){
    super(gridCharacter, new LabelStyle(new BitmapFont(), color));
    this.gridCharacter = gridCharacter;

    this.grid = new Label[ROWS][COLUMS];
    Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    for(int y = 0; y < ROWS; y++){
      for(int x = 0; x < COLUMS; x++){

        grid[y][x] = new Label(gridCharacter, style);
        grid[y][x].setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
      }
    }
  }

  public Label[][] getGrid() {
    return grid;
  }

  public void setGridCharacter(int y, int x, Label label) {
    grid[y][x] = label;
    label.setPosition(x * SPACE_BETWEEN_CHARACTERS + START_POSX_GRID, y * SPACE_BETWEEN_CHARACTERS + START_POSY_GRID);
  }

  public void setGridCharacter(int y, int x, String labelCharacter) {
    Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
    Label label = new Label(labelCharacter, style);

    this.setGridCharacter(y, x, label);
  }

  public String getGridCharacter() {
    return gridCharacter;
  }
}
