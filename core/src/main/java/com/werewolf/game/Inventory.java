package com.werewolf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.config.GameConfig;
import com.constant.FilePaths;
import com.resources.ResourcesManager;

import java.util.ArrayList;

public class Inventory implements Disposable {

    private ArrayList<Tab> tabs;
    private Tab selectedTab;
    private final Group mainGroup;
    private static Inventory instance;
    private PlayerAvatar playerAvatar;
    private Table tabTable;
    public Inventory(Group mainGroup) {
        instance = this;
        this.mainGroup = mainGroup;
        initPlayerAvatar();
        initTabTable();
        initTabs();
        setSelectedTab(ItemType.BACK);
    }
    private void initPlayerAvatar() {
        playerAvatar = new PlayerAvatar();
        mainGroup.addActor(playerAvatar.getRoot());
    }
    private void initTabTable() {
        tabTable = new Table();
        tabTable.top();
        tabTable.setSize(GameConfig.SCREEN_WIDTH, 120);
        tabTable.setPosition(0, GameConfig.SCREEN_HEIGHT/2-tabTable.getHeight());
        SpriteDrawable backgroundTabTable = new SpriteDrawable(new Sprite(ResourcesManager.getOnePixelTexture()));
        tabTable.setBackground(backgroundTabTable);
        mainGroup.addActor(tabTable);
    }
    private void initTabs() {
        tabs = new ArrayList<>();
        TextureRegion backgroundItemTextureRegion = new TextureRegion(ResourcesManager.getTexture(FilePaths.BACKGROUND_ITEM));
        for (ItemType type : ItemType.values()) {
            ArrayList<String> files = FilePaths.getItemsFilePath(type);
            if(type == ItemType.BODY || type == ItemType.HEAD) {
                Image image = new Image(ResourcesManager.getTexture(files.get(1)));
                Item item = new Item(image,type);
                playerAvatar.setItem(item);
                continue;
            }
            ArrayList<InventoryItem> items = new ArrayList<>();
            for (String file : files) {
                if (file.contains("_store")) {
                    TextureRegion itemTextureRegion = new TextureRegion(ResourcesManager.getTexture(file));
                    Image itemImage = new Image(itemTextureRegion);
                    Image backgroundItemImage = new Image(backgroundItemTextureRegion);
                    InventoryItem item = new InventoryItem(itemImage, type, backgroundItemImage, 133, 106);
                    items.add(item);
                }
            }
            Image icon = new Image(ResourcesManager.getTexture(FilePaths.getIconTabPath(type)));
            Tab tab = new Tab(type, items,icon);
            tabs.add(tab);
            int rand = MathUtils.random(0,items.size()-1);
            InventoryItem item = items.get(rand);
            setSelectedItem(item);
        }
    }
    private Tab findTab(ItemType type) {
        for (Tab tab : tabs) {
            if (tab.getType() == type) {
                return tab;
            }
        }
        return null;
    }
    public void setSelectedItem(InventoryItem item) {
        Tab tab = findTab(item.getType());
        if (tab != null) {
            tab.setSelectedItem(item);
        }
    }
    public PlayerAvatar getPlayerAvatar() {
        return playerAvatar;
    }
    public void setSelectedTab(ItemType selectedTabType) {
        Tab tab = findTab(selectedTabType);
        if (tab == null || selectedTab == tab) {
            return;
        }
        if(selectedTab != null) {
            selectedTab.unSelected();
        }
        selectedTab = tab;
        selectedTab.selected(mainGroup);
    }
    public Table getTabTable() {
        return tabTable;
    }
    public static Inventory getInstance() {
        return instance;
    }
    @Override
    public void dispose() {
        instance = null;
    }
}
