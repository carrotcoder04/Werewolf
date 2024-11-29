package com.werewolf.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.resources.ResourcesManager;
import com.resources.Texture2D;
import com.werewolf.game.role.Role;


public class Player {
    private int id;
    private String name;
    private final Avatar avatar;
    private Role role;
    private boolean isAlive;
    private boolean isProtected;
    private static Player mainPlayer;
    private Group root;
    public Player(int id,String name,Avatar avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.isAlive = true;
        this.isProtected = false;
        this.root = new Group();
        avatar.getRoot().setScale(0.4f);
        root.addActor(avatar.getRoot());
    }
    public Player(PlayerInfo playerInfo) {
        this(playerInfo.getId(),playerInfo.getName(),playerInfo.getAvatar());
    }
    public int getId() {
        return id;
    }
    public void setSlot(int id) {
        try {
            Slot oldSlot = getMySlot();
            Slot newSlot = PlayerBoard.getInstance().getSlot(id);
            if(!newSlot.isEmpty() || oldSlot == newSlot) {
                return;
            }
            oldSlot.removePlayer();
            this.id = id;
            newSlot.setPlayer(this);
        }
        catch (IndexOutOfBoundsException ignore) {
        }
    }
    public void setRole(Role role) {
        this.role = role;
        getMySlot().setIcon(role.getIcon());
    }
    public Slot getMySlot() {
        return PlayerBoard.getInstance().getSlot(this.id);
    }
    public Role getRole() {
        return role;
    }
    public Group getRoot() {
        return root;
    }
    public Avatar getAvatar() {
        return avatar;
    }
    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public String getName() {
        return name;
    }

    public static Player getMainPlayer() {
        return mainPlayer;
    }
    public void takeDamage() {
        if(isProtected) {
            isProtected = false;
        }
        else {
            isAlive = false;
        }
    }
    public PlayerInfo getPlayerInfo() {
        return new PlayerInfo(id, name, avatar);
    }
    public static void setMainPlayer(Player player) {
        mainPlayer = player;
    }
    public boolean isMainPlayer() {
        return mainPlayer == this;
    }
}
