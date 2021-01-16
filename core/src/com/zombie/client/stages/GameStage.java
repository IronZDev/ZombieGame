package com.zombie.client.stages;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.zombie.client.actors.*;
import com.zombie.client.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;

public class GameStage extends Stage implements ContactListener{

    private World world;
    private Defender defender = null;

    private final float TIME_STEP = 1 / 60f;
    private float accumulator = 0f;

    private OrthographicCamera camera;

    ArrayList<GameActor> activeGameActors = new ArrayList<GameActor>();
    private int bulletsToAdd = 0;
    private long lastSpawn = 0;
    private long lastShoot = 0;
    private int nextEnemySpawn = RandomUtils.RandInt(Constants.ENEMY_RESPAWN_TIME_MIN,Constants.ENEMY_RESPAWN_TIME_MAX);
    private boolean defenderCanMoveUp = true;
    private boolean defenderCanMoveDown = true;
    public boolean gameOver = false;
    public static boolean isPaused = false;
    public boolean escBlocked = false;

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, Constants.GAME_WIDTH, Constants.GAME_HEIGHT,
                new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT)));
        setUpWorld();
        setupCamera();
        Gdx.input.setInputProcessor(this);
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        AssetManager.initialize();
        setUpBackground();
        setUpDefender();
        addActor(new ScoreCounter(AssetManager.getFont()));
    }

    private void setUpBackground() {
        addActor(new Background(AssetManager.getBackground()));
    }

    private void setUpDefender() {
        defender = new Defender(WorldUtils.createDefender(world),AssetManager.getTextureAtlas());
        addActor(defender);
        addActor(new HealthCounter(AssetManager.getTextureAtlas()));
    }

    private void setupCamera() {
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        createEnemy();
        updateGameActors();
        updateGameState();
        while (bulletsToAdd>0)
        {
            createBullet();
            bulletsToAdd--;
        }
    }

    private void updateGameState() {
        if (HealthCounter.health <= 0){
            gameOver = true;
        }
    }

    private void updateGameActors() {

        Array<Actor> activeActors = this.getActors();
        activeGameActors = new ArrayList<GameActor>();
        for (Actor actor : activeActors)
        {
            if (actor instanceof Enemy || actor instanceof Bullet)
                activeGameActors.add((GameActor)actor);
            if (actor instanceof Defender && defender != null)
                defender = (Defender)actor;
        }
        for (GameActor activeGameActor : activeGameActors)
        {
            if (!BodyUtils.bodyInBounds(activeGameActor.body) || activeGameActor.toDelete) {
                if (activeGameActor instanceof Enemy && !activeGameActor.toDelete)
                    HealthCounter.health -= 1;
                world.destroyBody(activeGameActor.body);
                activeGameActor.remove();
            }
        }

        defenderCanMoveUp = true;
        defenderCanMoveDown = true;

        if(defender.getPosition().y>=(Constants.BOUND/2)) {
            defender.stop();
            defenderCanMoveUp = false;
        }
        if(defender.getPosition().y<(Constants.DEFENDER_HEIGHT/2)) {
            defender.stop();
            defenderCanMoveDown = false;
        }
    }

    private void createEnemy() {
        if (System.currentTimeMillis()-lastSpawn > nextEnemySpawn) {
            Enemy enemy = new Enemy(WorldUtils.createEnemy(world),AssetManager.getTextureAtlas());
            addActor(enemy);
            nextEnemySpawn = RandomUtils.RandInt(Constants.ENEMY_RESPAWN_TIME_MIN, Constants.ENEMY_RESPAWN_TIME_MAX);
            lastSpawn=System.currentTimeMillis();
        }
    }

    public void createBullet(){
        Vector2 defenderPosition = new Vector2(0,0);
        defenderPosition.x = defender.getPosition().x + Constants.DEFENDER_WIDTH/2 + Constants.BULLET_WIDTH/2;
        defenderPosition.y = defender.getPosition().y;
        Bullet bullet = new Bullet(WorldUtils.createBullet(world, defenderPosition),AssetManager.getTextureAtlas());
        addActor(bullet);
    }

    public void shoot(){
        if (System.currentTimeMillis()-lastShoot > Constants.SHOOT_RATE){
            bulletsToAdd++;
            lastShoot = System.currentTimeMillis();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode==Input.Keys.UP && defenderCanMoveUp)
            defender.moveUp();
        if (keycode==Input.Keys.DOWN && defenderCanMoveDown)
            defender.moveDown();
        if (keycode==Input.Keys.SPACE)
            shoot();
        if (keycode==Input.Keys.ESCAPE) {
            escBlocked = false;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode==Input.Keys.UP || keycode==Input.Keys.DOWN)
            defender.stop();
        if (keycode==Input.Keys.ESCAPE && !escBlocked)
            isPaused = true;
        return false;
    }

    @Override
    public void beginContact(Contact contact) {
        Body firstBody = contact.getFixtureA().getBody();
        Body secondBody = contact.getFixtureB().getBody();
        GameActor firstActor = null;
        GameActor secondActor = null;
        for (GameActor actor : activeGameActors) {
            if (firstBody == actor.body)
                firstActor = actor;
            if (secondBody == actor.body)
                secondActor = actor;
        }
        if(defender.body == firstBody)
            firstActor = defender;
        if(defender.body == secondBody)
            secondActor = defender;


        if ((firstActor instanceof Enemy && secondActor instanceof Bullet) || (firstActor instanceof Bullet && secondActor instanceof Enemy)){
            firstActor.toDelete = true;
            secondActor.toDelete = true;
            ScoreCounter.score += 10;
        }
        if (firstActor instanceof Enemy && secondActor instanceof Defender && !firstActor.toDelete){
            firstActor.toDelete = true;
            HealthCounter.health -= 1;
        }
        if (firstActor instanceof Defender && secondActor instanceof Enemy && !secondActor.toDelete){
            secondActor.toDelete = true;
            HealthCounter.health -= 1;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void clean(){
        Array<Actor> activeActors = this.getActors();
        for (Actor actor : activeActors){
            if(actor instanceof GameActor)
                world.destroyBody(((GameActor)actor).body);
            actor.remove();
        }
        world.dispose();
        AssetManager.dispose();
    }
}
