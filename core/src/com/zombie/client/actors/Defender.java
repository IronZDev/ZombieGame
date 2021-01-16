package com.zombie.client.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.zombie.client.box2d.DefenderUserData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zombie.client.utils.Constants;

public class Defender extends GameActor {

    private TextureRegion defenderTexture;
//    public int health = 3;

    public Defender(Body body, TextureAtlas textureAtlas) {
        super(body);
        defenderTexture = textureAtlas.findRegion(Constants.DEFENDER_REGION_NAME);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(defenderTexture, screenRectangle.x, screenRectangle.y, screenRectangle.width,
                screenRectangle.height);
    }

    @Override
    public DefenderUserData getUserData() {
        return (DefenderUserData) userData;
    }

    public void moveUp() {
        body.setLinearVelocity(getUserData().getLinearVelocity());
    }

    public void stop(){
        body.setLinearVelocity(0,0);
    }

    public void moveDown() {
        if (body.getPosition().y>1)
            body.setLinearVelocity(getUserData().getLinearVelocity().cpy().rotate(180));
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}