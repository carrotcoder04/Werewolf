package com.werewolf;

import com.badlogic.gdx.Screen;
import com.network.Client;

/** First screen of the application. Displayed after the application is created. */
public class MainScreen implements Screen {
    @Override
    public void show() {
        // Prepare your screen here.
        Client client = new Client();
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
