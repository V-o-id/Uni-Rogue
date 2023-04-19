package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class Player extends Label {

	private int gridPosX;
	private int gridPosY;

	public Player(String playerCharacter, Color color, Grid grid, int gridPosX, int gridPosY) {
		super(playerCharacter, new LabelStyle(new Font().setBitmapFont(), color));
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		grid.setGridCharacter(gridPosY, gridPosX, this);
	}

	public void characterControl(Grid grid) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && (gridPosY + 1 < Grid.ROWS) && grid.getGrid()[gridPosY + 1][gridPosX].getText().toString().equals(grid.getGridCharacter())) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY + 1][gridPosX]);
			gridPosY++;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && (gridPosY > 0) && grid.getGrid()[gridPosY - 1][gridPosX].getText().toString().equals(grid.getGridCharacter())) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY - 1][gridPosX]);
			gridPosY--;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && (gridPosX > 0) && grid.getGrid()[gridPosY][gridPosX - 1].getText().toString().equals(grid.getGridCharacter())) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX - 1]);
			gridPosX--;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && (gridPosX + 1 < Grid.COLUMNS) && grid.getGrid()[gridPosY][gridPosX + 1].getText().toString().equals(grid.getGridCharacter())) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX + 1]);
			gridPosX++;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}
	}

	public void dispose() {
		this.dispose();
	}
}
