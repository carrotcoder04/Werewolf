package com.werewolf.game.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.constant.FilePaths;
import com.io.Reader;
import com.io.Writer;
import com.resources.ResourcesManager;
import com.resources.Texture2D;
import com.serialization.Serializable;


public class Item implements Serializable {
    private Image image;
    private ItemType type;
    private String imagePath;
    public Item(Reader reader) {
        deserialize(reader);
    }
    public Item(Image image, ItemType type) {
        this.type = type;
        this.image = image;
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
    @Override
    public void deserialize(Reader reader) {
        this.type = ItemType.values()[reader.nextByte()];
        this.imagePath = FilePaths.getAssetPath(reader.nextInt());
        this.image = new Image(ResourcesManager.getTexture(imagePath));
    }
    @Override
    public Writer serialize() {
        Writer writer = new Writer(6);
        byte type = (byte) this.type.ordinal();
        writer.writeByte(type);
        writer.writeInt(FilePaths.getIndexOfAsset(imagePath));
        return writer;
    }
    public Item clone() {
        return new Item(new Image(ResourcesManager.getTexture(imagePath)), type);
    }
}
