package com.constant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.werewolf.MainGame;
import com.werewolf.game.inventory.ItemType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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


    private static final String[] ASSETS_PATHS;
    static {
        FileHandle assets = Gdx.files.internal("assets.txt");
        String text = assets.readString();
        ASSETS_PATHS = text.split("\n");
    }
    public static int getIndexOfAsset(String path) {
        return Arrays.binarySearch(ASSETS_PATHS,1,ASSETS_PATHS.length, path);
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
