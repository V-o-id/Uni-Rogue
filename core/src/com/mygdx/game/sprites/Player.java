package com.mygdx.game.sprites;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class Player extends Text {

	public Player(CharSequence playerCharacter, int x, int y) {
		super(playerCharacter, x, y);
	}

	public void characterControl() {

		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			setPostiton(new Vector3(getPostiton().x, getPostiton().y + 2, 0));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			setPostiton(new Vector3(getPostiton().x, getPostiton().y - 2, 0));
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			setPostiton(new Vector3(getPostiton().x - 2, getPostiton().y, 0));
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			setPostiton(new Vector3(getPostiton().x + 2, getPostiton().y, 0));
		}
	}
}
