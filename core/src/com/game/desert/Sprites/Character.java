package com.game.desert.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.game.desert.DesertGame;
import com.game.desert.Screens.PlayScreen;

//Karakterin oluşturulması
public class Character extends Sprite {
    //Aşağıdaki durumlar karakterin haraket durumlarını belirtir.
    public enum State{FALLING, JUMPING, STANDING, RUNNING}
    public State currentState;
    public State previousState;
    private Animation <TextureRegion> characterRun;
    private Animation <TextureRegion> characterJump;
    private boolean runningRight; //Sola koşarken sola dönmesini sağlıyor
    private float stateTimer;
    public World world;
    public Body b2body;
    private TextureRegion characterStand;

    public Character(World world, PlayScreen screen) {
        //Karakterin resmini tanımlar
        super(screen.getAtlas().findRegion("Girl"));
        this.world = world;

        currentState= State.STANDING;
        previousState= State.STANDING;
        stateTimer= 0;
        runningRight= true;

        // Animasyon görselleini içeren dizi
        Array<TextureRegion> frames = new Array<TextureRegion>();


        // Koşma animasyonu için for döngüsü
        for(int i= 0 ; i<6 ; i++)
            frames.add(new TextureRegion(getTexture(),i*48 ,18,30,48));

        characterRun= new Animation<TextureRegion>(0.1f,frames);
        frames.clear();


        // Zıplama animasyonu için for döngüsü
        for(int i= 3 ; i<4 ; i++ )
            frames.add(new TextureRegion(getTexture(),143 ,18,30,48));

        characterJump= new Animation<TextureRegion>(0.1f,frames);

        defineCharacter();

        // Karakterin hareketsiz halinin görüntüsünün kaçıncı pixellerden alınacağı belirleniyor
        characterStand= new TextureRegion(getTexture(), 290, 18, 30, 48);
        // Karakterin boyutu
        setBounds(0, 0, 100/DesertGame.PPM , 150/DesertGame.PPM);
        setRegion(characterStand);
    }




    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }


    public TextureRegion getFrame(float dt){
        //Karakterin anlık durumunu belirler.
        currentState= getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = (TextureRegion) characterJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) characterRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region= characterStand;
                break;
        }

        // Karakterin sağa veya sola koşarken koştuğu yöne dönmesini sağlayan if yapısı
        if((b2body.getLinearVelocity().x < 0 || !runningRight ) && !region.isFlipX()){

            region.flip(true,false);
            runningRight= false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight ) && region.isFlipX()) {

            region.flip(true, false);
            runningRight = true;
        }

        stateTimer= currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        //Karakterin durumunu verir.
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState==State.JUMPING))   return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)   return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)   return State.RUNNING;
        else return State.STANDING;
    }
    public void defineCharacter() {
        //Karakterin vücudunun oluşturulması
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((1/ DesertGame.PPM)+9,300/DesertGame.PPM); //Karakterin doğma yeri
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        //Karakterin temel yapısının şekli(çember)
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25/DesertGame.PPM);
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);
    }

    public void checkpointDefineCharacter(){
        //Kayıt yeri
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((1/ DesertGame.PPM)+60,300/DesertGame.PPM); //Karakterin doğma yeri
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        //Karakterin temel yapısının şekli(çember)
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25/DesertGame.PPM);
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);
    }
}