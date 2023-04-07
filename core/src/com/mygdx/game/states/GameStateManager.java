package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

  private final Stack<State> states;

  public GameStateManager(){
    states = new Stack<>();
  }

  public void push(State state){
    states.push(state);
  }

  public void pop(){
    states.pop().dispose();
  }

  public void set(State state){
    states.pop().dispose();
    states.push(state);
  }

  public void update(float dt){
    states.peek().update(dt);
  }

  public void render(SpriteBatch sb){
    states.peek().render(sb);
  }
}
