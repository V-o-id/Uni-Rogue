package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.VolumeSlider;
import com.mygdx.game.sprites.font.Font;
//import org.apache.commons.text.StringEscapeUtils;

public class OptionState extends State {
    //private final Text;
    private final TextField inputField;
    private boolean wrongInput = false;
    private Stage stage;
    private Label character;
    private TextButton textButton;
    private final VolumeSlider volumeSlider;
    private final Text backText;
    private final BitmapFont font = Font.getBitmapFont();
    private Text invalidPlayerCharacter;

    OptionState(final GameStateManager gsm){
        super(gsm);
        stage = new Stage();

        final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        final Table table = new Table();
        table.defaults().pad(10);
        table.setFillParent(true);

        character = new Label("Enter Character: ", skin);
        inputField = new TextField("", skin);
        textButton = new TextButton("Enter", skin);

        table.add(character);
        table.add(inputField).width(300);
        table.add(textButton);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileHandle file = Gdx.files.local("selectedCharacter.txt");
                String input = inputField.getText();

                if(input.length()==1 || (input.startsWith("\\u") && input.length()==6)) {
                    file.writeString(inputField.getText(), false);
                    gsm.set(new MenuState(gsm));
                }
                else{
                    wrongInput = true;
                    invalidPlayerCharacter = new Text("Only Strings with one character are allowed", State.WIDTH / 2f, State.HEIGHT / 2.5f, font, true);
                }
            }
        });

        volumeSlider = new VolumeSlider(State.WIDTH / 2f, State.HEIGHT / 3.5f, State.WIDTH / 4F, 100, 0, 1, 0.001f, false, stage);
        stage.addActor(volumeSlider);

        backText = new Text("Back to Menu", State.WIDTH / 2f, 50, font, true);

        if(!wrongInput){
            invalidPlayerCharacter = new Text("", State.WIDTH / 2f, State.HEIGHT / 2.5f, font, true);
        }

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        stage.act();
        stage.draw();
        backText.getFont().draw(sb, backText.getText(), backText.getPosition().x, backText.getPosition().y + backText.getGlyphLayout().height);
        invalidPlayerCharacter.getFont().draw(sb, invalidPlayerCharacter.getText(), invalidPlayerCharacter.getPosition().x, invalidPlayerCharacter.getPosition().y + invalidPlayerCharacter.getGlyphLayout().height);
        sb.end();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            if(backText.isClicked(Gdx.input.getX(), HEIGHT - Gdx.input.getY())){
                gsm.pop();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gsm.pop();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void dispose() {
    }
}
