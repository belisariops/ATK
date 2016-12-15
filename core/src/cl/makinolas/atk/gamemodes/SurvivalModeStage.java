package cl.makinolas.atk.gamemodes;


import cl.makinolas.atk.actors.Background;
import cl.makinolas.atk.actors.GameActor;
import cl.makinolas.atk.actors.friend.Gastly;
import cl.makinolas.atk.actors.fx.FxManager;
import cl.makinolas.atk.actors.platform.Platform;
import cl.makinolas.atk.actors.ui.BagVis;
import cl.makinolas.atk.actors.ui.IHero;
import cl.makinolas.atk.actors.ui.MobileGroup;
import cl.makinolas.atk.platformCreator.MinigamePlatformCreator;
import cl.makinolas.atk.platformCreator.SurvivalPlatformCreator;
import cl.makinolas.atk.screen.GameScreen;
import cl.makinolas.atk.stages.AbstractStage;
import cl.makinolas.atk.stages.CameraPosition;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SurvivalModeStage extends AbstractStage implements ContactListener{
    private World survivalWorld;
    private final float frameTime = 1 / 100f;
    private Array<GameActor> gameActors;
    private Platform initialPlatform;
    private Group ground, mons, ui, deco;
    private IHero hero;
    private float height;
    private SurvivalPlatformCreator plataformCreator;
    private CameraPosition cameraPosition;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    //Agregar requisitode calidad y restriccion
    public SurvivalModeStage(Viewport v, GameScreen actualScreen, Game game) {
        super(v);

        renderer = new Box2DDebugRenderer();
        myScreen = actualScreen;
        gameActors = new Array<GameActor>();
        survivalWorld = new World(new Vector2(0, -30), true);
        survivalWorld.setContactListener(this);
        initialPlatform =  new Platform(survivalWorld, "CU", -9, -7, 20, 1);
        addActor(new Background("Background/Night.png", getCamera()));
        ground = new Group();
        ground.addActor(initialPlatform);
        deco = new Group();
        addActor(deco);
        addActor(ground);
        mons = new Group();
        addActor(mons);
        ui = new Group();
        addActor(ui);

        MobileGroup group = new MobileGroup(false);
        Gdx.input.setInputProcessor(this);

        cameraObserver = new CameraPosition();
        plataformCreator = new SurvivalPlatformCreator(survivalWorld, this, -9, -7, ground);
        hero = new SurvivalHero(survivalWorld);
        hero.setWorld(survivalWorld);
        this.setPlayerPosition(hero.getBody().getPosition());
        addGameActor((GameActor)hero);
        ui.addActor(group);
        ui.addActor(BagVis.getInstance());
        FxManager.getInstance().setParent(ui);
        survivalWorld.setGravity(new Vector2(0,-100));
        addListener(new SurvivalInputController(hero,group));
        renderer = new Box2DDebugRenderer();
        height = 0;
        //cameraPosition = new CameraPosition(cameraObserver.getPositionX(),cameraObserver.getPositionY());
        cameraObserver.setPosition(getCamera().position.x,getCamera().position.y);

        camera = new OrthographicCamera(32, 24);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();



    }


    @Override
    public void changeCamera(float x, float y) {
        //camera.position.set((x + 7), 7, 0);
        //getCamera().position.set(0, height + 20, 0);
        camera.position.set(x, y, 0);
        getCamera().position.set(x * 20, y * 20, 0);
        getCamera().update();
        cameraObserver.setPosition(camera.position.x,camera.position.y/20);
        camera.update();
    }

    @Override
    public void beginContact(Contact contact) {
        GameActor actor1 = (GameActor) contact.getFixtureA().getBody().getUserData();
        GameActor actor2 = (GameActor) contact.getFixtureB().getBody().getUserData();
        actor1.interact(actor2, contact.getWorldManifold());
    }

    @Override
    public void endContact(Contact contact) {
        GameActor actor1 = (GameActor) contact.getFixtureA().getBody().getUserData();
        GameActor actor2 = (GameActor) contact.getFixtureB().getBody().getUserData();
        actor1.endInteraction(actor2, contact.getWorldManifold());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void addGameActor(GameActor actor) {
        mons.addActor(actor);
        gameActors.add(actor);
    }

    @Override
    public void draw() {
        super.draw();
        camera.update();
        renderer.render(survivalWorld, camera.combined);
    }



    @Override
    public void act(float delta){
        super.act(delta);

        this.setPlayerPosition(hero.getBody().getPosition());
        plataformCreator.update(cameraObserver,null);
        /*elapsed time es static en abstract stage, hay que agregarle el delta o no funciona la animacion*/
        AbstractStage.elapsedTime += delta;
        survivalWorld.step(frameTime, 6, 2);
        height += 1000*Math.abs(Math.sin(0.0001*height))*delta + (delta*delta);

        plataformCreator.setPlayerHeight(height);
        changeCamera(0,height-20);

        if (hero.isDead()) {
            myScreen.mainMenu();
        }

        for (GameActor actor : gameActors) {
            Body actorBody = actor.getBody();
            if (actorBody.getPosition().y < height -30) {
                gameActors.removeValue(actor,true);
                survivalWorld.destroyBody(actorBody);
                actor.remove();
            }
        }




    }




}
