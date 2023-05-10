package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.font.Font;

public class PauseState extends State {

    private final Text pauseText;
    private final Text resumeText;
    private final Text restartText;
    private final Text returnToMenuText;
    private final Text closeGameText;
    private final BitmapFont font = new Font().setFont();

    private final int textHeight = 75; // ? how to get height of text

    PlayState playState;

    public PauseState(GameStateManager gsm, PlayState playState) {
        super(gsm);
        this.playState = playState;
        pauseText = new Text("Pause", State.WIDTH / 2F, State.HEIGHT * 0.9F, font, true);
        resumeText = new Text("Resume", State.WIDTH / 2F, State.HEIGHT * 0.6F, font, true);
        restartText = new Text("Restart", State.WIDTH / 2F, State.HEIGHT * 0.6F - textHeight, font, true);
        returnToMenuText = new Text("Return to Menu", State.WIDTH / 2F, State.HEIGHT * 0.6F - 2 * textHeight, font, true);
        closeGameText = new Text("Close Game", State.WIDTH / 2F, State.HEIGHT * 0.6F - 3 * textHeight, font, true);
    }

    @Override
    protected void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            //close pause
            playState.resume();
            gsm.pop();
        }

        if (Gdx.input.isTouched()) {
            if (resumeText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())) {
                //close pause
                playState.resume();
                gsm.pop();
            }
            if (restartText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())) {
                //restart game
                gsm.set(new PlayState(gsm));
            }
            if (returnToMenuText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())) {
                //return to menu
                gsm.set(new MenuState(gsm));
            }
            if (closeGameText.getRectangle().contains(Gdx.input.getX(), HEIGHT - Gdx.input.getY())) {
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

        sb.end();


    }

    @Override
    public void dispose() {

    }

}
