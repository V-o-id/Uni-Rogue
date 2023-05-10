package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.VolumeSlider;
import com.mygdx.game.sprites.font.Font;

public class PauseState extends State {

    private final Text pauseText;
    private final Text resumeText;
    private final Text restartText;
    private final Text returnToMenuText;
    private final Text closeGameText;
    private final BitmapFont font = new Font().setFont();

    private final VolumeSlider volumeSlider;

    private final int textHeight = 75; // ? how to get height of text

    PlayState playState;
    Stage stage;
    public PauseState(GameStateManager gsm, PlayState playState) {
        super(gsm);
        stage = new Stage();
        this.playState = playState;
        pauseText = new Text("Pause", State.WIDTH / 2F, State.HEIGHT * 0.9F);
        resumeText = new Text("Resume", State.WIDTH / 2F, State.HEIGHT * 0.65F);
        restartText = new Text("Restart", State.WIDTH / 2F, State.HEIGHT * 0.65F - textHeight);
        returnToMenuText = new Text("Return to Menu", State.WIDTH / 2F, State.HEIGHT * 0.65F - 2 * textHeight);
        closeGameText = new Text("Close Game", State.WIDTH / 2F, State.HEIGHT * 0.65F - 3 * textHeight);
        volumeSlider = new VolumeSlider(State.WIDTH / 2F, State.HEIGHT * 0.65F - 6 * textHeight, State.WIDTH/4F, 100, 0f, 1f, 0.001f, false, new Stage());
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            //close pause
            playState.resume();
            gsm.pop();
        }

        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX(), y = HEIGHT - Gdx.input.getY();
            if (resumeText.isClicked(x, y)) {
                //close pause
                playState.resume();
                gsm.pop();
            }
            if (restartText.isClicked(x, y)) {
                //restart game
                gsm.pop();
                gsm.set(new PlayState(gsm));
            }
            if (returnToMenuText.isClicked(x, y)) {
                //return to menu
                gsm.pop();
                gsm.pop();
            }
            if (closeGameText.isClicked(x, y)) {
                //close game
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

        pauseText.getFont().draw(sb, pauseText.getText(), pauseText.getPostiton().x, pauseText.getPostiton().y + pauseText.getGlyphLayout().height);
        resumeText.getFont().draw(sb, resumeText.getText(), resumeText.getPostiton().x, resumeText.getPostiton().y + resumeText.getGlyphLayout().height);
        restartText.getFont().draw(sb, restartText.getText(), restartText.getPostiton().x, restartText.getPostiton().y + restartText.getGlyphLayout().height);
        returnToMenuText.getFont().draw(sb, returnToMenuText.getText(), returnToMenuText.getPostiton().x, returnToMenuText.getPostiton().y + returnToMenuText.getGlyphLayout().height);
        closeGameText.getFont().draw(sb, closeGameText.getText(), closeGameText.getPostiton().x, closeGameText.getPostiton().y + closeGameText.getGlyphLayout().height);

        volumeSlider.draw(sb, 1);

        sb.end();


    }

    @Override
    public void dispose() {

    }

}
