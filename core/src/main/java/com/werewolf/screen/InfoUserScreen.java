package com.werewolf.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.config.GameConfig;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.resources.Texture2D;
import com.werewolf.game.Inventory;

public class InfoUserScreen implements Screen {
    private Texture2D background;
    private Stage stage;
    private TextField nameField;
    private TextButton playButton;
    private TextButton roleButton;
    private final Color clearColor = new Color(120 / 255f, 120 / 255f, 120 / 255f, 1);
    private Inventory inventory;
    @Override
    public void show() {
        initStage();
        initBackground();
        initInventory();
        initNameTextField();
        initPlayButton();
        initRoleButton();
    }
    private void initStage() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }
    private void initBackground() {
        background = ResourcesManager.getTexture(FilePaths.BACKGROUND_INFO_SCREEN);
        Image backgroundImage = new Image(background);
        backgroundImage.setOrigin(Align.center);
        backgroundImage.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, Align.center);
        backgroundImage.setScale(0.66f);
        stage.addActor(backgroundImage);
    }
    private void initInventory() {
        Group inventoryGroup = new Group();
        stage.addActor(inventoryGroup);
        inventory = new Inventory(inventoryGroup);
    }
    private void initNameTextField() {
        nameField = new TextField("", ResourcesManager.getSkin(FilePaths.TEXT_FIELD_NAME));
        nameField.setMessageText("Nhập tên của bạn...");
        nameField.setAlignment(Align.center);
        nameField.setSize(360, 62);
        nameField.setMaxLength(10);
        nameField.getStyle().background.setLeftWidth(-135);
        nameField.setPosition(GameConfig.SCREEN_WIDTH / 2, GameConfig.SCREEN_HEIGHT - 100, Align.center);
        nameField.setTextFieldListener((textField, c) -> {
            playButton.setDisabled(textField.getText().isEmpty());
        });
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor actor = stage.hit(x, y, true);
                if(nameField != actor) {
                    stage.unfocus(nameField);
                }
                return true;
            }
        });
        stage.addActor(nameField);
    }
    private void initPlayButton() {
        Skin skin = ResourcesManager.getSkin(FilePaths.BUTTON_PLAY);
        playButton = new TextButton("Play",skin);
        playButton.setPosition(GameConfig.SCREEN_WIDTH/2+110, GameConfig.SCREEN_HEIGHT-100,Align.center);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                play();
            }
        });
        playButton.setDisabled(true);
        stage.addActor(playButton);
    }
    private void initRoleButton() {
        Skin skin = ResourcesManager.getSkin(FilePaths.BUTTON_ROLE);
        roleButton = new TextButton("Vai trò",skin);
        roleButton.setPosition(80, GameConfig.SCREEN_HEIGHT-40,Align.center);
        roleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showRolePanel();
            }
        });
        stage.addActor(roleButton);
    }
    private void play() {

    }
    private void showRolePanel() {

    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(clearColor);
        stage.act(delta);
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
