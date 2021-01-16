package com.zombie.client.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.zombie.client.box2d.EnemyUserData;
import com.zombie.client.box2d.DefenderUserData;
import com.zombie.client.box2d.BulletUserData;



public class WorldUtils {
    public static World createWorld() {
        return new World(Constants.WORLD_GRAVITY, true);
    }

    public static Body createDefender(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(new Vector2(Constants.DEFENDER_X, Constants.DEFENDER_Y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.DEFENDER_WIDTH / 2, Constants.DEFENDER_HEIGHT / 2);
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.DEFENDER_DENSITY);
        body.resetMassData();
        body.setUserData(new DefenderUserData(Constants.DEFENDER_WIDTH,Constants.DEFENDER_HEIGHT));
        shape.dispose();
        return body;
    }

    public static Body createEnemy(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(Constants.ENEMY_X, RandomUtils.RandFloat(Constants.ENEMY_Y_MIN,Constants.ENEMY_Y_MAX)));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.ENEMY_WIDTH / 2, Constants.ENEMY_HEIGHT / 2);
        while(world.isLocked());
        Body body = world.createBody(bodyDef);
        body.createFixture(shape, Constants.ENEMY_DENSITY);
        body.resetMassData();
        body.setUserData(new EnemyUserData(Constants.ENEMY_WIDTH,Constants.ENEMY_HEIGHT,Constants.ZOMBIE_WALKING_REGION_NAME));
        shape.dispose();
        return body;
    }

    public static BodyDef templateBullet = null;



    public static Body createBullet(World world, Vector2 spawnPosition) {
        if (templateBullet == null){
            templateBullet = new BodyDef();
            templateBullet.type = BodyDef.BodyType.KinematicBody;
        }
        templateBullet.position.set(spawnPosition);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Constants.BULLET_WIDTH / 2, Constants.BULLET_HEIGHT / 2);
        Body body = world.createBody(templateBullet);
        body.createFixture(shape, Constants.BULLET_DENSITY);
        body.resetMassData();
        body.setUserData(new BulletUserData(Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT));
        shape.dispose();
        return body;
    }
}
