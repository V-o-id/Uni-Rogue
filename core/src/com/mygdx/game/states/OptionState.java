package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class OptionState extends State {
    //private final Text;
    private final TextField inputField;
   // Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    //Stage myStage = new Stage();
    //String userInput;
    private Stage stage;
    Label character;

    private TextButton textButton;

    OptionState(final GameStateManager gsm){
        super(gsm);
        ExtendViewport extendViewPort = new ExtendViewport(700, 1200, new OrthographicCamera());
        stage = new Stage(extendViewPort);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        //skin.get("font-label", BitmapFont.class).getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Table table = new Table();
        table.defaults().pad(10);
        table.setFillParent(true);

        character = new Label("Enter Character: ", skin);
        inputField = new TextField("", skin);
        textButton = new TextButton("Enter", skin);

        table.add(character);
        table.add(inputField).width(300);
        table.add(textButton);

        stage.addActor(table);

        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("clicked");
                gsm.set(new MenuState(gsm));
                //write character into textfile
                FileHandle file = Gdx.files.local("selectedCharacter.txt");
                file.writeString(inputField.getText(), false);
            }
        });

        Gdx.input.setInputProcessor(stage);

        /* inputField = new TextField("", );
        inputField.setPosition(0, 0);
        inputField.setMessageText("Enter character");
        myStage.addActor(inputField);
        System.out.println(inputField.getText());*/
    }

    /*Input.TextInputListener textListener = new Input.TextInputListener() {
        @Override
        public void input(String text) {
            System.out.println(text);
            // userInput = text;
        }

        @Override
        public void canceled() {
            System.out.println("Aborted");
        }
    };*/

    @Override
    public void render(SpriteBatch sb){
        sb.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        sb.end();
    }

    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void dispose() {
    }
}
