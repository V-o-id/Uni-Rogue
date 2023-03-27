package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Player;

import static com.mygdx.game.Application.HEIGHT;
import static com.mygdx.game.Application.WIDTH;


public class PlayState extends State{

  private Player player;
  private Enemy enemy;

  public PlayState(GameStateManager gsm) {
    super(gsm);
    player = new Player("Spieler",WIDTH/2, HEIGHT/2);
    enemy = new Enemy("Gegner", WIDTH/4, HEIGHT/4);
  }

  @Override
  protected void handleInput() {
    player.characterControl();
  }

  @Override
  public void update(float dt) {
    handleInput();
    player.update(dt);

    if(player.getRectangle().overlaps(enemy.getRectangle())){
      Gdx.app.exit();
    }
  }

  @Override
  public void render(SpriteBatch sb) {
    Gdx.gl.glClearColor(1, 0, 0, 1);
    sb.begin();
    player.getFont().draw(sb, player.getText(), player.getPostiton().x, player.getPostiton().y + player.getGlyphLayout().height);
    enemy.getFont().draw(sb, enemy.getText(), enemy.getPostiton().x, enemy.getPostiton().y + enemy.getGlyphLayout().height);
    sb.end();
  }

  @Override
  public void dispose() {

  }

}
