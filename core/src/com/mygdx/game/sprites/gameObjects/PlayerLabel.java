package com.mygdx.game.sprites.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.sprites.Grid;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.PauseState;
import com.mygdx.game.states.PlayState;

import static com.mygdx.game.sprites.gameObjects.PathLabel.PATH_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.RoomLabel.ROOM_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.items.HealthLabel.HEALTH_CHARACTER;
import static com.mygdx.game.sprites.gameObjects.items.SwordLabel.SWORD_CHARACTER;


public class PlayerLabel extends GameObjectLabel {

	private int gridPosX;
	private int gridPosY;
	private static final String playerCharacter = Gdx.files.local("selectedCharacter.txt").readString();
	private String previousCharacter;
	private int health = 10;
	private int attackDamage = 5;

	public PlayerLabel(Grid grid, LabelStyle style, int gridPosX, int gridPosY) {
		super(playerCharacter, style);
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		grid.setGridCharacter(gridPosY, gridPosX, this);
		this.previousCharacter = ROOM_CHARACTER;
	}

	public void characterControl(Grid grid, GameStateManager gsm, PlayState playState) {

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && (gridPosY + 1 < Grid.ROWS)) {

					String topCharacter = grid.getGrid()[gridPosY + 1][gridPosX].getLabelString();

					//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
					if (topCharacter.equals(ROOM_CHARACTER) || topCharacter.equals(PATH_CHARACTER) || topCharacter.equals(SWORD_CHARACTER) || topCharacter.equals(HEALTH_CHARACTER)) {
						grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
						gridPosY++;
						collectItems(topCharacter, grid);
						grid.setGridCharacter(gridPosY, gridPosX, this);
						grid.updateEnemies();
					}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && (gridPosY > 0)) {

			String bottomCharacter = grid.getGrid()[gridPosY - 1][gridPosX].getLabelString();

			//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
			if (bottomCharacter.equals(ROOM_CHARACTER) || bottomCharacter.equals(PATH_CHARACTER) || bottomCharacter.equals(SWORD_CHARACTER) || bottomCharacter.equals(HEALTH_CHARACTER)) {
				grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
				gridPosY--;
				collectItems(bottomCharacter, grid);
				grid.setGridCharacter(gridPosY, gridPosX, this);
				grid.updateEnemies();
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && (gridPosX > 0)) {

			String leftCharacter = grid.getGrid()[gridPosY][gridPosX - 1].getLabelString();

			//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
			if (leftCharacter.equals(ROOM_CHARACTER) || leftCharacter.equals(PATH_CHARACTER) || leftCharacter.equals(SWORD_CHARACTER) || leftCharacter.equals(HEALTH_CHARACTER)) {
				grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
				gridPosX--;
				collectItems(leftCharacter, grid);
				grid.setGridCharacter(gridPosY, gridPosX, this);
				grid.updateEnemies();
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && (gridPosX + 1 < Grid.COLUMNS)) {

			String rightCharacter = grid.getGrid()[gridPosY][gridPosX + 1].getLabelString();

			//TODO: Find better way instead of the very long if - also bad if the add an item/enemy, we need to add to if
			if (rightCharacter.equals(ROOM_CHARACTER) || rightCharacter.equals(PATH_CHARACTER) || rightCharacter.equals(SWORD_CHARACTER) || rightCharacter.equals(HEALTH_CHARACTER)) {
				grid.setGridCharacter(gridPosY, gridPosX, previousCharacter);
				gridPosX++;
				collectItems(rightCharacter, grid);
				grid.setGridCharacter(gridPosY, gridPosX, this);
				grid.updateEnemies();
			}
		}

		//open pause menu
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			playState.pause();
			gsm.push(new PauseState(gsm, playState));
		}

	}

	private void collectItems(String character, Grid grid){

		switch (character) {
			case SWORD_CHARACTER: previousCharacter = ROOM_CHARACTER; setAttackDamage(grid.getGrid()[gridPosY][gridPosX].getObjectValue()); break;
			case HEALTH_CHARACTER: previousCharacter = ROOM_CHARACTER; setHealth(grid.getGrid()[gridPosY][gridPosX].getObjectValue()); break;
			default: previousCharacter = character;
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public void dispose() {

	}
}
