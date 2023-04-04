package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.sprites.Text;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.text.html.Option;

public class OptionState extends State {
    //private final Text;
    //private final TextField inputField;
    String userInput;

    OptionState(GameStateManager gsm){
        super(gsm);
        //inputField = new TextField("Enter character: ", );
    }

    Input.TextInputListener textListener = new Input.TextInputListener() {
        @Override
        public void input(String text) {
            System.out.println(text);
            // userInput = text;
        }

        @Override
        public void canceled() {
            System.out.println("Aborted");
        }
    };

    @Override
    public void render(SpriteBatch sb){
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
