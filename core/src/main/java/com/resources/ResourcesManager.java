package com.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.constant.Constants;
import java.util.HashMap;

public class ResourcesManager {
    private static final HashMap<String,Texture2D> textures;
    private static final HashMap<String,Skin> skins;
    private static final HashMap<String, BitmapFont> fonts;
    private static final Pixmap onePixelPixmap;
    private static final Texture onePixelTexture;
    private ResourcesManager() {}
    static {
        textures = new HashMap<>();
        skins = new HashMap<>();
        fonts = new HashMap<>();
        onePixelPixmap = new Pixmap(1,1,Pixmap.Format.RGBA8888);
        onePixelPixmap.setColor(Color.WHITE);
        onePixelPixmap.fill();
        onePixelTexture = new Texture(onePixelPixmap);
    }
    public static Texture2D getTexture(String path) {
        if (!textures.containsKey(path) || !textures.get(path).isManaged()) {
            Texture2D texture = new Texture2D(Gdx.files.internal(path));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textures.put(path, texture);
        }
        return textures.get(path);
    }
    public static void removeTexture(String path) {
        textures.remove(path);
    }
    public static Skin getSkin(String path) {
        if (!skins.containsKey(path)) {
            Skin skin = new Skin(Gdx.files.internal(path));
            skins.put(path, skin);
        }
        return skins.get(path);
    }
    public static BitmapFont getFont(String path) {
        if (!fonts.containsKey(path)) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.characters = Constants.FONT_CHARACTERS;
            BitmapFont font = generator.generateFont(parameter);
            generator.dispose();
            fonts.put(path, font);
        }
        return fonts.get(path);
    }
    public static Texture getOnePixelTexture() {
        return onePixelTexture;
    }
    public static void dispose() {
        try {
            for (Texture2D texture : textures.values()) {
                try {
                    texture.dispose();
                }
                catch (Exception ignored) {
                }
            }
            for (Skin skin : skins.values()) {
                try {
                    skin.dispose();
                }
                catch (Exception ignored) {

                }
            }
        }
        catch (Exception ignored) {

        }
        try {
            onePixelPixmap.dispose();
        }
        catch (Exception ignored) {

        }
        try {
            onePixelTexture.dispose();
        }
        catch (Exception ignored) {

        }
    }
}
