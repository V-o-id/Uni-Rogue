package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Application;
import com.mygdx.game.sprites.Text;

public class PauseState extends State {

    private final Text pauseText;
    private final Text resumeText;
    private final Text restartText;
    private final Text returnToMenuText;
    private final Text closeGameText;
    private final Text volumeSliderText;
    private final Slider volumeSlider;

    private final int textHeight = 75; // ? how to get height of text

    PlayState playState;
    Stage stage;
    public PauseState(GameStateManager gsm, PlayState playState) {
        super(gsm);
        stage = new Stage();
        this.playState = playState;
        pauseText = new Text("Pause", State.WIDTH / 2F, State.HEIGHT * 0.9F);
        resumeText = new Text("Resume", State.WIDTH / 2F, State.HEIGHT * 0.6F);
        restartText = new Text("Restart", State.WIDTH / 2F, State.HEIGHT * 0.6F - textHeight);
        returnToMenuText = new Text("Return to Menu", State.WIDTH / 2F, State.HEIGHT * 0.6F - 2 * textHeight);
        closeGameText = new Text("Close Game", State.WIDTH / 2F, State.HEIGHT * 0.6F - 3 * textHeight);
        volumeSliderText = new Text("Volume", State.WIDTH / 2F, State.HEIGHT * 0.6F - 5 * textHeight);
        volumeSlider = new Slider(0f, 1f, 0.001f, false, new Skin(Gdx.files.internal("uiskin.json")));
        createSlider();
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

    private void createSlider() {
        volumeSlider.setValue(Application.getMusicVolume());
        System.out.println(Application.getMusicVolume());
        volumeSlider.setColor(new Color(255f,255f,255f,1f));
        volumeSlider.setPosition(State.WIDTH / 2F - State.WIDTH/8F, State.HEIGHT * 0.575F - 6 * textHeight);
        volumeSlider.setSize(State.WIDTH/4F, 100);
        stage.addActor(volumeSlider);
        Gdx.input.setInputProcessor(stage);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Application.setMusicVolume(volumeSlider.getValue());
            }
        });
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

        volumeSliderText.getFont().draw(sb, volumeSliderText.getText(), volumeSliderText.getPostiton().x, volumeSliderText.getPostiton().y + volumeSliderText.getGlyphLayout().height);
        volumeSlider.draw(sb, 1);
        
        sb.end();


    }

    @Override
    public void dispose() {

    }

}
