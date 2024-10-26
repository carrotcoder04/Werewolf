package com.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.scene2d.element.TileBackground;
import com.tween.*;


public class SplashScreen implements Screen {
    private Stage stage;
    @Override
    public void show() {
        Texture iconTexture = ResourcesManager.getTexture(FilePaths.ICON_LAUNCHER);
        Image icon = new Image(iconTexture);
        icon.setOrigin(icon.getWidth() / 2, icon.getHeight() / 2);
        icon.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2,Align.center);
        Texture backgroundTexture = ResourcesManager.getTexture(FilePaths.BACKGROUND_TILE_ACCENT);
        TileBackground background = new TileBackground(backgroundTexture, 3, 2);
        Group group = new Group();
        group.addActor(icon);
        group.addActor(background);
        icon.setZIndex(background.getZIndex() + 1);
        stage = new Stage(new ScreenViewport());
        stage.addActor(group);
        Tween fadeTween = new FadeTween(group,0,1,3.5f);
        fadeTween.start();
        Tween scaleTween = new ScaleXYTween(icon,0.4f,1f,4f);
        scaleTween.start();
        float start = icon.getY(Align.center);
        float end = start + 300;
        Tween moveYTween = new MoveYTween(icon,start,end,8,Align.center);
        moveYTween.setEase(Ease.OUT_CUBIC);
        moveYTween.start();
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
    }

    @Override
    public void dispose() {
    }
}
