package com.werewolf;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.constant.FilePaths;
import com.resources.ResourcesManager;
import com.werewolf.game.Player;

public class BoxChat {
    private Table table;
    private ScrollPane scrollPane;
    private TextField chatText;
    private Skin labelSkin;
    private Group root;
    public BoxChat() {
        table = new Table();
        scrollPane = new ScrollPane(this.table, ResourcesManager.getSkin(FilePaths.BOX_CHAT));
        table.top().left().padLeft(10).padTop(10).padRight(10);
        scrollPane.setY(60);
        scrollPane.setColor(new Color(0.75f, 0.75f, 0.75f, 1f));
        scrollPane.setSize(550, 270);
        chatText = new TextField("", ResourcesManager.getSkin(FilePaths.CHAT_TEXT_FIELD));
        chatText.setY(5);
        chatText.getStyle().background.setLeftWidth(10);
        chatText.setSize(550, 50);
        chatText.setMessageText("Nhập tin nhắn...");
        chatText.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Stage stage = chatText.getStage();
                if (stage == null) {
                    return true;
                }
                Actor actor = stage.hit(x, y, true);
                if (chatText != actor) {
                    stage.unfocus(chatText);
                }
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    addText(" " + Player.getMainPlayer().getName() + ": " + chatText.getText(),false);
                    chatText.setText("");
                }
                return true;
            }
        });
        labelSkin = ResourcesManager.getSkin(FilePaths.CHAT_LABEL);
        this.root = new Group();
        this.root.addActor(this.scrollPane);
        this.root.addActor(this.chatText);
    }
    private void addText(String text,boolean isItalic) {
        Label label;
        if(isItalic){
            label = new Label(text,labelSkin.get("italic", Label.LabelStyle.class));
        }
        else {
            label = new Label(text, labelSkin);
        }
        table.add(label).padBottom(4).expandX().fillX().row();
    }
    public Group getRoot() {
        return root;
    }
}
