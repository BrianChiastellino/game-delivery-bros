package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.mygdx.game.Scenes.Hud;

public class PlayScreen implements Screen {

    private final MarioBros game;

    private final OrthographicCamera gamecam;
    private final Viewport gamePort;
    private final Hud hud;

    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private final World world;
    private final Box2DDebugRenderer b2dr;



    public PlayScreen(MarioBros game) {

        this.game = game;

        gamecam = new OrthographicCamera();

        gamePort = new FitViewport((float) MarioBros.V_WIDHT / MarioBros.PPM, (float) MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Map/MarioMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / MarioBros.PPM);

        gamecam.position.set((float) gamePort.getWorldWidth() / 2,
                (float) gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;





//        Codigo para el suelo
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM ,
                    (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2,rect.getHeight() / 2);

            fdef.shape = shape;

            body.createFixture(fdef);
        }

//        Codigo para la tuberia
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM ,
                    (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2,rect.getHeight() / 2);

            fdef.shape = shape;

            body.createFixture(fdef);
        }

//        Codigo para las coins
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM ,
                    (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2,rect.getHeight() / 2);

            fdef.shape = shape;

            body.createFixture(fdef);
        }


//        Codigo para los bricks
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM ,
                    (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM );

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2,rect.getHeight() / 2);

            fdef.shape = shape;

            body.createFixture(fdef);
        }

    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isTouched()){
            gamecam.position.x += 100*dt;
        }


    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f,6,2);

        gamecam.update();
        renderer.setView(gamecam);

        renderer.render();


    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world,gamecam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


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

        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();

    }



}
