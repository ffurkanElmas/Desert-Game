package com.game.desert.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.desert.Sprites.Character;
import com.game.desert.DesertGame;

public class PlayScreen implements Screen {

    private TextureAtlas atlas; // Karakter animasyonu için eklenen nesne
    private DesertGame game;

    private Body b2body;
    //Camera için kullanılan sınıf
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    //Tiled uygulamasıyla haritayı koda aktarır
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //Oyun dünyası için değişkenler
    private World world;
    private Character player;
    private int jumpCounter = 0;
    //Ses efektleri
    private Music music;
    private Sound sound;
    private boolean checkPoint= false;

    Music music1 = Gdx.audio.newMusic(Gdx.files.internal("yurume_sesi_1_1.ogg"));
    Music music2 = Gdx.audio.newMusic(Gdx.files.internal("ziplama_sesi_2.ogg"));

    public PlayScreen(DesertGame game){

        atlas= new TextureAtlas("Karakter.pack");  //Karakter animasyonlarını içeren dosya

        this.game = game;

        //Kameranın oluşturulması
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DesertGame.WIDTH/DesertGame.PPM, DesertGame.HEIGHT/DesertGame.PPM, gameCam);

        //Haritanın tanımlanması
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("adsız.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/DesertGame.PPM);
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        //Oyun dünyasının oluşturulması
        world = new World(new Vector2(0,-10), true);
        player = new Character(world,this);



        //Vücudun oluşturulması
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //Oyundaki zeminin oluşturulması
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle =((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2)/DesertGame.PPM, (rectangle.getY() + rectangle.getHeight()/2)/DesertGame.PPM);
            body = world.createBody(bodyDef);
            polygonShape.setAsBox(rectangle.getWidth()/2/DesertGame.PPM, rectangle.getHeight()/2/DesertGame.PPM);
            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
        }
    }



    public TextureAtlas getAtlas(){

        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta) {

        //Karakter kontrolü ve ses efektleri için gerekli kodlar
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && jumpCounter < 1) {
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
                jumpCounter++;
                music2.play();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 3) {
                player.b2body.applyLinearImpulse(new Vector2(0.075f, 0), player.b2body.getWorldCenter(), true);
                music1.play();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -3) {
                player.b2body.applyLinearImpulse(new Vector2(-0.075f, 0), player.b2body.getWorldCenter(), true);
                music1.play();
            }

            if (player.b2body.getLinearVelocity().y == 0) {
                jumpCounter = 0;
            }

            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                game.changeScreen(DesertGame.PAUSE);
            }
    }


    private void update(float delta) {

        //Oyunun yenilenmesi için gerekli kodlar
        handleInput(delta);
        world.step(1/60f, 6, 2);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);
        player.update(delta);

        //Kooordinat ile ölüp ölmediğini anlama
        if(player.b2body.getPosition().y<0){
            if(player.b2body.getPosition().x >60){
                checkPoint= true;
            }
            if(checkPoint){
                player.checkpointDefineCharacter();
            }
            else player.defineCharacter();
        }

        //Oyunu bitirme
        if(player.b2body.getPosition().x>124){
            game.changeScreen(DesertGame.MENU);
            player.defineCharacter();
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        // Harita ve kamera güncellenir
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();


        // Karakter animasyonları için gerekli kodlar
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
    }



    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
