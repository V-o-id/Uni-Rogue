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
import com.mygdx.game.data.CurrentPlayer;
import com.mygdx.game.data.Playerdata;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.VolumeSlider;
import com.mygdx.game.sprites.font.Font;
//import org.apache.commons.text.StringEscapeUtils;

public class OptionState extends State {

    private final TextField inputField;
    private Stage stage;
    Label character;
    private TextButton textButton;

    private final Label playerNameText;
    private final TextField playerName;
    private final TextButton confirmNameButton;

    private final VolumeSlider volumeSlider;
    private final Text backText;
    private final BitmapFont font = Font.getBitmapFont();

    OptionState(final GameStateManager gsm){
        super(gsm);
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.defaults().pad(10);

        table.setFillParent(true);

        playerNameText = new Label("Playername:", skin);
        playerName = new TextField("", skin); // get latest playername from file
        confirmNameButton = new TextButton("Confirm", skin);

        character = new Label("Enter Character: ", skin);
        inputField = new TextField("", skin);
        textButton = new TextButton("Confirm", skin);


        table.add(playerNameText);
        table.add(playerName).width(300);
        table.add(confirmNameButton);
        table.row();
        table.row();
        table.add(character);
        table.add(inputField).width(300);
        table.add(textButton);

        stage.addActor(table);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //write character into textfile
                FileHandle file = Gdx.files.local("selectedCharacter.txt");
                file.writeString(inputField.getText(), false);
                CurrentPlayer.getCurrentPlayer().setPlayerCharacter(inputField.getText());
            }
        });
        confirmNameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Playerdata playerdata = new Playerdata(playerName.getText());
                //read character from textfile
                String character = Gdx.files.local("selectedCharacter.txt").readString();
                if(character.equals("")){
                    character = "*";
                }
                playerdata.setPlayerCharacter(character);
                System.out.println(playerdata.getPlayerCharacter() + " " + playerdata.getPlayerName());
                CurrentPlayer.setCurrentPlayer(playerdata);
            }
        });


        volumeSlider = new VolumeSlider(State.WIDTH / 2f, State.HEIGHT / 3.5f, State.WIDTH / 4F, 100, 0, 1, 0.001f, false, stage);
        stage.addActor(volumeSlider);

        backText = new Text("Back to Menu", State.WIDTH / 2f, 50, font, true);


        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(SpriteBatch sb){
        sb.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        backText.getFont().draw(sb, backText.getText(), backText.getPosition().x, backText.getPosition().y + backText.getGlyphLayout().height);
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
