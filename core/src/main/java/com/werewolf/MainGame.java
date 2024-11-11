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
    private FileHandle logFile;
    public MainGame() {
        instance = this;
    }
    @Override
    public void create() {
        logFile = Gdx.files.absolute("C:\\Users\\ASUS\\Desktop\\FolderManager\\JavaGame\\Werewolf\\log.txt");
        Client client = new Client();
        client.connect();
        setScreen(new SplashScreen());
    }
    public void log(String str) {
        logFile.writeString(str + "\n", true);

    }
    @Override
    public void render() {
        try {
            TweenController.update(Gdx.graphics.getDeltaTime());
            InvokeOnMainThread.update();
            super.render();
        }
        catch (Exception e) {
            logFile.writeString(e + "\n", true);
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
