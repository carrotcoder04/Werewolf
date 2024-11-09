package com.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Texture2D extends Texture {
    private final String path;

    public Texture2D(String internalPath) {
        super(internalPath);
        this.path = internalPath;
    }
    public Texture2D(FileHandle file) {
        super(file);
        this.path = file.path();
    }
    public String getPath() {
        return path;
    }
    public Texture2D(FileHandle file, boolean useMipMaps) {
        super(file, useMipMaps);
        this.path = file.path();
    }

    @Override
    public void dispose() {
        if(path != null) {
            ResourcesManager.removeTexture(path);
        }
        super.dispose();
    }
}
