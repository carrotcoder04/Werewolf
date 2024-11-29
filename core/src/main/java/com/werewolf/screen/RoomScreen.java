package com.werewolf.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.config.GameConfig;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.tween.Tween;
import com.werewolf.BoxChat;
import com.werewolf.GameState;
import com.werewolf.RolePanel;
import com.werewolf.game.PlayerInfo;
import com.werewolf.game.Player;
import com.werewolf.game.PlayerBoard;

public class RoomScreen implements Screen , Disposable {
    private Stage stage;
    private PlayerBoard playerBoard;
    private BoxChat boxChat;
    private RolePanel rolePanel;
    private Color theme;
    private Label infoLabel;
    private static RoomScreen instance;
    public static RoomScreen getInstance() {
        return instance;
    }
    public RoomScreen(PlayerInfo mainPlayerInfo) {
        Player.setMainPlayer(new Player(mainPlayerInfo));
    }
    @Override
    public void show() {
        instance = this;
        theme = Color.WHITE.cpy();
        initStage();
        initPlayerBoard();
        initBoxChat();
        initRolePanel();
        initInfoLabel();
    }
    public void setThemeColorByState(GameState state) {
        switch (state) {
            case NIGHT:
                Tween lerpColorNgiht = new Tween(1.5f) {
                    @Override
                    protected void onUpdate(float value) {
                        theme.lerp(Color.GRAY, value);
                    }
                };
                lerpColorNgiht.start();
                break;
            case DAY:
                Tween lerpColorDay = new Tween(1.5f) {
                    @Override
                    protected void onUpdate(float value) {
                        theme.lerp(Color.WHITE, value);
                    }
                };
                lerpColorDay.start();
                break;
        }
    }
    private void initStage() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }
    private void initPlayerBoard() {
        playerBoard = new PlayerBoard();
        stage.addActor(playerBoard);
    }
    private void initBoxChat() {
        boxChat = new BoxChat();
        stage.addActor(boxChat.getRoot());
    }
    private void initRolePanel() {
        rolePanel = new RolePanel(stage);
    }
    private void initInfoLabel() {
        BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_REGULAR);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK.cpy());
        infoLabel = new Label("",style);
        stage.addActor(infoLabel);
    }
    public void setInfoText(String infoText) {
        infoLabel.setText(infoText);
        infoLabel.setPosition(GameConfig.SCREEN_WIDTH/2 - infoLabel.getText().length * 3,GameConfig.SCREEN_HEIGHT-10);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(theme);
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
    }
}
