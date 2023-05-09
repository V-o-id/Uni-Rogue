package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;


public class Application extends ApplicationAdapter {

    //possible delete?
    public static final String TITLE = "Uni-Rogue";
    private GameStateManager gsm;
    private SpriteBatch batch;
    private Viewport viewport;
    private Music music;


    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        gsm.push(new MenuState(gsm));

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/music.mp3"));
        music.play();
        music.setLooping(true);

        Camera camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);

       /* if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }*/
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}