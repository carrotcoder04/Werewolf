package com.constant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.werewolf.game.inventory.ItemType;
import com.werewolf.game.role.RoleInfo;

import java.util.ArrayList;

public class FilePaths {
    public static final String FONT = "SJ  CCRichardStarkings.ttf";
    public static final String ICON_LAUNCHER = "ic_launcher_round.png";
    public static final String BACKGROUND_INTRO = "wolvesville_large.png";
    public static final String BACKGROUND_INFO_SCREEN = "wolvesville_large_night.png";
    public static final String BACKGROUND_ITEM = "Item/item_background.png";
    public static final String BUTTON_PLAY = "Skin/Button/button_play.json";
    public static final String BUTTON_ROLE = "Skin/Button/button_role.json";
    public static final String BACKGROUND_AVATAR = "background_avatar.png";
    public static final String TEXT_FIELD_NAME = "Skin/TextField/text_field_name.json";
    public static final String BOX_CHAT = "Skin/ScrollPane/box_chat.json";
    public static final String CHAT_TEXT_FIELD = "Skin/TextField/chat_text_field.json";

    public static final String ROBOTO_BOLD = "Font/Roboto-Bold.ttf";
    public static final String ROBOTO_ITALIC = "Font/Roboto-Italic.ttf";
    public static final String ROBOTO_REGULAR = "Font/Roboto-Regular.ttf";
    public static final String ROLE_PANEL = "Skin/ScrollPane/role_panel.json";
    public static final String HAND_VOTE = "vote_day.png";
    public static final String VOTE_TARGET = "vote_day_voter_skin_2.png";
    private static final String[] ASSETS_PATHS;
    static {
        FileHandle assets = Gdx.files.internal("assets.txt");
        String text = assets.readString();
        ASSETS_PATHS = text.split("\n");
    }


    public static String getRolePath(RoleInfo role) {
        return "Role/icon_" + role.name().toLowerCase() + "_filled_small.png";
    }
    public static String getAssetPath(int index) {
        return ASSETS_PATHS[index];
    }
    public static String getItemDirectory(ItemType itemType) {
        StringBuilder name = new StringBuilder(itemType.name().toLowerCase());
        name.setCharAt(0, Character.toUpperCase(name.charAt(0)));
        return "Item/" + name + "/";
    }
    public static ArrayList<String> getItemsFilePath(ItemType type) {
        String directory = getItemDirectory(type);
        ArrayList<String> files = new ArrayList<>();
        boolean start = false;
        for(int i=1;i<ASSETS_PATHS.length;i++) {
            if(ASSETS_PATHS[i].startsWith(directory)) {
                files.add(ASSETS_PATHS[i]);
                start = true;
            }
            else {
                if(start) {
                   break;
                }
            }
        }
        return files;
    }
    public static String getIconTabPath(ItemType type) {
        return "Item/Tab/inventory_tab_" + type.name().toLowerCase() + ".png";
    }
}
