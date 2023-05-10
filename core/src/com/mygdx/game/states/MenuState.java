package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;


public class MenuState extends State {

  private final Text startGameText;
  private final Text optionText;
  private final Text highscoreText;
  private final Text exitText;
  private final BitmapFont font = new Font().setBitmapFont();

  public MenuState(GameStateManager gsm) {
    super(gsm);
    startGameText = new Text("Start Game", State.WIDTH / 2F, State.HEIGHT/2F + 3*State.HEIGHT/8F, font, true);
    optionText = new Text("Options", State.WIDTH / 2F, State.HEIGHT/2F + State.HEIGHT/8F, font, true);
    highscoreText = new Text("High-Score", State.WIDTH / 2F, State.HEIGHT/2F - State.HEIGHT/8F, font, true);
    exitText = new Text("Exit Game", State.WIDTH / 2F, State.HEIGHT/2F - 3*State.HEIGHT/8F, font, true);
  }

  @Override
  protected void handleInput() {

    if(Gdx.input.isTouched()){
      int x = Gdx.input.getX(), y = HEIGHT - Gdx.input.getY();
      if(startGameText.isClicked(x, y)){
        PlayState playState = new PlayState(gsm);
        gsm.push(playState);
        gsm.set(playState);
      }
      if(optionText.isClicked(x, y)) {
        OptionState optionState = new OptionState(gsm);
        gsm.push(optionState);
        gsm.set(optionState);
      }
      if(highscoreText.isClicked(x, y)){
        Gdx.gl.glClearColor(1, 0, 1, 1);
      }
      if(exitText.isClicked(x, y)){
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
