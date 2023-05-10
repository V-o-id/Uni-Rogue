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
    try {
      states.pop().dispose();
    } catch (Exception e) {
      System.err.println("ERROR. There was no state to pop.");
      e.printStackTrace();
    }
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
