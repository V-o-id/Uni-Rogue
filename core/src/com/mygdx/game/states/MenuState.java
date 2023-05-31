package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.data.CurrentPlayer;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;

/**
 * MenuState representing the menu of the game, selection: start the game, options (player name, player character, volume), high-score, exit Game
 */
public class MenuState extends State {
  private final Text startGameText;
  private final Text optionText;
  private final Text highscoreText;
  private final Text exitText;
  //get font for the Text from the seperate class Font
  private final BitmapFont font = Font.getBitmapFont();

  public MenuState(GameStateManager gsm) {
    super(gsm);
    //initialise the Texts and their position
    startGameText = new Text("Start Game", State.WIDTH / 2F, State.HEIGHT/2F + 3*State.HEIGHT/8F, font, true);
    optionText = new Text("Options", State.WIDTH / 2F, State.HEIGHT/2F + State.HEIGHT/8F, font, true);
    highscoreText = new Text("High-Score", State.WIDTH / 2F, State.HEIGHT/2F - State.HEIGHT/8F, font, true);
    exitText = new Text("Exit Game", State.WIDTH / 2F, State.HEIGHT/2F - 3*State.HEIGHT/8F, font, true);
  }

  @Override
  protected void handleInput() {

    if(Gdx.input.isTouched()){
      int x = Gdx.input.getX(), y = HEIGHT - Gdx.input.getY();
      //if the startGame-Text is clicked, save the playerdata, create a new game and playstate with default values, the play state is the current state now
      if(startGameText.isClicked(x, y)){
        CurrentPlayer.getCurrentPlayer().savePlayerdata(); // we only save the playerdata when the player starts a game
        GameInstance gameInstanceData = new GameInstance(CurrentPlayer.getCurrentPlayer());

        PlayState playState = new PlayState(gsm, 1, 100, 5, 0, 0, gameInstanceData);
        gsm.push(playState);
        gsm.set(playState);
      }
      //if the Options-Text is clicked, create a new OptionState and set the current state
      if(optionText.isClicked(x, y)) {
        OptionState optionState = new OptionState(gsm);
        gsm.push(optionState);
        gsm.set(optionState);
      }
      //if the High-Score-Text is clicked, create a new LeaderBoardState and set the current state
      if(highscoreText.isClicked(x, y)){
        LeaderboardState leaderboardState = new LeaderboardState(gsm);
        gsm.push(leaderboardState);
        gsm.set(leaderboardState);
      }
      //if the Exit Game-Text is clicked, exit the game
      if(exitText.isClicked(x, y)){
        Gdx.app.exit();
        System.exit(0);
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
    startGameText.drawText(sb);
    optionText.drawText(sb);
    highscoreText.drawText(sb);
    exitText.drawText(sb);
    sb.end();
  }

  @Override
  public void dispose() {

  }
}
