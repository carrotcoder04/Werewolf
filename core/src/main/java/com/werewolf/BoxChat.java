package com.werewolf;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.constant.FilePaths;
import com.io.Writer;
import com.message.tag.MessageTag;
import com.network.Client;
import com.resources.ResourcesManager;
import com.werewolf.game.Player;

public class BoxChat {
    private Table table;
    private ScrollPane scrollPane;
    private TextField chatText;
    private Skin labelSkin;
    private Group root;
    private static BoxChat instance;
    public BoxChat() {
        instance = this;
        table = new Table();
        scrollPane = new ScrollPane(this.table, ResourcesManager.getSkin(FilePaths.BOX_CHAT));
        table.top().left().padLeft(10).padTop(10).padRight(10).padBottom(10);
        scrollPane.setY(60);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setColor(new Color(0.85f, 0.85f, 0.85f, 0.8f));
        scrollPane.setSize(550, 270);
        chatText = new TextField("", ResourcesManager.getSkin(FilePaths.CHAT_TEXT_FIELD));
        chatText.setY(5);
        chatText.getStyle().background.setLeftWidth(10);
        chatText.setSize(550, 50);
        chatText.setMaxLength(60);
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
                    String text = chatText.getText();
                    if(!text.isEmpty()) {
                        Writer writer = new Writer();
                        writer.writeString(text);
                        Client.getInstance().send(MessageTag.CHAT,writer);
                        addText(Player.getMainPlayer(), text);
                        chatText.setText("");
                    }
                }
                return true;
            }
        });
        labelSkin = ResourcesManager.getSkin(FilePaths.CHAT_LABEL);
        this.root = new Group();
        this.root.addActor(this.scrollPane);
        this.root.addActor(this.chatText);
    }
    public void addText(Player player,String text) {
        addText(player.getId() + " "  + player.getName(),text, !player.isAlive(), text.contains(String.valueOf(Player.getMainPlayer().getId())),false);
    }
    public void addNotification(String text) {
        addText("Thông báo",text,false,false,true);
    }
    public static BoxChat getInstance() {
        return instance;
    }
    private void addText(String name,String text,boolean isItalic,boolean isHightLight,boolean isNotification) {
        Label.LabelStyle labelStyle;
        if(isItalic){
            BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_ITALIC);
            labelStyle = new Label.LabelStyle(font, Color.BLACK.cpy());
        }
        else {
            BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_REGULAR);
            labelStyle = new Label.LabelStyle(font, Color.BLACK.cpy());
        }
        if(isNotification) {
            labelStyle.font = ResourcesManager.getFont(FilePaths.ROBOTO_BOLD);
            labelStyle.fontColor = Color.RED.cpy();
        }
        if(isHightLight) {
            Sprite sprite = new Sprite(ResourcesManager.getOnePixelTexture());
            sprite.setColor(new Color(195/255f, 9/255f, 50/255f, 0.25f));
            SpriteDrawable background = new SpriteDrawable(sprite);
            background.setLeftWidth(3);
            labelStyle.background = background;
        }
        BitmapFont font = ResourcesManager.getFont(FilePaths.ROBOTO_BOLD);
        Label.LabelStyle nameLabelStyle = new Label.LabelStyle(font, Color.BLACK.cpy());
        Label nameLabel = new Label(name + ": ",nameLabelStyle);
        Label label = new Label(text, labelStyle);
        table.add(nameLabel).padBottom(3).left();
        table.add(label).padBottom(3).expandX().left().fillX().row();
    }

    public Group getRoot() {
        return root;
    }
}
