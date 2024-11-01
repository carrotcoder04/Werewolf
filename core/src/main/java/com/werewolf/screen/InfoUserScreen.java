package com.werewolf.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.resources.Texture2D;
import com.werewolf.game.Inventory;
import com.werewolf.game.ItemType;


public class InfoUserScreen implements Screen {
    private Texture2D background;
    private Stage stage;
    private Color clearColor;
    private Inventory inventory;
    @Override
    public void show() {
        clearColor = new Color(143/255f, 145/255f, 148/255f, 1);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        background = ResourcesManager.getTexture(FilePaths.BACKGROUND_INFO_SCREEN);
        Image backgroundImage = new Image(background);
        backgroundImage.setOrigin(Align.center);
        backgroundImage.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2,Align.center);
        backgroundImage.setScale(0.66f);
        stage.addActor(backgroundImage);
        Group inventoryGroup = new Group();
        stage.addActor(inventoryGroup);
        inventory = new Inventory(inventoryGroup);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(clearColor);
        stage.act(delta);
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            ItemType itemType = ItemType.values()[MathUtils.random(0, ItemType.values().length - 1)];
            inventory.setSelectedTab(itemType);
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
