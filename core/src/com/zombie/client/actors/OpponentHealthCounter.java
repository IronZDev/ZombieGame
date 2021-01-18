package com.zombie.client.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zombie.client.utils.Constants;

public class OpponentHealthCounter extends Actor {
    private TextureRegion defenderTexture;
    private BitmapFont font;
    public static int health = Constants.HEALTH;

    public OpponentHealthCounter(TextureAtlas textureAtlas, BitmapFont font24) {
        defenderTexture = textureAtlas.findRegion(Constants.DEFENDER_REGION_NAME);
        this.font = font24;
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, "Opponent:",Constants.OPPONENT_SCORE_COUNTER_X,Constants.HEALTH_COUNTER_Y + 40);
        for (int counter = 0; counter<health; ++counter)
            batch.draw(defenderTexture, Constants.OPPONENT_HEALTH_COUNTER_X + (25 * counter), Constants.HEALTH_COUNTER_Y - 35, Constants.DEFENDER_WIDTH*3, Constants.DEFENDER_HEIGHT*3);
    }
}
