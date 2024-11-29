package com.werewolf;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.tween.SizeHeightTween;
import com.tween.Tween;
import com.werewolf.game.role.RoleInfo;

import java.util.ArrayList;

public class RolePanel extends Table {
    private static RolePanel instance;
    private ArrayList<RoleInfo> roles;
    private ScrollPane scrollPane;
    private boolean isTweening;

    public RolePanel(Stage stage) {
        super();
        roles = new ArrayList<>();
        instance = this;
        initScrollPane(stage);
    }

    private void initScrollPane(Stage stage) {
        top();
        padTop(10);
        scrollPane = new ScrollPane(this, ResourcesManager.getSkin(FilePaths.ROLE_PANEL));
        scrollPane.setSize(550, 320);
        scrollPane.setScrollingDisabled(true, false);
        stage.addActor(scrollPane);
        scrollPane.setVisible(false);
    }

    public boolean isTweening() {
        return isTweening;
    }

    public void show() {
        if (isTweening) {
            return;
        }
        scrollPane.setVisible(true);
        Tween tween = new SizeHeightTween(scrollPane, 0, 280, 0.5f);
        isTweening = true;
        tween.onComplete(() -> {
            isTweening = false;
        });
        tween.start();
    }

    public void hide() {
        if (isTweening) {
            return;
        }
        Tween tween = new SizeHeightTween(scrollPane, 280, 0, 0.5f);
        isTweening = true;
        tween.onComplete(() -> {
            scrollPane.setVisible(false);
            isTweening = false;
        });
        tween.start();
    }

    @Override
    public boolean isVisible() {
        return scrollPane.isVisible();
    }

    public static RolePanel getInstance() {
        return instance;
    }
    public void setMainRole(RoleInfo role) {
        int index = roles.indexOf(role);
        int labelIndex = index * 5 + 1;
        Label label = (Label) getChildren().get(labelIndex);
        label.getStyle().fontColor = Color.RED.cpy();
    }
    public void setRoles(ArrayList<RoleInfo> roles) {
        clearChildren();
        this.roles.clear();
        for (RoleInfo role : roles) {
            addRole(role);
        }
    }

    public void addRole(RoleInfo role) {
        roles.add(role);
        Image icon = new Image(new Sprite(ResourcesManager.getTexture(FilePaths.getRolePath(role))));
        add(icon).width(icon.getWidth()).height(icon.getHeight()).center().row();
        BitmapFont nameLabelFont = ResourcesManager.getFont(FilePaths.ROBOTO_BOLD);
        Label.LabelStyle nameLabelStyle;
        nameLabelStyle = new Label.LabelStyle(nameLabelFont, Color.BLACK.cpy());
        Label nameLabel = new Label(role.getName(), nameLabelStyle);
        add(nameLabel).center().row();

        Label.LabelStyle teamLabelStyle = new Label.LabelStyle(nameLabelFont, Color.ORANGE.cpy());
        Label teamLabel = new Label("Phe: " + role.getTeam(), teamLabelStyle);
        add(teamLabel).center().row();
        Label.LabelStyle auraLabelStyle = new Label.LabelStyle(nameLabelFont, Color.GREEN.cpy());
        Label auraLabel = new Label("Hào quang: " + role.getAura(), auraLabelStyle);
        add(auraLabel).center().row();
        BitmapFont descriptionFont = ResourcesManager.getFont(FilePaths.ROBOTO_REGULAR);
        Label.LabelStyle descriptionLabelStyle = new Label.LabelStyle(descriptionFont, Color.BLACK);
        Label descriptionLabel = new Label(role.getDescription(), descriptionLabelStyle);
        descriptionLabel.setWrap(true);
        descriptionLabel.setAlignment(Align.center);
        add(descriptionLabel).width(260).center().padBottom(20).row();
    }
}
