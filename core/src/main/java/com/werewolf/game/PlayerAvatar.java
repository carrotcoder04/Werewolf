package com.werewolf.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.config.GameConfig;
import com.tween.MoveYTween;
import com.tween.Tween;

import java.util.TreeMap;

public class PlayerAvatar implements Disposable {
    private final TreeMap<ItemType,Item> items;
    private final Group root = new Group();
    public PlayerAvatar() {
        items = new TreeMap<>();
        root.setWidth(300);
        root.setHeight(300);
        root.setX(GameConfig.SCREEN_WIDTH / 2 - root.getWidth() / 2);
        Tween moveTween = new MoveYTween(root,0,GameConfig.SCREEN_HEIGHT/2f,1, Align.bottom);
        moveTween.start();
    }
    public void setItem(Item item) {
        items.put(item.getType(), item);
        refreshSkin();
    }
    private void refreshSkin() {
        root.clear();
        for(Item item : items.values()) {
            Image image = item.getImage();
            image.setPosition(root.getWidth()/2-image.getWidth()/2, 0);
            root.addActor(item.getImage());
        }
    }
    public PlayerAvatar(TreeMap<ItemType,Item> items) {
        this.items = items;
        refreshSkin();
    }

    public Group getRoot() {
        return root;
    }
    @Override
    public void dispose() {
    }
}
