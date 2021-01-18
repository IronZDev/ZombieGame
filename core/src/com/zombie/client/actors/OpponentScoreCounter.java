package com.zombie.client.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zombie.client.utils.Constants;

public class OpponentScoreCounter extends Actor {
    public static int score = 0;
    private BitmapFont font;

    public OpponentScoreCounter(BitmapFont font24) {
        this.font=font24;
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        String currentScore = "Score: "+score;
        font.draw(batch,currentScore, Constants.OPPONENT_SCORE_COUNTER_X,Constants.SCORE_COUNTER_Y);
    }
}
