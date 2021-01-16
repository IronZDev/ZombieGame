package com.zombie.client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class AssetManager {
    private static Texture background;
    private static TextureAtlas textureAtlas;
    private static BitmapFont font;
    public static void initialize(){
        background = new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH));
        textureAtlas = new TextureAtlas(Constants.SPRITES_ATLAS_PATH);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_PATH));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = Color.RED;
        font = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();
    }

    public static Texture getBackground(){
        return background;
    }

    public static TextureAtlas getTextureAtlas(){
        return textureAtlas;
    }

    public static BitmapFont getFont(){
        return font;
    }

    public static void dispose(){
        background.dispose();
        textureAtlas.dispose();
        font.dispose();
    }
}
