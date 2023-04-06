package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.lwjgl.Sys;


public class Player extends Label {

	private int gridPosX;
	private int gridPosY;
	private String playerCharacter;

	public Player(String playerCharacter, Color color, Grid grid, int gridPosX, int gridPosY) {
		super(playerCharacter, new LabelStyle(new BitmapFont(), color));
		this.playerCharacter = playerCharacter;
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		grid.setGridCharacter(gridPosY, gridPosX, this);
	}

	public void characterControl(Grid grid) {

		if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && (gridPosY + 1 < Grid.ROWS)) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGridCharacter());
			gridPosY++;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && (gridPosY > 0)) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGridCharacter());
			gridPosY--;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && (gridPosX > 0)) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGridCharacter());
			gridPosX--;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && (gridPosX + 1 < Grid.ROWS)) {
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGridCharacter());
			gridPosX++;
			grid.setGridCharacter(gridPosY, gridPosX, this);
		}
	}

	public void dispose() {
		dispose();
	}
}
