package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {

  public static final int WIDTH = Gdx.graphics.getWidth();
  public static final int HEIGHT = Gdx.graphics.getHeight();

  protected GameStateManager gsm;

  public State(GameStateManager gsm){
    this.gsm = gsm;
  }

  protected abstract void handleInput();
  public abstract void update(float dt);
  public abstract void render(SpriteBatch sb);
  public abstract void dispose();
}

