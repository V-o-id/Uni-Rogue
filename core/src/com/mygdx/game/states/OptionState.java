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
    //OptionState representing the Options of the game (choose a name, a charater to play with and the volume of the sound
    private final TextField inputField;
    private boolean wrongInput = false;
    private final Stage stage;
    private final Label character;
    private final TextButton textButton;

    private final Label playerNameText;
    private final TextField playerName;
    private final TextButton confirmNameButton;

    private final VolumeSlider volumeSlider;
    private final Text backText;
    private final BitmapFont font = Font.getBitmapFont();
    private Text invalidPlayerCharacter;

    OptionState(final GameStateManager gsm){
        super(gsm);
        stage = new Stage();

        //Stores resoures for UI-Widgets in a json-File (Color, Font, Buttonstyles,...)
        final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        //Store ui elements in a table
        final Table table = new Table();
        table.defaults().pad(10);
        table.setFillParent(true);

        playerNameText = new Label("Playername:", skin);
        playerName = new TextField("", skin); // get latest playername from file
        confirmNameButton = new TextButton("Confirm", skin);

        character = new Label("Enter Character: ", skin);
        inputField = new TextField("", skin);
        textButton = new TextButton("Enter", skin);

        table.add(playerNameText);
        table.add(playerName).width(300);
        table.add(confirmNameButton);
        table.row();
        table.row();
        table.add(character);
        table.add(inputField).width(300);
        table.add(textButton);

        stage.addActor(table);

        //User can choose the character he wants to play with
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String input = inputField.getText();

                //user can only enter 1 char or a specific unicode
                if(input.length()==1 || (input.startsWith("\\u") && (input.length()==6 || input.length()==12))) {
                    //if the input is correct, the String of the TextField(variable input) is handed over to the setPlayCharacter method of the current player (selecter character is stored in the file selectedCharacter.txt)
                    CurrentPlayer.getCurrentPlayer().setPlayerCharacter(inputField.getText());
                    //after clicking enter go back to the menu
                    gsm.set(new MenuState(gsm));
                }
                else{
                    //if there was a wrong input, an appropriate message is printed
                    wrongInput = true;
                    invalidPlayerCharacter = new Text("Only Strings with one character are allowed", State.WIDTH / 2f, State.HEIGHT / 2.5f, font, true);
                }
            }
        });


        confirmNameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Create a new Player with all properties and the name of the textField playerName
                Playerdata playerdata = new Playerdata(playerName.getText());
                //Set current player
                CurrentPlayer.setCurrentPlayer(playerdata);
            }
        });


        //volumeSlider for changing the volume of the sound
        volumeSlider = new VolumeSlider(State.WIDTH / 2f, State.HEIGHT / 3.5f, State.WIDTH / 4F, 100, 0, 1, 0.001f, false, stage);
        stage.addActor(volumeSlider);

        backText = new Text("Back to Menu", State.WIDTH / 2f, 50, font, true);

        //if the input for the player character was right, show no error message
        if(!wrongInput){
            invalidPlayerCharacter = new Text("", State.WIDTH / 2f, State.HEIGHT / 2.5f, font, true);
        }

        //adding the table to the stage
        stage.addActor(table);
        //set stage as input processor
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
        //if escape-key get pressed or backText gets clicked go back to the last state (menu)
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
