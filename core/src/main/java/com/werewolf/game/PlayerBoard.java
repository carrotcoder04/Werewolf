package com.werewolf.game;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.config.GameConfig;
import com.werewolf.game.role.RoleInfo;

import java.util.ArrayList;

public class PlayerBoard extends Table  {
    private final ArrayList<Slot> slots;
    private static PlayerBoard instance;
    private ArrayList<RoleInfo> roleInfos;
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
    public void setAllRoleInfos(ArrayList<RoleInfo> roleInfos) {
        this.roleInfos = roleInfos;
    }
    public void updateSlotEmpty(int index) {
        getSlot(index).removePlayer();
    }
    public void updateRoom(PlayerInfo playerInfo) {
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
