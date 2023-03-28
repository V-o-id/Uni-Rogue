package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {

    final UniRogue game;
    private final FitViewport viewport;
    OrthographicCamera camera;

    public MainMenuScreen(final UniRogue game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        viewport = new FitViewport(1920, 1080, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "This is a message on screen", 100, 150);
        game.font.draw(game.batch, "There should be buttons and other stuff too", 100, 100);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
