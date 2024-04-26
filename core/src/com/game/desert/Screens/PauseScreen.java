package com.game.desert.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.desert.DesertGame;

public class PauseScreen implements Screen {
    //Burası menü ekranının aynısı
    //Normalde menü ekranına dönemezken bu sınıf sayesinde dönebiliyoruz.
    private DesertGame parent;
    private Stage stage;
    private Label label;
    private Texture background;
    public PauseScreen(DesertGame game){

        parent = game;
        stage = new Stage(new FitViewport(DesertGame.WIDTH,DesertGame.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        background = new Texture("ArkaPlan/ArkaPlan.png");


        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("ArkaPlan/ArkaPlan.png"))));


        // Metnin boyutunu, fontunu ve renklerini ayarlama
        BitmapFont font = new BitmapFont();
        font.getData().setScale(6);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        label = new Label("DESERT GAME", style);
        label.setPosition(850, 100, Align.center); // Metnin konumunu ayarlama
        stage.addActor(label);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        TextButton playGame = new TextButton("OYNA", skin);
        TextButton preferences = new TextButton("AYARLAR", skin);
        TextButton exit = new TextButton("CIKIS", skin);


        playGame.setSize(160, 90);
        preferences.setSize(160, 90);
        exit.setSize(160, 90);


        stage.addActor(playGame);
        stage.addActor(preferences);
        stage.addActor(exit);


        playGame.setPosition(100, 400);
        preferences.setPosition(100, 250);
        exit.setPosition(100, 100);


        playGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                parent.changeScreen(DesertGame.APPLICATION);
                return true;
            }
        });

        preferences.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                parent.changeScreen(DesertGame.PREFERENCES);
                return true;
            }
        });

        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


    }

    @Override
    public void show() {
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
