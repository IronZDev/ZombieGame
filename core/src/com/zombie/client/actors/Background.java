package com.zombie.client.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zombie.client.utils.Constants;

public class Background extends Actor {

    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds;

    public Background(Texture background) {
        textureRegion = new TextureRegion(background);
        textureRegionBounds = new Rectangle(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRegionBounds.x, textureRegionBounds.y, Constants.GAME_WIDTH,
                Constants.GAME_HEIGHT);
    }
}
