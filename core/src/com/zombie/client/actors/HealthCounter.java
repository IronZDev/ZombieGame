package com.zombie.client.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zombie.client.utils.Constants;

public class HealthCounter extends Actor{

    private TextureRegion defenderTexture;
    public static int health = Constants.HEALTH;

    public HealthCounter(TextureAtlas textureAtlas) {
        defenderTexture = textureAtlas.findRegion(Constants.DEFENDER_REGION_NAME);
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int counter = 0; counter<health; ++counter)
            batch.draw(defenderTexture, Constants.HEALTH_COUNTER_X + (25 * counter), Constants.HEALTH_COUNTER_Y, Constants.DEFENDER_WIDTH*3, Constants.DEFENDER_HEIGHT*3);
    }
}