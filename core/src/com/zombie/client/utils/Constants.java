package com.zombie.client.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    public static final float WORLD_TO_SCREEN = 4f;

    public static final int VIEWPORT_WIDTH = GAME_WIDTH/4;
    public static final int VIEWPORT_HEIGHT = GAME_HEIGHT/4;

    public static final String BACKGROUND_IMAGE_PATH = "NightCity.png";
    public static final String GAME_OVER_IMAGE_PATH = "GameOverBackground.png";
    public static final String MAIN_MENU_IMAGE_PATH = "MainMenu.png";
    public static final String SCOREBOARD_IMAGE_PATH = "Scoreboard.png";

    public static final String FONT_PATH = "gymkhana-bk.ttf";

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, 0);

    public static final int BOUND = VIEWPORT_HEIGHT/3*2;

    public static final float DEFENDER_WIDTH = 6.25f;
    public static final float DEFENDER_HEIGHT = 12.5f;
    public static final float DEFENDER_X = 10;
    public static final float DEFENDER_Y = VIEWPORT_HEIGHT/4;
    public static final float DEFENDER_DENSITY = 30f;
    public static final Vector2 DEFENDER_LINEAR_VELOCITY = new Vector2(0, 50f);

    public static final float HEALTH_COUNTER_X = DEFENDER_X;
    public static final float HEALTH_COUNTER_Y = GAME_HEIGHT - 50;

    public static final int HEALTH = 3;

    public static final float SCORE_COUNTER_X = GAME_WIDTH - 200;
    public static final float SCORE_COUNTER_Y = GAME_HEIGHT - 15;

    public static final float ENEMY_WIDTH = 6.25f;
    public static final float ENEMY_HEIGHT = 12.5f;
    public static final float ENEMY_X = VIEWPORT_WIDTH+ENEMY_WIDTH;
    public static final float ENEMY_Y_MIN = 1+ENEMY_HEIGHT/2;
    public static final float ENEMY_Y_MAX = VIEWPORT_HEIGHT/3-ENEMY_HEIGHT/2;
    public static final float ENEMY_DENSITY = DEFENDER_DENSITY;
    public static final Vector2 ENEMY_LINEAR_VELOCITY = new Vector2(-25f, 0);

    public static final float BULLET_WIDTH = 6f;
    public static final float BULLET_HEIGHT = 2f;
    public static final float BULLET_DENSITY = DEFENDER_DENSITY;
    public static final Vector2 BULLET_LINEAR_VELOCITY = new Vector2(150f, 0);
    public static final int SHOOT_RATE = 1200;

    public static final int ENEMY_RESPAWN_TIME_MIN = 500;
    public static final int ENEMY_RESPAWN_TIME_MAX = 1500;

    public static final String SPRITES_ATLAS_PATH = "sprites.txt";
    public static final String DEFENDER_REGION_NAME = "Player";
    public static final String BULLET_REGION_NAME = "bullet";
    public static final String[] ZOMBIE_WALKING_REGION_NAME = new String[] {"zombie1", "zombie2", "zombie3", "zombie4", "zombie5", "zombie6","zombie7", "zombie8", "zombie9", "zombie10", "zombie11", "zombie12"};


}
