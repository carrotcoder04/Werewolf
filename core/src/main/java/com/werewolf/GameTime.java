package com.werewolf;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.werewolf.screen.RoomScreen;

public class GameTime {
    private static GameTime instance;
    private GameState state;
    private Label infoLabel;
    private GameTime() {

    }
    public GameState getState() {
        return state;
    }


    public void setState(GameState state) {
        this.state = state;
        RoomScreen.getInstance().setThemeColorByState(state);
    }
    public static GameTime getInstance() {
        if (instance == null) {
            instance = new GameTime();
        }
        return instance;
    }
}
