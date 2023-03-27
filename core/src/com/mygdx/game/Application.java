package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;


public class Application extends ApplicationAdapter {

  public static final String TITLE = "Uni-Rogue";
  public static final int WIDTH = 800;
  public static final int HEIGHT = 600;

  private GameStateManager gsm;
  private SpriteBatch batch;


  @Override
  public void create() {
    batch = new SpriteBatch();
    gsm = new GameStateManager();
    Gdx.gl.glClearColor(0, 0, 0, 1);
    gsm.push(new MenuState(gsm));
  }

  @Override
  public void render() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    gsm.update(Gdx.graphics.getDeltaTime());
    gsm.render(batch);

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      Gdx.app.exit();
    }
  }

  @Override
  public void dispose() {
    super.dispose();
  }

}