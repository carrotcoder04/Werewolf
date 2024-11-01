package com.werewolf.screen;

import com.badlogic.gdx.Gdx;
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
    private Texture2D background;
    @Override
    public void show() {
        // Create stage
        stage = new Stage();
        // Load background texture
        background = ResourcesManager.getTexture(FilePaths.BACKGROUND_INTRO);
        // Create background image
        Image backgroundImage = new Image(background);
        backgroundImage.setOrigin(Align.center);
        backgroundImage.setScale(1.31f);
        backgroundImage.setPosition(GameConfig.SCREEN_WIDTH/2, GameConfig.SCREEN_HEIGHT / 2,Align.center);
        // Add background to stage
        stage.addActor(backgroundImage);
        // Fade alpha background image 0 -> 1
        Tween fadeIn = new FadeTween(backgroundImage, 0, 1, 1.5f);
        fadeIn.onComplete(() -> {
            Tween delayTween = new DelayTween(1,() -> {
                Tween fadeOut = new FadeTween(backgroundImage, 1, 0, 1f);
                fadeOut.onComplete(() -> {
                    MainGame.getInstance().setScreen(new InfoUserScreen());
                });
                fadeOut.start();
            });
            delayTween.start();
        });
        fadeIn.start();
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
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
