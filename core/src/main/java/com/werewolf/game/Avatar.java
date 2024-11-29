package com.werewolf.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.io.Reader;
import com.io.Writer;
import com.werewolf.game.inventory.Item;
import com.werewolf.game.inventory.ItemType;
import com.serialization.Serializable;
import java.util.TreeMap;

public class Avatar implements Serializable {
    private final TreeMap<ItemType, Item> items;
    private final Group root = new Group();
    public Avatar() {
        items = new TreeMap<>();
    }
    public Avatar(Reader reader) {
        items = new TreeMap<>();
        deserialize(reader);
    }
    public Avatar(TreeMap<ItemType,Item> items) {
        this.items = items;
        refreshSkin();
    }
    public void setItem(Item item) {
        items.put(item.getType(), item);
        refreshSkin();
    }
    private void refreshSkin() {
        root.clearChildren();
        for(Item item : items.values()) {
            Image image = item.getImage();
            image.setPosition(root.getWidth()/2-image.getWidth()/2, 0);
            root.addActor(item.getImage());
        }
    }
    public Group getRoot() {
        return root;
    }
    @Override
    public void deserialize(Reader reader) {
        for (int i = 0; i < ItemType.values().length; i++) {
            Item item = new Item(reader);
            items.put(item.getType(), item);
        }
        refreshSkin();
    }

    @Override
    public Writer serialize() {
        Writer writer = new Writer(1024);
        for(Item item : items.values()) {
            writer.write(item);
        }
        return writer;
    }

    public Avatar clone() {
        Avatar avatar = new Avatar();
        for(Item item : items.values()) {
            avatar.items.put(item.getType(), item.clone());
        }
        avatar.refreshSkin();
        return avatar;
    }
}
