package com.game.desert.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.desert.DesertGame;

public class LoadingScreen implements Screen {
    private DesertGame parent;
    private BitmapFont font;
    private BitmapFont font1;
    private SpriteBatch batch;
    private float timer = 0; // sayaç değişkenini tanımlayın

    public LoadingScreen(DesertGame game){
        parent = game;
        batch = new SpriteBatch();
        font = new BitmapFont(); // BitmapFont nesnesini oluşturun
        font1= new BitmapFont();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timer += delta;
        if (timer >= 3) {
            parent.changeScreen(DesertGame.MENU);
        }
        batch.begin(); // grafik çizimine başlayın
        font.getData().setScale(15); // yazıyı iki katı büyütün
        font1.getData().setScale(4);
        float fontHeight = font.getCapHeight(); // yazının yüksekliğini hesaplayın
        font.draw(batch, "DESERT GAME", 300, Gdx.graphics.getHeight() / 2 + fontHeight / 2);
        font1.draw(batch,"MADE BY:\nMEHMET YUSUF SEZGI\nFURKAN ELMAS\nUTKU OZUAK",500,400);
        batch.end(); // grafik çizimine son verin
    }

    @Override
    public void resize(int width, int height) {

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
