package com.werewolf.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.resources.Texture2D;


public class Item {
    private final Image image;
    private final ItemType type;
    private final String imagePath;
    public Item(Image image, ItemType type) {
        this.image = image;
        this.type = type;
        TextureRegionDrawable drawable = (TextureRegionDrawable) image.getDrawable();
        Texture texture = drawable.getRegion().getTexture();
        if(texture instanceof Texture2D) {
            Texture2D texture2D = (Texture2D) texture;
            imagePath = texture2D.getPath();
        }
        else {
            imagePath = null;
        }
    }
    public Image getImage() {
        return image;
    }
    public ItemType getType() {
        return type;
    }
}
