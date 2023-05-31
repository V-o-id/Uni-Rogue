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
import com.mygdx.game.data.PlayerData;
import com.mygdx.game.sprites.Text;
import com.mygdx.game.sprites.VolumeSlider;
import com.mygdx.game.sprites.font.Font;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * OptionState representing the Options of the game (choose a name, a charater to play with and the volume of the sound
 */
public class OptionState extends State {
    private final TextField inputField;
    private boolean wrongInput = false;
    private final Stage stage;
    private final Label character;
    private final TextButton textButton;

    private final TextButton randomCharacterButton;

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
        playerName = new TextField(CurrentPlayer.getCurrentPlayer().getName(), skin); // get latest playername from file
        confirmNameButton = new TextButton("Confirm", skin);

        character = new Label("Enter Character: ", skin);
        inputField = new TextField(CurrentPlayer.getCurrentPlayer().getPlayerCharacter(), skin);
        textButton = new TextButton("Enter", skin);


        randomCharacterButton = new TextButton("Random Character", skin);

        table.add(playerNameText);
        table.add(playerName).width(300);
        table.add(confirmNameButton);
        table.row();
        table.row();
        table.add(character);
        table.add(inputField).width(300);
        table.add(textButton);
        table.row();
        table.add();
        table.add(randomCharacterButton).width(300);

        stage.addActor(table);

        //User can choose the character he wants to play with
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String input = inputField.getText();

                //user can only enter 1 char or a specific unicode
                if(input.length()==1 || (input.startsWith("\\u") && (input.length()==6 || input.length()==12))) {
                    //if the input is correct, the String of the TextField(variable input) is handed over to the setPlayCharacter method of the current player (selecter character is stored in the file selectedCharacter.txt)
                    FileHandle file = Gdx.files.local("files/selectedCharacter.txt");
                    file.writeString(input, false);
                    CurrentPlayer.getCurrentPlayer().setPlayerCharacter(inputField.getText());
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
                PlayerData playerdata = new PlayerData(playerName.getText());
                //Set current player
                CurrentPlayer.setCurrentPlayer(playerdata);
            }
        });

        randomCharacterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String s = OptionState.getRandomEmoji();
                if(s == null) {
                    s = "😀";
                }
                inputField.setText(s);
                textButton.toggle();
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

    /**
     * Returns a random emoji
     * @return a random emoji as String in \\uXXXX\\uXXXX or \\uXXXX format
     */
    //collapse this method, because it is too long
    private static String getRandomEmoji() {
        String[] emojis = new String[] {"\\u270b", "\\u270a", "\\u2615", "\\u26ea", "\\u26f2", "\\u26fa", "\\u26fd", "\\u2693", "\\u26f5", "\\u231b", "\\u23f3", "\\u231a", "\\u23f0", "\\u2b50", "\\u26c5", "\\u2614", "\\u26a1", "\\u26c4", "\\u2728", "\\u26bd", "\\u26be", "\\u26f3", "\\u267f", "\\u26d4", "\\u2648", "\\u2649", "\\u264a", "\\u264b", "\\u264c", "\\u264d", "\\u264e", "\\u264f", "\\u2650", "\\u2651", "\\u2652", "\\u2653", "\\u26ce", "\\u23e9", "\\u23ea", "\\u23eb", "\\u23ec", "\\u2795", "\\u2796", "\\u2797", "\\u2753", "\\u2754", "\\u2755", "\\u2757", "\\u2b55", "\\u2705", "\\u274c", "\\u274e", "\\u27b0", "\\u27bf", "\\u26ab", "\\u26aa", "\\u2b1b", "\\u2b1c", "\\u25fe", "\\u25fd", "\\ud83d\\ude00", "\\ud83d\\ude03", "\\ud83d\\ude04", "\\ud83d\\ude01", "\\ud83d\\ude06", "\\ud83d\\ude05", "\\ud83e\\udd23", "\\ud83d\\ude02", "\\ud83d\\ude42", "\\ud83d\\ude43", "\\ud83e\\udee0", "\\ud83d\\ude09", "\\ud83d\\ude0a", "\\ud83d\\ude07", "\\ud83e\\udd70", "\\ud83d\\ude0d", "\\ud83e\\udd29", "\\ud83d\\ude18", "\\ud83d\\ude17", "\\u263a\\ufe0f", "\\ud83d\\ude1a", "\\ud83d\\ude19", "\\ud83e\\udd72", "\\ud83d\\ude0b", "\\ud83d\\ude1b", "\\ud83d\\ude1c", "\\ud83e\\udd2a", "\\ud83d\\ude1d", "\\ud83e\\udd11", "\\ud83e\\udd17", "\\ud83e\\udd2d", "\\ud83e\\udee2", "\\ud83e\\udee3", "\\ud83e\\udd2b", "\\ud83e\\udd14", "\\ud83e\\udee1", "\\ud83e\\udd10", "\\ud83e\\udd28", "\\ud83d\\ude10", "\\ud83d\\ude11", "\\ud83d\\ude36", "\\ud83e\\udee5", "\\ud83d\\ude0f", "\\ud83d\\ude12", "\\ud83d\\ude44", "\\ud83d\\ude2c", "\\ud83e\\udd25", "\\ud83d\\ude0c", "\\ud83d\\ude14", "\\ud83d\\ude2a", "\\ud83e\\udd24", "\\ud83d\\ude34", "\\ud83d\\ude37", "\\ud83e\\udd12", "\\ud83e\\udd15", "\\ud83e\\udd22", "\\ud83e\\udd2e", "\\ud83e\\udd27", "\\ud83e\\udd75", "\\ud83e\\udd76", "\\ud83e\\udd74", "\\ud83d\\ude35", "\\ud83e\\udd2f", "\\ud83e\\udd20", "\\ud83e\\udd73", "\\ud83e\\udd78", "\\ud83d\\ude0e", "\\ud83e\\udd13", "\\ud83e\\uddd0", "\\ud83d\\ude15", "\\ud83e\\udee4", "\\ud83d\\ude1f", "\\ud83d\\ude41", "\\u2639\\ufe0f", "\\ud83d\\ude2e", "\\ud83d\\ude2f", "\\ud83d\\ude32", "\\ud83d\\ude33", "\\ud83e\\udd7a", "\\ud83e\\udd79", "\\ud83d\\ude26", "\\ud83d\\ude27", "\\ud83d\\ude28", "\\ud83d\\ude30", "\\ud83d\\ude25", "\\ud83d\\ude22", "\\ud83d\\ude2d", "\\ud83d\\ude31", "\\ud83d\\ude16", "\\ud83d\\ude23", "\\ud83d\\ude1e", "\\ud83d\\ude13", "\\ud83d\\ude29", "\\ud83d\\ude2b", "\\ud83e\\udd71", "\\ud83d\\ude24", "\\ud83d\\ude21", "\\ud83d\\ude20", "\\ud83e\\udd2c", "\\ud83d\\ude08", "\\ud83d\\udc7f", "\\ud83d\\udc80", "\\u2620\\ufe0f", "\\ud83d\\udca9", "\\ud83e\\udd21", "\\ud83d\\udc79", "\\ud83d\\udc7a", "\\ud83d\\udc7b", "\\ud83d\\udc7d", "\\ud83d\\udc7e", "\\ud83e\\udd16", "\\ud83d\\ude3a", "\\ud83d\\ude38", "\\ud83d\\ude39", "\\ud83d\\ude3b", "\\ud83d\\ude3c", "\\ud83d\\ude3d", "\\ud83d\\ude40", "\\ud83d\\ude3f", "\\ud83d\\ude3e", "\\ud83d\\ude48", "\\ud83d\\ude49", "\\ud83d\\ude4a", "\\ud83d\\udc8b", "\\ud83d\\udc8c", "\\ud83d\\udc98", "\\ud83d\\udc9d", "\\ud83d\\udc96", "\\ud83d\\udc97", "\\ud83d\\udc93", "\\ud83d\\udc9e", "\\ud83d\\udc95", "\\ud83d\\udc9f", "\\u2763\\ufe0f", "\\ud83d\\udc94", "\\u2764\\ufe0f", "\\ud83e\\udde1", "\\ud83d\\udc9b", "\\ud83d\\udc9a", "\\ud83d\\udc99", "\\ud83d\\udc9c", "\\ud83e\\udd0e", "\\ud83d\\udda4", "\\ud83e\\udd0d", "\\ud83d\\udcaf", "\\ud83d\\udca2", "\\ud83d\\udca5", "\\ud83d\\udcab", "\\ud83d\\udca6", "\\ud83d\\udca8", "\\ud83d\\udca3", "\\ud83d\\udcac", "\\ud83d\\udcad", "\\ud83d\\udca4", "\\ud83d\\udc4b", "\\ud83e\\udd1a", "\\ud83d\\udd96", "\\ud83e\\udef1", "\\ud83e\\udef2", "\\ud83e\\udef3", "\\ud83e\\udef4", "\\ud83d\\udc4c", "\\ud83e\\udd0c", "\\ud83e\\udd0f", "\\u270c\\ufe0f", "\\ud83e\\udd1e", "\\ud83e\\udef0", "\\ud83e\\udd1f", "\\ud83e\\udd18", "\\ud83e\\udd19", "\\ud83d\\udc48", "\\ud83d\\udc49", "\\ud83d\\udc46", "\\ud83d\\udd95", "\\ud83d\\udc47", "\\u261d\\ufe0f", "\\ud83e\\udef5", "\\ud83d\\udc4d", "\\ud83d\\udc4e", "\\ud83d\\udc4a", "\\ud83e\\udd1b", "\\ud83e\\udd1c", "\\ud83d\\udc4f", "\\ud83d\\ude4c", "\\ud83e\\udef6", "\\ud83d\\udc50", "\\ud83e\\udd32", "\\ud83e\\udd1d", "\\ud83d\\ude4f", "\\u270d\\ufe0f", "\\ud83d\\udc85", "\\ud83e\\udd33", "\\ud83d\\udcaa", "\\ud83e\\uddbe", "\\ud83e\\uddbf", "\\ud83e\\uddb5", "\\ud83e\\uddb6", "\\ud83d\\udc42", "\\ud83e\\uddbb", "\\ud83d\\udc43", "\\ud83e\\udde0", "\\ud83e\\udec0", "\\ud83e\\udec1", "\\ud83e\\uddb7", "\\ud83e\\uddb4", "\\ud83d\\udc40", "\\ud83d\\udc45", "\\ud83d\\udc44", "\\ud83e\\udee6", "\\ud83d\\udc76", "\\ud83e\\uddd2", "\\ud83d\\udc66", "\\ud83d\\udc67", "\\ud83e\\uddd1", "\\ud83d\\udc71", "\\ud83d\\udc68", "\\ud83e\\uddd4", "\\ud83d\\udc69", "\\ud83e\\uddd3", "\\ud83d\\udc74", "\\ud83d\\udc75", "\\ud83d\\ude4d", "\\ud83d\\ude4e", "\\ud83d\\ude45", "\\ud83d\\ude46", "\\ud83d\\udc81", "\\ud83d\\ude4b", "\\ud83e\\uddcf", "\\ud83d\\ude47", "\\ud83e\\udd26", "\\ud83e\\udd37", "\\ud83d\\udc6e", "\\ud83d\\udc82", "\\ud83e\\udd77", "\\ud83d\\udc77", "\\ud83e\\udec5", "\\ud83e\\udd34", "\\ud83d\\udc78", "\\ud83d\\udc73", "\\ud83d\\udc72", "\\ud83e\\uddd5", "\\ud83e\\udd35", "\\ud83d\\udc70", "\\ud83e\\udd30", "\\ud83e\\udec3", "\\ud83e\\udec4", "\\ud83e\\udd31", "\\ud83d\\udc7c", "\\ud83c\\udf85", "\\ud83e\\udd36", "\\ud83e\\uddb8", "\\ud83e\\uddb9", "\\ud83e\\uddd9", "\\ud83e\\uddda", "\\ud83e\\udddb", "\\ud83e\\udddc", "\\ud83e\\udddd", "\\ud83e\\uddde", "\\ud83e\\udddf", "\\ud83e\\uddcc", "\\ud83d\\udc86", "\\ud83d\\udc87", "\\ud83d\\udeb6", "\\ud83e\\uddcd", "\\ud83e\\uddce", "\\ud83c\\udfc3", "\\ud83d\\udc83", "\\ud83d\\udd7a", "\\ud83d\\udc6f", "\\ud83e\\uddd6", "\\ud83e\\uddd7", "\\ud83e\\udd3a", "\\ud83c\\udfc7", "\\u26f7\\ufe0f", "\\ud83c\\udfc2", "\\ud83c\\udfc4", "\\ud83d\\udea3", "\\ud83c\\udfca", "\\u26f9\\ufe0f", "\\ud83d\\udeb4", "\\ud83d\\udeb5", "\\ud83e\\udd38", "\\ud83e\\udd3c", "\\ud83e\\udd3d", "\\ud83e\\udd3e", "\\ud83e\\udd39", "\\ud83e\\uddd8", "\\ud83d\\udec0", "\\ud83d\\udecc", "\\ud83d\\udc6d", "\\ud83d\\udc6b", "\\ud83d\\udc6c", "\\ud83d\\udc8f", "\\ud83d\\udc91", "\\ud83d\\udc6a", "\\ud83d\\udc64", "\\ud83d\\udc65", "\\ud83e\\udec2", "\\ud83d\\udc63", "\\ud83d\\udc35", "\\ud83d\\udc12", "\\ud83e\\udd8d", "\\ud83e\\udda7", "\\ud83d\\udc36", "\\ud83d\\udc15", "\\ud83e\\uddae", "\\ud83d\\udc29", "\\ud83d\\udc3a", "\\ud83e\\udd8a", "\\ud83e\\udd9d", "\\ud83d\\udc31", "\\ud83d\\udc08", "\\ud83e\\udd81", "\\ud83d\\udc2f", "\\ud83d\\udc05", "\\ud83d\\udc06", "\\ud83d\\udc34", "\\ud83d\\udc0e", "\\ud83e\\udd84", "\\ud83e\\udd93", "\\ud83e\\udd8c", "\\ud83e\\uddac", "\\ud83d\\udc2e", "\\ud83d\\udc02", "\\ud83d\\udc03", "\\ud83d\\udc04", "\\ud83d\\udc37", "\\ud83d\\udc16", "\\ud83d\\udc17", "\\ud83d\\udc3d", "\\ud83d\\udc0f", "\\ud83d\\udc11", "\\ud83d\\udc10", "\\ud83d\\udc2a", "\\ud83d\\udc2b", "\\ud83e\\udd99", "\\ud83e\\udd92", "\\ud83d\\udc18", "\\ud83e\\udda3", "\\ud83e\\udd8f", "\\ud83e\\udd9b", "\\ud83d\\udc2d", "\\ud83d\\udc01", "\\ud83d\\udc00", "\\ud83d\\udc39", "\\ud83d\\udc30", "\\ud83d\\udc07", "\\ud83e\\uddab", "\\ud83e\\udd94", "\\ud83e\\udd87", "\\ud83d\\udc3b", "\\ud83d\\udc28", "\\ud83d\\udc3c", "\\ud83e\\udda5", "\\ud83e\\udda6", "\\ud83e\\udda8", "\\ud83e\\udd98", "\\ud83e\\udda1", "\\ud83d\\udc3e", "\\ud83e\\udd83", "\\ud83d\\udc14", "\\ud83d\\udc13", "\\ud83d\\udc23", "\\ud83d\\udc24", "\\ud83d\\udc25", "\\ud83d\\udc26", "\\ud83d\\udc27", "\\ud83e\\udd85", "\\ud83e\\udd86", "\\ud83e\\udda2", "\\ud83e\\udd89", "\\ud83e\\udda4", "\\ud83e\\udeb6", "\\ud83e\\udda9", "\\ud83e\\udd9a", "\\ud83e\\udd9c", "\\ud83d\\udc38", "\\ud83d\\udc0a", "\\ud83d\\udc22", "\\ud83e\\udd8e", "\\ud83d\\udc0d", "\\ud83d\\udc32", "\\ud83d\\udc09", "\\ud83e\\udd95", "\\ud83e\\udd96", "\\ud83d\\udc33", "\\ud83d\\udc0b", "\\ud83d\\udc2c", "\\ud83e\\uddad", "\\ud83d\\udc1f", "\\ud83d\\udc20", "\\ud83d\\udc21", "\\ud83e\\udd88", "\\ud83d\\udc19", "\\ud83d\\udc1a", "\\ud83e\\udeb8", "\\ud83d\\udc0c", "\\ud83e\\udd8b", "\\ud83d\\udc1b", "\\ud83d\\udc1c", "\\ud83d\\udc1d", "\\ud83e\\udeb2", "\\ud83d\\udc1e", "\\ud83e\\udd97", "\\ud83e\\udeb3", "\\ud83e\\udd82", "\\ud83e\\udd9f", "\\ud83e\\udeb0", "\\ud83e\\udeb1", "\\ud83e\\udda0", "\\ud83d\\udc90", "\\ud83c\\udf38", "\\ud83d\\udcae", "\\ud83e\\udeb7", "\\ud83c\\udf39", "\\ud83e\\udd40", "\\ud83c\\udf3a", "\\ud83c\\udf3b", "\\ud83c\\udf3c", "\\ud83c\\udf37", "\\ud83c\\udf31", "\\ud83e\\udeb4", "\\ud83c\\udf32", "\\ud83c\\udf33", "\\ud83c\\udf34", "\\ud83c\\udf35", "\\ud83c\\udf3e", "\\ud83c\\udf3f", "\\u2618\\ufe0f", "\\ud83c\\udf40", "\\ud83c\\udf41", "\\ud83c\\udf42", "\\ud83c\\udf43", "\\ud83e\\udeb9", "\\ud83e\\udeba", "\\ud83c\\udf47", "\\ud83c\\udf48", "\\ud83c\\udf49", "\\ud83c\\udf4a", "\\ud83c\\udf4b", "\\ud83c\\udf4c", "\\ud83c\\udf4d", "\\ud83e\\udd6d", "\\ud83c\\udf4e", "\\ud83c\\udf4f", "\\ud83c\\udf50", "\\ud83c\\udf51", "\\ud83c\\udf52", "\\ud83c\\udf53", "\\ud83e\\uded0", "\\ud83e\\udd5d", "\\ud83c\\udf45", "\\ud83e\\uded2", "\\ud83e\\udd65", "\\ud83e\\udd51", "\\ud83c\\udf46", "\\ud83e\\udd54", "\\ud83e\\udd55", "\\ud83c\\udf3d", "\\ud83e\\uded1", "\\ud83e\\udd52", "\\ud83e\\udd6c", "\\ud83e\\udd66", "\\ud83e\\uddc4", "\\ud83e\\uddc5", "\\ud83c\\udf44", "\\ud83e\\udd5c", "\\ud83e\\uded8", "\\ud83c\\udf30", "\\ud83c\\udf5e", "\\ud83e\\udd50", "\\ud83e\\udd56", "\\ud83e\\uded3", "\\ud83e\\udd68", "\\ud83e\\udd6f", "\\ud83e\\udd5e", "\\ud83e\\uddc7", "\\ud83e\\uddc0", "\\ud83c\\udf56", "\\ud83c\\udf57", "\\ud83e\\udd69", "\\ud83e\\udd53", "\\ud83c\\udf54", "\\ud83c\\udf5f", "\\ud83c\\udf55", "\\ud83c\\udf2d", "\\ud83e\\udd6a", "\\ud83c\\udf2e", "\\ud83c\\udf2f", "\\ud83e\\uded4", "\\ud83e\\udd59", "\\ud83e\\uddc6", "\\ud83e\\udd5a", "\\ud83c\\udf73", "\\ud83e\\udd58", "\\ud83c\\udf72", "\\ud83e\\uded5", "\\ud83e\\udd63", "\\ud83e\\udd57", "\\ud83c\\udf7f", "\\ud83e\\uddc8", "\\ud83e\\uddc2", "\\ud83e\\udd6b", "\\ud83c\\udf71", "\\ud83c\\udf58", "\\ud83c\\udf59", "\\ud83c\\udf5a", "\\ud83c\\udf5b", "\\ud83c\\udf5c", "\\ud83c\\udf5d", "\\ud83c\\udf60", "\\ud83c\\udf62", "\\ud83c\\udf63", "\\ud83c\\udf64", "\\ud83c\\udf65", "\\ud83e\\udd6e", "\\ud83c\\udf61", "\\ud83e\\udd5f", "\\ud83e\\udd60", "\\ud83e\\udd61", "\\ud83e\\udd80", "\\ud83e\\udd9e", "\\ud83e\\udd90", "\\ud83e\\udd91", "\\ud83e\\uddaa", "\\ud83c\\udf66", "\\ud83c\\udf67", "\\ud83c\\udf68", "\\ud83c\\udf69", "\\ud83c\\udf6a", "\\ud83c\\udf82", "\\ud83c\\udf70", "\\ud83e\\uddc1", "\\ud83e\\udd67", "\\ud83c\\udf6b", "\\ud83c\\udf6c", "\\ud83c\\udf6d", "\\ud83c\\udf6e", "\\ud83c\\udf6f", "\\ud83c\\udf7c", "\\ud83e\\udd5b", "\\ud83e\\uded6", "\\ud83c\\udf75", "\\ud83c\\udf76", "\\ud83c\\udf7e", "\\ud83c\\udf77", "\\ud83c\\udf78", "\\ud83c\\udf79", "\\ud83c\\udf7a", "\\ud83c\\udf7b", "\\ud83e\\udd42", "\\ud83e\\udd43", "\\ud83e\\uded7", "\\ud83e\\udd64", "\\ud83e\\uddcb", "\\ud83e\\uddc3", "\\ud83e\\uddc9", "\\ud83e\\uddca", "\\ud83e\\udd62", "\\ud83c\\udf74", "\\ud83e\\udd44", "\\ud83d\\udd2a", "\\ud83e\\uded9", "\\ud83c\\udffa", "\\ud83c\\udf0d", "\\ud83c\\udf0e", "\\ud83c\\udf0f", "\\ud83c\\udf10", "\\ud83d\\uddfe", "\\ud83e\\udded", "\\u26f0\\ufe0f", "\\ud83c\\udf0b", "\\ud83d\\uddfb", "\\ud83e\\uddf1", "\\ud83e\\udea8", "\\ud83e\\udeb5", "\\ud83d\\uded6", "\\ud83c\\udfe0", "\\ud83c\\udfe1", "\\ud83c\\udfe2", "\\ud83c\\udfe3", "\\ud83c\\udfe4", "\\ud83c\\udfe5", "\\ud83c\\udfe6", "\\ud83c\\udfe8", "\\ud83c\\udfe9", "\\ud83c\\udfea", "\\ud83c\\udfeb", "\\ud83c\\udfec", "\\ud83c\\udfed", "\\ud83c\\udfef", "\\ud83c\\udff0", "\\ud83d\\udc92", "\\ud83d\\uddfc", "\\ud83d\\uddfd", "\\ud83d\\udd4c", "\\ud83d\\uded5", "\\ud83d\\udd4d", "\\u26e9\\ufe0f", "\\ud83d\\udd4b", "\\ud83c\\udf01", "\\ud83c\\udf03", "\\ud83c\\udf04", "\\ud83c\\udf05", "\\ud83c\\udf06", "\\ud83c\\udf07", "\\ud83c\\udf09", "\\u2668\\ufe0f", "\\ud83c\\udfa0", "\\ud83d\\udedd", "\\ud83c\\udfa1", "\\ud83c\\udfa2", "\\ud83d\\udc88", "\\ud83c\\udfaa", "\\ud83d\\ude82", "\\ud83d\\ude83", "\\ud83d\\ude84", "\\ud83d\\ude85", "\\ud83d\\ude86", "\\ud83d\\ude87", "\\ud83d\\ude88", "\\ud83d\\ude89", "\\ud83d\\ude8a", "\\ud83d\\ude9d", "\\ud83d\\ude9e", "\\ud83d\\ude8b", "\\ud83d\\ude8c", "\\ud83d\\ude8d", "\\ud83d\\ude8e", "\\ud83d\\ude90", "\\ud83d\\ude91", "\\ud83d\\ude92", "\\ud83d\\ude93", "\\ud83d\\ude94", "\\ud83d\\ude95", "\\ud83d\\ude96", "\\ud83d\\ude97", "\\ud83d\\ude98", "\\ud83d\\ude99", "\\ud83d\\udefb", "\\ud83d\\ude9a", "\\ud83d\\ude9b", "\\ud83d\\ude9c", "\\ud83d\\udef5", "\\ud83e\\uddbd", "\\ud83e\\uddbc", "\\ud83d\\udefa", "\\ud83d\\udeb2", "\\ud83d\\udef4", "\\ud83d\\udef9", "\\ud83d\\udefc", "\\ud83d\\ude8f", "\\ud83d\\udede", "\\ud83d\\udea8", "\\ud83d\\udea5", "\\ud83d\\udea6", "\\ud83d\\uded1", "\\ud83d\\udea7", "\\ud83d\\udedf", "\\ud83d\\udef6", "\\ud83d\\udea4", "\\u26f4\\ufe0f", "\\ud83d\\udea2", "\\u2708\\ufe0f", "\\ud83d\\udeeb", "\\ud83d\\udeec", "\\ud83e\\ude82", "\\ud83d\\udcba", "\\ud83d\\ude81", "\\ud83d\\ude9f", "\\ud83d\\udea0", "\\ud83d\\udea1", "\\ud83d\\ude80", "\\ud83d\\udef8", "\\ud83e\\uddf3", "\\u23f1\\ufe0f", "\\u23f2\\ufe0f", "\\ud83d\\udd5b", "\\ud83d\\udd67", "\\ud83d\\udd50", "\\ud83d\\udd5c", "\\ud83d\\udd51", "\\ud83d\\udd5d", "\\ud83d\\udd52", "\\ud83d\\udd5e", "\\ud83d\\udd53", "\\ud83d\\udd5f", "\\ud83d\\udd54", "\\ud83d\\udd60", "\\ud83d\\udd55", "\\ud83d\\udd61", "\\ud83d\\udd56", "\\ud83d\\udd62", "\\ud83d\\udd57", "\\ud83d\\udd63", "\\ud83d\\udd58", "\\ud83d\\udd64", "\\ud83d\\udd59", "\\ud83d\\udd65", "\\ud83d\\udd5a", "\\ud83d\\udd66", "\\ud83c\\udf11", "\\ud83c\\udf12", "\\ud83c\\udf13", "\\ud83c\\udf14", "\\ud83c\\udf15", "\\ud83c\\udf16", "\\ud83c\\udf17", "\\ud83c\\udf18", "\\ud83c\\udf19", "\\ud83c\\udf1a", "\\ud83c\\udf1b", "\\ud83c\\udf1c", "\\u2600\\ufe0f", "\\ud83c\\udf1d", "\\ud83c\\udf1e", "\\ud83e\\ude90", "\\ud83c\\udf1f", "\\ud83c\\udf20", "\\ud83c\\udf0c", "\\u2601\\ufe0f", "\\u26c8\\ufe0f", "\\ud83c\\udf00", "\\ud83c\\udf08", "\\ud83c\\udf02", "\\u2602\\ufe0f", "\\u26f1\\ufe0f", "\\u2744\\ufe0f", "\\u2603\\ufe0f", "\\u2604\\ufe0f", "\\ud83d\\udd25", "\\ud83d\\udca7", "\\ud83c\\udf0a", "\\ud83c\\udf83", "\\ud83c\\udf84", "\\ud83c\\udf86", "\\ud83c\\udf87", "\\ud83e\\udde8", "\\ud83c\\udf88", "\\ud83c\\udf89", "\\ud83c\\udf8a", "\\ud83c\\udf8b", "\\ud83c\\udf8d", "\\ud83c\\udf8e", "\\ud83c\\udf8f", "\\ud83c\\udf90", "\\ud83c\\udf91", "\\ud83e\\udde7", "\\ud83c\\udf80", "\\ud83c\\udf81", "\\ud83c\\udfab", "\\ud83c\\udfc6", "\\ud83c\\udfc5", "\\ud83e\\udd47", "\\ud83e\\udd48", "\\ud83e\\udd49", "\\ud83e\\udd4e", "\\ud83c\\udfc0", "\\ud83c\\udfd0", "\\ud83c\\udfc8", "\\ud83c\\udfc9", "\\ud83c\\udfbe", "\\ud83e\\udd4f", "\\ud83c\\udfb3", "\\ud83c\\udfcf", "\\ud83c\\udfd1", "\\ud83c\\udfd2", "\\ud83e\\udd4d", "\\ud83c\\udfd3", "\\ud83c\\udff8", "\\ud83e\\udd4a", "\\ud83e\\udd4b", "\\ud83e\\udd45", "\\u26f8\\ufe0f", "\\ud83c\\udfa3", "\\ud83e\\udd3f", "\\ud83c\\udfbd", "\\ud83c\\udfbf", "\\ud83d\\udef7", "\\ud83e\\udd4c", "\\ud83c\\udfaf", "\\ud83e\\ude81", "\\ud83c\\udfb1", "\\ud83d\\udd2e", "\\ud83e\\ude84", "\\ud83e\\uddff", "\\ud83e\\udeac", "\\ud83c\\udfae", "\\ud83c\\udfb0", "\\ud83c\\udfb2", "\\ud83e\\udde9", "\\ud83e\\uddf8", "\\ud83e\\ude85", "\\ud83e\\udea9", "\\ud83e\\ude86", "\\u2660\\ufe0f", "\\u2665\\ufe0f", "\\u2666\\ufe0f", "\\u2663\\ufe0f", "\\u265f\\ufe0f", "\\ud83c\\udccf", "\\ud83c\\udc04", "\\ud83c\\udfb4", "\\ud83c\\udfad", "\\ud83c\\udfa8", "\\ud83e\\uddf5", "\\ud83e\\udea1", "\\ud83e\\uddf6", "\\ud83e\\udea2", "\\ud83d\\udc53", "\\ud83e\\udd7d", "\\ud83e\\udd7c", "\\ud83e\\uddba", "\\ud83d\\udc54", "\\ud83d\\udc55", "\\ud83d\\udc56", "\\ud83e\\udde3", "\\ud83e\\udde4", "\\ud83e\\udde5", "\\ud83e\\udde6", "\\ud83d\\udc57", "\\ud83d\\udc58", "\\ud83e\\udd7b", "\\ud83e\\ude71", "\\ud83e\\ude72", "\\ud83e\\ude73", "\\ud83d\\udc59", "\\ud83d\\udc5a", "\\ud83d\\udc5b", "\\ud83d\\udc5c", "\\ud83d\\udc5d", "\\ud83c\\udf92", "\\ud83e\\ude74", "\\ud83d\\udc5e", "\\ud83d\\udc5f", "\\ud83e\\udd7e", "\\ud83e\\udd7f", "\\ud83d\\udc60", "\\ud83d\\udc61", "\\ud83e\\ude70", "\\ud83d\\udc62", "\\ud83d\\udc51", "\\ud83d\\udc52", "\\ud83c\\udfa9", "\\ud83c\\udf93", "\\ud83e\\udde2", "\\ud83e\\ude96", "\\u26d1\\ufe0f", "\\ud83d\\udcff", "\\ud83d\\udc84", "\\ud83d\\udc8d", "\\ud83d\\udc8e", "\\ud83d\\udd07", "\\ud83d\\udd08", "\\ud83d\\udd09", "\\ud83d\\udd0a", "\\ud83d\\udce2", "\\ud83d\\udce3", "\\ud83d\\udcef", "\\ud83d\\udd14", "\\ud83d\\udd15", "\\ud83c\\udfbc", "\\ud83c\\udfb5", "\\ud83c\\udfb6", "\\ud83c\\udfa4", "\\ud83c\\udfa7", "\\ud83d\\udcfb", "\\ud83c\\udfb7", "\\ud83e\\ude97", "\\ud83c\\udfb8", "\\ud83c\\udfb9", "\\ud83c\\udfba", "\\ud83c\\udfbb", "\\ud83e\\ude95", "\\ud83e\\udd41", "\\ud83e\\ude98", "\\ud83d\\udcf1", "\\ud83d\\udcf2", "\\u260e\\ufe0f", "\\ud83d\\udcde", "\\ud83d\\udcdf", "\\ud83d\\udce0", "\\ud83d\\udd0b", "\\ud83e\\udeab", "\\ud83d\\udd0c", "\\ud83d\\udcbb", "\\u2328\\ufe0f", "\\ud83d\\udcbd", "\\ud83d\\udcbe", "\\ud83d\\udcbf", "\\ud83d\\udcc0", "\\ud83e\\uddee", "\\ud83c\\udfa5", "\\ud83c\\udfac", "\\ud83d\\udcfa", "\\ud83d\\udcf7", "\\ud83d\\udcf8", "\\ud83d\\udcf9", "\\ud83d\\udcfc", "\\ud83d\\udd0d", "\\ud83d\\udd0e", "\\ud83d\\udca1", "\\ud83d\\udd26", "\\ud83c\\udfee", "\\ud83e\\ude94", "\\ud83d\\udcd4", "\\ud83d\\udcd5", "\\ud83d\\udcd6", "\\ud83d\\udcd7", "\\ud83d\\udcd8", "\\ud83d\\udcd9", "\\ud83d\\udcda", "\\ud83d\\udcd3", "\\ud83d\\udcd2", "\\ud83d\\udcc3", "\\ud83d\\udcdc", "\\ud83d\\udcc4", "\\ud83d\\udcf0", "\\ud83d\\udcd1", "\\ud83d\\udd16", "\\ud83d\\udcb0", "\\ud83e\\ude99", "\\ud83d\\udcb4", "\\ud83d\\udcb5", "\\ud83d\\udcb6", "\\ud83d\\udcb7", "\\ud83d\\udcb8", "\\ud83d\\udcb3", "\\ud83e\\uddfe", "\\ud83d\\udcb9", "\\u2709\\ufe0f", "\\ud83d\\udce7", "\\ud83d\\udce8", "\\ud83d\\udce9", "\\ud83d\\udce4", "\\ud83d\\udce5", "\\ud83d\\udce6", "\\ud83d\\udceb", "\\ud83d\\udcea", "\\ud83d\\udcec", "\\ud83d\\udced", "\\ud83d\\udcee", "\\u270f\\ufe0f", "\\u2712\\ufe0f", "\\ud83d\\udcdd", "\\ud83d\\udcbc", "\\ud83d\\udcc1", "\\ud83d\\udcc2", "\\ud83d\\udcc5", "\\ud83d\\udcc6", "\\ud83d\\udcc7", "\\ud83d\\udcc8", "\\ud83d\\udcc9", "\\ud83d\\udcca", "\\ud83d\\udccb", "\\ud83d\\udccc", "\\ud83d\\udccd", "\\ud83d\\udcce", "\\ud83d\\udccf", "\\ud83d\\udcd0", "\\u2702\\ufe0f", "\\ud83d\\udd12", "\\ud83d\\udd13", "\\ud83d\\udd0f", "\\ud83d\\udd10", "\\ud83d\\udd11", "\\ud83d\\udd28", "\\ud83e\\ude93", "\\u26cf\\ufe0f", "\\u2692\\ufe0f", "\\u2694\\ufe0f", "\\ud83d\\udd2b", "\\ud83e\\ude83", "\\ud83c\\udff9", "\\ud83e\\ude9a", "\\ud83d\\udd27", "\\ud83e\\ude9b", "\\ud83d\\udd29", "\\u2699\\ufe0f", "\\u2696\\ufe0f", "\\ud83e\\uddaf", "\\ud83d\\udd17", "\\u26d3\\ufe0f", "\\ud83e\\ude9d", "\\ud83e\\uddf0", "\\ud83e\\uddf2", "\\ud83e\\ude9c", "\\u2697\\ufe0f", "\\ud83e\\uddea", "\\ud83e\\uddeb", "\\ud83e\\uddec", "\\ud83d\\udd2c", "\\ud83d\\udd2d", "\\ud83d\\udce1", "\\ud83d\\udc89", "\\ud83e\\ude78", "\\ud83d\\udc8a", "\\ud83e\\ude79", "\\ud83e\\ude7c", "\\ud83e\\ude7a", "\\ud83e\\ude7b", "\\ud83d\\udeaa", "\\ud83d\\uded7", "\\ud83e\\ude9e", "\\ud83e\\ude9f", "\\ud83e\\ude91", "\\ud83d\\udebd", "\\ud83e\\udea0", "\\ud83d\\udebf", "\\ud83d\\udec1", "\\ud83e\\udea4", "\\ud83e\\ude92", "\\ud83e\\uddf4", "\\ud83e\\uddf7", "\\ud83e\\uddf9", "\\ud83e\\uddfa", "\\ud83e\\uddfb", "\\ud83e\\udea3", "\\ud83e\\uddfc", "\\ud83e\\udee7", "\\ud83e\\udea5", "\\ud83e\\uddfd", "\\ud83e\\uddef", "\\ud83d\\uded2", "\\ud83d\\udeac", "\\u26b0\\ufe0f", "\\ud83e\\udea6", "\\u26b1\\ufe0f", "\\ud83d\\uddff", "\\ud83e\\udea7", "\\ud83e\\udeaa", "\\ud83c\\udfe7", "\\ud83d\\udeae", "\\ud83d\\udeb0", "\\ud83d\\udeb9", "\\ud83d\\udeba", "\\ud83d\\udebb", "\\ud83d\\udebc", "\\ud83d\\udebe", "\\ud83d\\udec2", "\\ud83d\\udec3", "\\ud83d\\udec4", "\\ud83d\\udec5", "\\u26a0\\ufe0f", "\\ud83d\\udeb8", "\\ud83d\\udeab", "\\ud83d\\udeb3", "\\ud83d\\udead", "\\ud83d\\udeaf", "\\ud83d\\udeb1", "\\ud83d\\udeb7", "\\ud83d\\udcf5", "\\ud83d\\udd1e", "\\u2622\\ufe0f", "\\u2623\\ufe0f", "\\u2b06\\ufe0f", "\\u2197\\ufe0f", "\\u27a1\\ufe0f", "\\u2198\\ufe0f", "\\u2b07\\ufe0f", "\\u2199\\ufe0f", "\\u2b05\\ufe0f", "\\u2196\\ufe0f", "\\u2195\\ufe0f", "\\u2194\\ufe0f", "\\u21a9\\ufe0f", "\\u21aa\\ufe0f", "\\u2934\\ufe0f", "\\u2935\\ufe0f", "\\ud83d\\udd03", "\\ud83d\\udd04", "\\ud83d\\udd19", "\\ud83d\\udd1a", "\\ud83d\\udd1b", "\\ud83d\\udd1c", "\\ud83d\\udd1d", "\\ud83d\\uded0", "\\u269b\\ufe0f", "\\u2721\\ufe0f", "\\u2638\\ufe0f", "\\u262f\\ufe0f", "\\u271d\\ufe0f", "\\u2626\\ufe0f", "\\u262a\\ufe0f", "\\u262e\\ufe0f", "\\ud83d\\udd4e", "\\ud83d\\udd2f", "\\ud83d\\udd00", "\\ud83d\\udd01", "\\ud83d\\udd02", "\\u25b6\\ufe0f", "\\u23ed\\ufe0f", "\\u23ef\\ufe0f", "\\u25c0\\ufe0f", "\\u23ee\\ufe0f", "\\ud83d\\udd3c", "\\ud83d\\udd3d", "\\u23f8\\ufe0f", "\\u23f9\\ufe0f", "\\u23fa\\ufe0f", "\\u23cf\\ufe0f", "\\ud83c\\udfa6", "\\ud83d\\udd05", "\\ud83d\\udd06", "\\ud83d\\udcf6", "\\ud83d\\udcf3", "\\ud83d\\udcf4", "\\u2640\\ufe0f", "\\u2642\\ufe0f", "\\u26a7\\ufe0f", "\\u2716\\ufe0f", "\\ud83d\\udff0", "\\u267e\\ufe0f", "\\u203c\\ufe0f", "\\u2049\\ufe0f", "\\u3030\\ufe0f", "\\ud83d\\udcb1", "\\ud83d\\udcb2", "\\u2695\\ufe0f", "\\u267b\\ufe0f", "\\u269c\\ufe0f", "\\ud83d\\udd31", "\\ud83d\\udcdb", "\\ud83d\\udd30", "\\u2611\\ufe0f", "\\u2714\\ufe0f", "\\u303d\\ufe0f", "\\u2733\\ufe0f", "\\u2734\\ufe0f", "\\u2747\\ufe0f", "\\u2122\\ufe0f", "\\ud83d\\udd1f", "\\ud83d\\udd20", "\\ud83d\\udd21", "\\ud83d\\udd22", "\\ud83d\\udd23", "\\ud83d\\udd24", "\\ud83c\\udd8e", "\\ud83c\\udd91", "\\ud83c\\udd92", "\\ud83c\\udd93", "\\u2139\\ufe0f", "\\ud83c\\udd94", "\\u24c2\\ufe0f", "\\ud83c\\udd95", "\\ud83c\\udd96", "\\ud83c\\udd97", "\\ud83c\\udd98", "\\ud83c\\udd99", "\\ud83c\\udd9a", "\\ud83c\\ude01", "\\ud83c\\ude36", "\\ud83c\\ude2f", "\\ud83c\\ude50", "\\ud83c\\ude39", "\\ud83c\\ude1a", "\\ud83c\\ude32", "\\ud83c\\ude51", "\\ud83c\\ude38", "\\ud83c\\ude34", "\\ud83c\\ude33", "\\u3297\\ufe0f", "\\u3299\\ufe0f", "\\ud83c\\ude3a", "\\ud83c\\ude35", "\\ud83d\\udd34", "\\ud83d\\udfe0", "\\ud83d\\udfe1", "\\ud83d\\udfe2", "\\ud83d\\udd35", "\\ud83d\\udfe3", "\\ud83d\\udfe4", "\\ud83d\\udfe5", "\\ud83d\\udfe7", "\\ud83d\\udfe8", "\\ud83d\\udfe9", "\\ud83d\\udfe6", "\\ud83d\\udfea", "\\ud83d\\udfeb", "\\u25fc\\ufe0f", "\\u25fb\\ufe0f", "\\u25aa\\ufe0f", "\\u25ab\\ufe0f", "\\ud83d\\udd36", "\\ud83d\\udd37", "\\ud83d\\udd38", "\\ud83d\\udd39", "\\ud83d\\udd3a", "\\ud83d\\udd3b", "\\ud83d\\udca0", "\\ud83d\\udd18", "\\ud83d\\udd33", "\\ud83d\\udd32", "\\ud83c\\udfc1", "\\ud83d\\udea9", "\\ud83c\\udf8c", "\\ud83c\\udff4"};
        Random r = new Random();
        return emojis[r.nextInt(emojis.length)];

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
