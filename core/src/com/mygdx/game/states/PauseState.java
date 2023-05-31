package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.data.CurrentPlayer;
import com.mygdx.game.data.GameInstance;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.VolumeSlider;
import com.mygdx.game.sprites.font.Font;


/**
 * The Pause-Menu State of the game.
 * <br>
 * The Pause-Menu State is a State that is called when the player presses the ESC key during the PlayState. <br>
 * It is a menu that allows the player to resume the game, restart the game, return to the main menu, close the game or change the volume.
 */
public class PauseState extends State {
    //PauseState representing the Pause-Menu of the game, selections: resume, restart, return to menu, close game, change volume

    private final Text pauseText;
    private final Text resumeText;
    private final Text restartText;
    private final Text returnToMenuText;
    private final Text closeGameText;

    private final VolumeSlider volumeSlider;

    PlayState playState;
    Stage stage;

    /**
     * Constructor for initializing the PauseState.
     * @param gsm Reference to the {@link GameStateManager}
     * @param playState Reference to the {@link PlayState}
     */
    public PauseState(GameStateManager gsm, PlayState playState) {
        super(gsm);
        BitmapFont font = Font.getBitmapFont();
        font.setColor(Color.WHITE); // otherwise poison makes text purple
        stage = new Stage();
        this.playState = playState;
        pauseText = new Text("Pause", State.WIDTH / 2F, State.HEIGHT * 0.9F, font, true);
        resumeText = new Text("Resume", State.WIDTH / 2F, State.HEIGHT * 0.65F, font, true);
        // ? how to get height of text
        int textHeight = 75;
        restartText = new Text("Restart", State.WIDTH / 2F, State.HEIGHT * 0.65F - textHeight, font, true);
        returnToMenuText = new Text("Return to Menu", State.WIDTH / 2F, State.HEIGHT * 0.65F - 2 * textHeight, font, true);
        closeGameText = new Text("Close Game", State.WIDTH / 2F, State.HEIGHT * 0.65F - 3 * textHeight, font, true);
        volumeSlider = new VolumeSlider(State.WIDTH / 2F, State.HEIGHT * 0.65F - 6 * textHeight, State.WIDTH/4F, 100, 0f, 1f, 0.001f, false, new Stage());
    }

    @Override
    protected void handleInput() {

        //if escaped is pressed, continue playing with the playState from before
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            //close pause
            volumeSlider.remove();
            playState.resume();
            gsm.pop();
        }

        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX(), y = HEIGHT - Gdx.input.getY();
            //if resume is clicked, continue playing with the playState from before
            if (resumeText.isClicked(x, y)) {
                //close pause
                volumeSlider.remove();
                playState.resume();
                gsm.pop();
            }
            if (restartText.isClicked(x, y)) {
                //restart game
                gsm.pop();
                gsm.set(new PlayState(gsm, 1,  100, 5, 0, 0, new GameInstance(CurrentPlayer.getCurrentPlayer())));
            }
            if (returnToMenuText.isClicked(x, y)) {
                //return to menu
                volumeSlider.remove();
                gsm.pop();
                gsm.pop();
            }
            if (closeGameText.isClicked(x, y)) {
                //close game
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

        pauseText.getFont().draw(sb, pauseText.getText(), pauseText.getPosition().x, pauseText.getPosition().y + pauseText.getGlyphLayout().height);
        resumeText.getFont().draw(sb, resumeText.getText(), resumeText.getPosition().x, resumeText.getPosition().y + resumeText.getGlyphLayout().height);
        restartText.getFont().draw(sb, restartText.getText(), restartText.getPosition().x, restartText.getPosition().y + restartText.getGlyphLayout().height);
        returnToMenuText.getFont().draw(sb, returnToMenuText.getText(), returnToMenuText.getPosition().x, returnToMenuText.getPosition().y + returnToMenuText.getGlyphLayout().height);
        closeGameText.getFont().draw(sb, closeGameText.getText(), closeGameText.getPosition().x, closeGameText.getPosition().y + closeGameText.getGlyphLayout().height);

        volumeSlider.draw(sb, 1);

        sb.end();
    }

    @Override
    public void dispose() {
    }
}
