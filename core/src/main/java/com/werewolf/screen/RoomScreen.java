package com.werewolf.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.werewolf.BoxChat;
import com.werewolf.game.PlayerInfo;
import com.werewolf.game.Player;
import com.werewolf.game.PlayerBoard;

public class RoomScreen implements Screen , Disposable {
    private Stage stage;
    private PlayerBoard playerBoard;
    private BoxChat boxChat;
    public RoomScreen(PlayerInfo mainPlayerInfo) {
        Player.setMainPlayer(new Player(mainPlayerInfo));
    }
    @Override
    public void show() {
        initStage();
        initPlayerBoard();
        initBoxChat();
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
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
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
