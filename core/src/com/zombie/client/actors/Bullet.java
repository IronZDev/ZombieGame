package com.zombie.client.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.zombie.client.box2d.BulletUserData;
import com.zombie.client.utils.Constants;

public class Bullet extends GameActor {

    private TextureRegion bulletTexture;

    public Bullet(Body body, TextureAtlas textureAtlas) {
        super(body);
        bulletTexture = textureAtlas.findRegion(Constants.BULLET_REGION_NAME);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(bulletTexture, screenRectangle.x, screenRectangle.y, screenRectangle.width,
                screenRectangle.height);
    }

    @Override
    public BulletUserData getUserData() {
        return (BulletUserData) userData;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }
}