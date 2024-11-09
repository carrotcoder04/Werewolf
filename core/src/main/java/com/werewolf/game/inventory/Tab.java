package com.werewolf.game.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.config.GameConfig;
import com.resources.ResourcesManager;
import com.tween.ScaleXYTween;
import com.tween.Tween;

import java.util.ArrayList;

public class Tab {
    private final ItemType type;
    private ArrayList<InventoryItem> items;
    private Image icon;
    private InventoryItem selectedItem;
    private Table itemTable;
    private ScrollPane scrollPane;
    private static final Color selected;
    private static final Color unSelected;
    static {
        selected = Color.BLACK.cpy();
        selected.a = 0.9f;
        unSelected = Color.GRAY.cpy();
        unSelected.a = 0.75f;
    }
    public Tab(ItemType type,ArrayList<InventoryItem> items, Image icon) {
        this.type = type;
        initIcon(icon);
        initItemTable();
        setItems(items);
    }
    private void initIcon(Image icon) {
        this.icon = icon;
        icon.setScaling(Scaling.fit);
        icon.setOrigin(Align.center);
        Color iconColor = Color.GRAY.cpy();
        iconColor.a = 0.75f;
        icon.setColor(iconColor);
        icon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Inventory.getInstance().setSelectedTab(type);
            }
        });
        Inventory.getInstance().getTabTable().add(icon).width(36).height(36).pad(9);
    }
    private void initItemTable() {
        itemTable = new Table();
        itemTable.top();
        SpriteDrawable backgroundTable = new SpriteDrawable(new Sprite(ResourcesManager.getOnePixelTexture()));
        itemTable.setBackground(backgroundTable);
        scrollPane = new ScrollPane(itemTable);
        scrollPane.setScrollingDisabled(false, false);
        scrollPane.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT/2-60);
    }
    private void setItems(ArrayList<InventoryItem> items) {
        this.items = items;
        itemTable.clear();
        int count = 0;
        for (InventoryItem item : items) {
            itemTable.add(item.getRoot()).center().width(item.getWidth()).height(item.getHeight()).pad(10);
            count++;
            if (count % 3 == 0) {
                itemTable.row();
            }
        }
    }
    public void selected(Group group) {
        icon.setColor(selected);
        Tween scaleTween = new ScaleXYTween(icon,1,1.1f,0.1f);
        scaleTween.onComplete(() -> {
            icon.setScale(1);
        });
        scaleTween.start();
        group.addActor(scrollPane);
    }
    public void unSelected() {
        icon.setColor(unSelected);
        scrollPane.remove();
    }
    public ItemType getType() {
        return type;
    }
    public void setSelectedItem(InventoryItem selectedItem) {
        if(this.selectedItem != selectedItem) {
            Inventory.getInstance().getPlayerAvatar().setItem(selectedItem.getItemAvatar());
            if(this.selectedItem != null) {
                this.selectedItem.unSelected();
            }
            this.selectedItem = selectedItem;
            this.selectedItem.changeColorSelected();
        }
    }
}
