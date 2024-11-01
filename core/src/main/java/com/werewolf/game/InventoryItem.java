package com.werewolf.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.resources.ResourcesManager;
import com.resources.Texture2D;

public class InventoryItem  {
    private final Group root = new Group();
    private final Image image;
    private final Image background;
    private final ItemType type;
    private final String imagePath;
    private final float width;
    private final float height;
    private final static Color selected;
    private final static Color unselected;
    static {
        selected = new Color(152/255f, 227/255f, 172/255f,0.6f);
        unselected = new Color(1,1,1,0.6f);
    }
    public InventoryItem(Image image, ItemType type,Image background, float width, float height) {
        this.image = image;
        this.type = type;
        image.setScaling(Scaling.fit);
        root.addActor(image);
        TextureRegionDrawable drawable = (TextureRegionDrawable) image.getDrawable();
        Texture texture = drawable.getRegion().getTexture();
        if(texture instanceof Texture2D) {
            Texture2D texture2D = (Texture2D) texture;
            imagePath = texture2D.getPath();
        }
        else {
            imagePath = null;
        }
        this.width = width;
        this.height = height;
        this.background = background;
        background.getColor().a = 0.6f;
        background.setSize(width, height);
        image.setScale(0.5f);
        image.setOrigin(width  - image.getWidth() * image.getScaleX(), height/2-15);
        root.setSize(width, height);
        root.addActorAt(0,background);
        root.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selected();
            }
        });

    }
    public void selected() {
        Inventory.getInstance().setSelectedItem(this);
    }
    public void changeColorSelected() {
        background.setColor(selected);
    }
    public void unSelected() {
        background.setColor(unselected);
    }
    public Group getRoot() {
        return root;
    }
    public ItemType getType() {
        return type;
    }
    public Item getItemAvatar() {
        String path =imagePath.replace("store.","avatar_small.");
        Image image = new Image(ResourcesManager.getTexture(path));
        return new Item(image,type);
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
}
