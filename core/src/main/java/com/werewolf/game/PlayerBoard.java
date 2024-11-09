package com.werewolf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.config.GameConfig;

import java.util.ArrayList;

public class PlayerBoard extends Table  {
    private final ArrayList<Slot> slots;
    private static PlayerBoard instance;
    public PlayerBoard() {
        super();
        instance = this;
        top();
        setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT/2);
        setPosition(0, GameConfig.SCREEN_HEIGHT/2);
        slots = new ArrayList<>();
        for(int i = 0; i < 12;i++) {
            addSlot(new Slot(i));
        }
        Player mainPlayer = Player.getMainPlayer();
        getSlot(mainPlayer.getId()).setPlayer(mainPlayer);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    public void addSlot(Slot slot) {
        slots.add(slot);
        add(slot.getRoot()).width(Slot.WIDTH).height(Slot.HEIGHT).pad(2);
        if(slots.size() % 4 == 0) {
            row();
        }
    }
    public static PlayerBoard getInstance() {
        return instance;
    }
    public void updatePlayer(PlayerInfo playerInfo) {
        int id = playerInfo.getId();
        if(!getSlot(id).isEmpty()) {
            return;
        }
        getSlot(id).setPlayer(new Player(playerInfo));
    }
    public Slot getSlot(int index) {
        return slots.get(index);
    }
}
