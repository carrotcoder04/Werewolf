package com.werewolf.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.config.GameConfig;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.resources.Texture2D;
import com.tween.*;
import com.werewolf.MainGame;

public class SplashScreen implements Screen {
    private Stage stage;
    private Texture2D backgroundTexture;
    private Image background;
    @Override
    public void show() {
        initStage();
        initBackground();
        fadeBackground();
    }
    private void initStage() {
        stage = new Stage();
    }
    private void initBackground() {
        backgroundTexture = ResourcesManager.getTexture(FilePaths.BACKGROUND_INTRO);
        background = new Image(backgroundTexture);
        background.setOrigin(Align.center);
        background.setScale(1.31f);
        background.setPosition(GameConfig.SCREEN_WIDTH/2, GameConfig.SCREEN_HEIGHT / 2,Align.center);
        stage.addActor(background);
    }
    private void fadeBackground() {
        Tween fadeIn = new FadeTween(background, 0, 1, 1.5f);
        fadeIn.onComplete(() -> {
            Tween delayTween = new DelayTween(1,() -> {
                Tween fadeOut = new FadeTween(background, 1, 0, 0.3f);
                fadeOut.onComplete(() -> {
                    MainGame.getInstance().setScreen(new ClientInfoScreen());
                });
                fadeOut.start();
            });
            delayTween.start();
        });
        fadeIn.start();
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
        backgroundTexture.dispose();
    }
}
