package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Text;

import static com.mygdx.game.Application.HEIGHT;
import static com.mygdx.game.Application.WIDTH;

public class MenuState extends State {

  private final Text startGameText;
  private final Text optionText;
  private final Text highscoreText;
  private final Text exitText;

  public MenuState(GameStateManager gsm) {
    super(gsm);
    startGameText = new Text("Start Game", WIDTH / 2F, HEIGHT/2 + 3*HEIGHT/8);
    optionText = new Text("Options", WIDTH / 2F, HEIGHT/2 + HEIGHT/8);
    highscoreText = new Text("Highscore", WIDTH / 2F, HEIGHT/2 - HEIGHT/8);
    exitText = new Text("Exit Game", WIDTH / 2F, HEIGHT/2 - 3*HEIGHT/8);
  }

  @Override
  protected void handleInput() {

    if(Gdx.input.isTouched()){
      if(startGameText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())){
        gsm.set(new PlayState(gsm));
      }
      if(optionText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())) {
        //Gdx.gl.glClearColor(1, 1, 0, 1);
        gsm.set(new OptionState(gsm));
      }
      if(highscoreText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())){
        Gdx.gl.glClearColor(1, 0, 1, 1);
      }
      if(exitText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())){
        Gdx.app.exit();
      }
    }
  }

  @Override
  public void update(float dt) {
    handleInput();
  }

  @Override
  public void render(SpriteBatch sb) {
    sb.begin();
    startGameText.getFont().draw(sb, startGameText.getText(), startGameText.getPostiton().x, startGameText.getPostiton().y + startGameText.getGlyphLayout().height);
    optionText.getFont().draw(sb, optionText.getText(), optionText.getPostiton().x, optionText.getPostiton().y + optionText.getGlyphLayout().height);
    highscoreText.getFont().draw(sb, highscoreText.getText(), highscoreText.getPostiton().x, highscoreText.getPostiton().y + highscoreText.getGlyphLayout().height);
    exitText.getFont().draw(sb, exitText.getText(), exitText.getPostiton().x, exitText.getPostiton().y + exitText.getGlyphLayout().height);
    sb.end();
  }

  @Override
  public void dispose() {

  }
}
