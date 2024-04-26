package com.game.desert.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.desert.DesertGame;

public class PreferencesScreen implements Screen{

    private DesertGame parent;
    private MenuScreen menuScreen;
    private Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label musicOnOffLabel;
    private Music music;



    public PreferencesScreen(DesertGame game){
        //Ayarlar için bir taslak oluşturur.
        parent = game;
        stage = new Stage(new FitViewport(DesertGame.WIDTH,DesertGame.HEIGHT));
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("ArkaPlan/ArkaPlan.png"))));
        stage.addActor(table);


        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Müzik sesi ayarlama
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                if(parent.getPreferences().isMusicEnabled()){
                    music = DesertGame.manager.get("Music/arkaplan_1.ogg", Music.class);
                    music.setLooping(true);
                    music.setVolume(volumeMusicSlider.getValue());
                    music.play();
                }
                else{
                    music = DesertGame.manager.get("Music/arkaplan_1.ogg", Music.class);
                    music.setLooping(true);
                    music.setVolume(volumeMusicSlider.getValue());
                    music.stop();
                }

                return false;
            }
        });

        // Müzik açma kapama
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });


        // Geri dönme butonu
        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                parent.changeScreen(DesertGame.MENU);
                menuScreen=new MenuScreen(parent);
                return true;
            }
        });
        //Butonları ekleme
        titleLabel = new Label( "Ayarlar", skin );
        volumeMusicLabel = new Label( "Muzik seviyesi", skin );
        musicOnOffLabel = new Label( "Muzik", skin );

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);

        table.add(backButton).colspan(2);

    }

    @Override
    public void render(float delta) {
        //Ekranı temizler
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
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
