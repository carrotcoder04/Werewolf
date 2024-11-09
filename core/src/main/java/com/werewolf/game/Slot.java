package com.werewolf.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.tween.FadeTween;
import com.tween.MoveYTween;
import com.tween.Tween;

public class Slot {
    public static final int WIDTH = 108;
    public static final int HEIGHT = 172;
    private int id;
    private Image background;
    private Player player;
    private Group root;
    public Slot(int id) {
        this.id = id;
        this.root = new Group();
        this.background = new Image(ResourcesManager.getTexture(FilePaths.BACKGROUND_AVATAR));
        this.background.setScaling(Scaling.fit);
        this.background.setSize(WIDTH,HEIGHT);
        this.root.addActor(background);
        this.root.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Player.getMainPlayer().setSlot(id);
            }
        });
    }
    public void setPlayer(Player player) {
        this.player = player;
        player.setSlot(id);
        this.root.addActor(player.getRoot());
        player.getRoot().setX(WIDTH / 2);
        Tween fadeTween = new FadeTween(player.getRoot(), 0, 1, 0.3f);
        Tween moveYTween = new MoveYTween(player.getRoot(), WIDTH / 2, 0, 0.3f, Align.left);
        fadeTween.start();
        moveYTween.start();
    }
    public void removePlayer() {
        if(player != null) {
            root.removeActor(player.getRoot());
        }
    }
    public boolean isEmpty() {
        return player == null;
    }
    public Player getPlayer() {
        return player;
    }
    public Group getRoot() {
        return root;
    }
}
