package com.werewolf.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Image icon;
    private Label labelName;
    private Player player;
    private Group root;
    public Slot(int id) {
        this.id = id;
        this.root = new Group();
        this.background = new Image(ResourcesManager.getTexture(FilePaths.BACKGROUND_AVATAR));
        this.background.setScaling(Scaling.fit);
        this.background.setSize(WIDTH,HEIGHT);
        this.root.addActor(background);
    }
    public void setPlayer(Player player) {
        this.player = player;
        player.setSlot(id);
        setLabelName(player.getName());
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
            player = null;
            if(labelName != null) {
                root.removeActor(labelName);
            }
        }
    }
    public void setIcon(Image icon) {
        if(this.icon != null) {
            root.removeActor(this.icon);
        }
        this.icon = icon;
        root.addActor(icon);
    }
    private void setLabelName(String name) {
        BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_BOLD);
        Color fontColor = Color.WHITE.cpy();
        if(player == Player.getMainPlayer()) {
            fontColor = Color.RED.cpy();
        }
        Label.LabelStyle labelStyle = new Label.LabelStyle(font,fontColor);
        Label label = new Label(id + " " + name,labelStyle);
        label.setAlignment(Align.center);
        label.setPosition(WIDTH / 2 - (name.length()+2) * 3.5f, HEIGHT - 20);
        if(this.labelName != null) {
            root.removeActor(this.labelName);
        }
        this.labelName = label;
        root.addActor(label);
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
