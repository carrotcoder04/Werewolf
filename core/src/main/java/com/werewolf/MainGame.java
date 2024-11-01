package com.werewolf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.resources.ResourcesManager;
import com.werewolf.screen.InfoUserScreen;
import com.tween.TweenController;
import com.werewolf.screen.SplashScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {
    private static MainGame instance;
    public MainGame() {
        instance = this;
    }
    @Override
    public void create() {
        setScreen(new SplashScreen());
    }
    @Override
    public void render() {
        TweenController.update(Gdx.graphics.getDeltaTime());
        super.render();
    }

    public static MainGame getInstance() {
        return instance;
    }

    @Override
    public void dispose() {
        ResourcesManager.dispose();
    }
}
