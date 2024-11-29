package com.werewolf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.network.Client;
import com.resources.ResourcesManager;
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
        Client client = new Client();
        client.connect();
        setScreen(new SplashScreen());
    }

    @Override
    public void render() {
        try {
            TweenController.update(Gdx.graphics.getDeltaTime());
            InvokeOnMainThread.update();
            super.render();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MainGame getInstance() {
        return instance;
    }

    @Override
    public void dispose() {
        ResourcesManager.dispose();
    }
}
