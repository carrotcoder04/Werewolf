package com.constant;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.werewolf.game.ItemType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FilePaths {
    public static final String FONT = "SJ  CCRichardStarkings.ttf";
    public static final String ICON_LAUNCHER = "ic_launcher_round.png";
    public static final String BACKGROUND_INTRO = "wolvesville_large.png";
    public static final String BACKGROUND_INFO_SCREEN = "wolvesville_large_night.png";
    public static final String BACKGROUND_ITEM = "Item/item_background.png";

    public static String getItemDirectory(ItemType itemType) {
        StringBuilder name = new StringBuilder(itemType.name().toLowerCase());
        name.setCharAt(0, Character.toUpperCase(name.charAt(0)));
        return "Item/" + name + "/";
    }
    public static ArrayList<String> getItemsFilePath(ItemType type) {
        String directory = getItemDirectory(type);
        FileHandle fileHandle = Gdx.files.internal(directory);
        FileHandle[] fileHandles = fileHandle.list();
        Arrays.sort(fileHandles, Comparator.comparing(FileHandle::name));
        ArrayList<String> files = new ArrayList<>();
        for (FileHandle handle : fileHandles) {
            files.add(handle.path());
        }
        return files;
    }
    public static String getIconTabPath(ItemType type) {
        return "Item/Tab/inventory_tab_" + type.name().toLowerCase() + ".png";
    }
}
