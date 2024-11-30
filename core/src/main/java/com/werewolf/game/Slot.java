package com.werewolf.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Image icon;
    private Label labelName;
    private Player player;
    private Group root;
    private Image hand;
    private Label numVoteLabel;
    private Image voteTarget;
    private Label targetLabel;
    public Slot(int id) {
        this.id = id;
        this.root = new Group();
        initBackground();
        initLabelName();
        initHand();
        initNumVoteLabel();
        initVoteTarget();
        initTargetLabel();
        root.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               onClick();
           }
        });
    }
    private void onClick() {
        if(player == null) {
            return;
        }
        Player.getMainPlayer().selectPlayer(player);
    }
    private void initBackground() {
        this.background = new Image(ResourcesManager.getTexture(FilePaths.BACKGROUND_AVATAR));
        this.background.setScaling(Scaling.fit);
        this.background.setSize(WIDTH,HEIGHT);
        this.root.addActor(background);
    }

    private void initLabelName() {
        BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_BOLD);
        Color fontColor = Color.WHITE.cpy();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font,fontColor);
        labelName = new Label("",labelStyle);
        root.addActor(labelName);
    }

    private void initHand() {
        Sprite handSprite = new Sprite(ResourcesManager.getTexture(FilePaths.HAND_VOTE));
        this.hand = new Image(handSprite);
        hand.setScale(0.7f);
        hand.setRotation(-45);
        hand.setPosition(-20,130);
        hand.setVisible(false);
        root.addActor(hand);
    }
    private void initNumVoteLabel() {
        BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_BOLD);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.RED.cpy());
        numVoteLabel = new Label("", labelStyle);
        numVoteLabel.setPosition(12.5f,115.5f);
        numVoteLabel.setVisible(false);
        root.addActor(numVoteLabel);
    }
    private void initVoteTarget() {
        Sprite voteTargetSprite = new Sprite(ResourcesManager.getTexture(FilePaths.VOTE_TARGET));
        voteTarget = new Image(voteTargetSprite);
        voteTarget.setScale(0.5f);
        voteTarget.setX(18);
        voteTarget.setVisible(false);
        root.addActor(voteTarget);
    }
    private void initTargetLabel() {
        BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_BOLD);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE.cpy());
        targetLabel = new Label("", labelStyle);
        targetLabel.setPosition(50,9);
        targetLabel.setVisible(false);
        root.addActor(targetLabel);
    }
    public void setPlayer(Player player) {
        this.player = player;
        player.setSlot(id);
        setLabelName(player.getName());
        this.root.addActorAfter(background,player.getRoot());
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
                labelName.setText("");
            }
        }
    }
    public void setIcon(Image icon) {
        if(this.icon != null) {
            root.removeActor(this.icon);
        }
        this.icon = icon;
        root.addActor(icon);
        Tween fadeTween = new FadeTween(icon ,0,1,0.5f);
        fadeTween.start();
    }
    private void setLabelName(String name) {
        Color fontColor;
        if(player.isMainPlayer()) {
            fontColor = new Color(234/255f,96/255f,96/255f,1);
        }
        else {
            fontColor = Color.WHITE.cpy();
        }
        labelName.setText(id + " " + name);
        labelName.setPosition(WIDTH / 2 - (name.length()+2) * 3.5f, HEIGHT - 20);
        System.out.println(labelName.getText());
        labelName.getStyle().fontColor = fontColor;
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
    public void setNumVote(int numVote) {
        if(numVote == 0) {
            hand.setVisible(false);
            numVoteLabel.setVisible(false);
        }
        else {
            numVoteLabel.setVisible(true);
            hand.setVisible(true);
            numVoteLabel.setText(String.valueOf(numVote));
        }
    }
    public void vote(Player player) {
        voteTarget.setVisible(true);
        targetLabel.setVisible(true);
        targetLabel.setText(player.getId());
    }
    public void unVote() {
        voteTarget.setVisible(false);
        targetLabel.setVisible(false);
    }

    public void playDeadAnimation() {
        Color color = background.getColor();
        Color red = Color.RED.cpy();
        red.a = 0.5f;
        Tween lerpRedBackGround = new Tween(1f) {
            @Override
            protected void onUpdate(float value) {
                color.lerp(Color.RED.cpy(), value);
            }
        };
        lerpRedBackGround.onComplete(() -> {
            Tween lerpWhiteBackGround = new Tween(1f) {
                @Override
                protected void onUpdate(float value) {
                    color.lerp(Color.WHITE.cpy(), value);
                }
            };
            lerpWhiteBackGround.start();
        });
        lerpRedBackGround.start();
    }
}
