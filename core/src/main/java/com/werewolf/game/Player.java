package com.werewolf.game;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.io.Writer;
import com.message.tag.MessageTag;
import com.network.Client;
import com.tween.FadeTween;
import com.tween.Tween;
import com.werewolf.GameState;
import com.werewolf.GameTime;
import com.werewolf.game.role.Role;


public class Player {
    private int id;
    private String name;
    private final Avatar avatar;
    private Role role;

    private static Player mainPlayer;
    private Group root;


    //GamePlay
    private boolean canVote;
    private boolean isAlive;
    private boolean isProtected;
    private boolean canChat;

    public Player(int id, String name, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.isAlive = true;
        this.isProtected = false;
        this.canVote = false;
        this.canChat = true;
        this.root = new Group();
        avatar.getRoot().setScale(0.4f);
        root.addActor(avatar.getRoot());
    }

    public Player(PlayerInfo playerInfo) {
        this(playerInfo.getId(), playerInfo.getName(), playerInfo.getAvatar());
    }

    public int getId() {
        return id;
    }

    public void setSlot(int id) {
        try {
            Slot oldSlot = getMySlot();
            Slot newSlot = PlayerBoard.getInstance().getSlot(id);
            if (!newSlot.isEmpty() || oldSlot == newSlot) {
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

    public boolean isProtected() {
        return isProtected;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
        if (!isAlive) {
            canVote = false;
            canChat = true;
            Tween tweenFade = new FadeTween(avatar.getRoot(), 2, 0, 1);
            tweenFade.start();
            getMySlot().playDeadAnimation();

        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public void setCanChat(boolean canChat) {
        this.canChat = canChat;
    }

    public boolean canChat() {
        return canChat;
    }

    public String getName() {
        return name;
    }

    public static Player getMainPlayer() {
        return mainPlayer;
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

    public void vote(Player player) {
        getMySlot().vote(player);
    }

    public void unVote() {
        getMySlot().unVote();
    }
    public void selectPlayer(Player player) {

        if (!canVote) {
            return;
        }
        if (player.isMainPlayer()) {
            return;
        }
        if (!player.isAlive()) {
            return;
        }
        Writer writer = new Writer(5);
        writer.writeByte((byte) player.getId());
        Client.getInstance().send(MessageTag.SELECT_PLAYER, writer);
    }
}
