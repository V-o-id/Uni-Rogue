package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Grid extends Label {

  private Label[][] grid;
  public final static int ROWS = 80;
  public final static int COLUMS = 80;

  private String gridCharacter;

  public Grid(String gridCharacter, Color color){
    super(gridCharacter, new LabelStyle(new BitmapFont(), color));
    this.gridCharacter = gridCharacter;

    this.grid = new Label[ROWS][COLUMS];
    Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    for(int y = 0; y < ROWS; y++){
      for(int x = 0; x < COLUMS; x++){

        grid[y][x] = new Label(gridCharacter, style);
        grid[y][x].setPosition(x*10 +100, y*10 +100);
      }
    }
  }

  public Label[][] getGrid() {
    return grid;
  }

  public void setGridCharacter(int y, int x, Label label) {
    grid[y][x] = label;
    label.setPosition(x*10 + 100, y * 10 + 100);
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
