package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Application;

public class VolumeSlider extends Slider {

    private final Text volumeText;
    private final float x, y, width, height;
    private final Stage stage;

    public VolumeSlider(float x, float y, float width, float height, float min, float max, float stepSize, boolean vertical, Stage stage) {
        super(min, max, stepSize, vertical, new Skin(Gdx.files.internal("uiskin.json")));
        this.stage = stage;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        volumeText = new Text("Volume", x, y);
        createSlider();
    }

    private void createSlider() {
        setValue(Application.getMusicVolume());
        setColor(new Color(255f,255f,255f,1f));
        setPosition(x - width/2, y - 100);
        setSize(width, height);
        stage.addActor(this);
        Gdx.input.setInputProcessor(stage);
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Application.setMusicVolume(getValue());
            }
        });
    }

    @Override
    public boolean remove() {
        return stage.getActors().removeValue(this, true);
    }

    @Override
    public void draw(Batch sb, float parentAlpha) {
        volumeText.getFont().draw(sb, volumeText.getText(), volumeText.getPostiton().x, volumeText.getPostiton().y + volumeText.getGlyphLayout().height);
        super.draw(sb, parentAlpha);
    }

}
