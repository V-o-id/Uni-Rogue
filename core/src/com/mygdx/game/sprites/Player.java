package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.sprites.enemies.Snake;
import com.sun.org.apache.bcel.internal.Const;

import java.lang.reflect.Array;


public class Player extends Label {

	private int gridPosX;
	private int gridPosY;
	private int poisonDuration = 0;
	private int health = 100;
	private final int damage = 10;

	public Player(String playerCharacter, Color color, Grid grid, int gridPosX, int gridPosY) {
		super(playerCharacter, new LabelStyle(new Font().setBitmapFont(), color));
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		grid.setGridCharacter(gridPosY, gridPosX, this);
	}

	public void characterControl(Grid grid) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && (gridPosY + 1 < Grid.ROWS) && (grid.getGrid()[gridPosY + 1][gridPosX].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY + 1][gridPosX].getText().toString().equals(grid.getPathCharacter()))) {
			damage(0); //for poison damage
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY + 1][gridPosX]);
			gridPosY++;
			grid.setGridCharacter(gridPosY, gridPosX, this);
			grid.updateEnemies();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && (gridPosY > 0) && (grid.getGrid()[gridPosY - 1][gridPosX].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY - 1][gridPosX].getText().toString().equals(grid.getPathCharacter()))) {
			damage(0);
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY - 1][gridPosX]);
			gridPosY--;
			grid.setGridCharacter(gridPosY, gridPosX, this);
			grid.updateEnemies();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && (gridPosX > 0) && (grid.getGrid()[gridPosY][gridPosX - 1].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY][gridPosX - 1].getText().toString().equals(grid.getPathCharacter()))) {
			damage(0);
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX - 1]);
			gridPosX--;
			grid.setGridCharacter(gridPosY, gridPosX, this);
			grid.updateEnemies();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && (gridPosX + 1 < Grid.COLUMNS) && (grid.getGrid()[gridPosY][gridPosX + 1].getText().toString().equals(grid.getGridCharacter()) || grid.getGrid()[gridPosY][gridPosX + 1].getText().toString().equals(grid.getPathCharacter()))) {
			damage(0);
			grid.setGridCharacter(gridPosY, gridPosX, grid.getGrid()[gridPosY][gridPosX + 1]);
			gridPosX++;
			grid.setGridCharacter(gridPosY, gridPosX, this);
			grid.updateEnemies();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			attackEnemies(grid, gridPosX, gridPosY);
			grid.updateEnemies();
		}
	}

	private void attackEnemies(Grid grid, int gridPosX, int gridPosY) {
		Enemy enemy;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(gridPosX + i < 0 || gridPosY + j < 0) break;
				if(grid.getGrid()[i + gridPosY][j + gridPosX] instanceof Enemy) {
					enemy = (Enemy) grid.getGrid()[i + gridPosY][j + gridPosX];
					attack(damage, enemy, grid);
					damage(enemy.getDamage());
					if(enemy instanceof Snake) poison(5);
				}
			}
		}
	}

	public void damage(int damage) {
		health -= damage;
		if(poisonDuration > 0) {
			health -= 3; //poison damage
			poisonDuration--;
		}
		if(health <= 0) {
			System.out.println("GAME OVER");
		}
		System.out.println(health);
	}

	private void attack(int damage, Enemy target, Grid grid) {
		target.damage(damage);
		if(target.getHealth() <= 0) {
			grid.removeEnemy(target);
			grid.setGridCharacter(target.getGridPosY(), target.getGridPosX(), new Label(".", Constants.STYLE));
		}
	}

	public void poison(int duration) {
		poisonDuration = duration;
	}

	public void dispose() {
		this.dispose();
	}
}
