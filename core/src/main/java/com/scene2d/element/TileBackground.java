package com.scene2d.element;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TileBackground extends Actor {
    private Sprite tileSprite;
    private int tileWidth;
    private int tileHeight;
    private int rows;
    private int columns;

    public TileBackground(Sprite tileTexture, int rows, int columns) {
        this.tileSprite = tileTexture;
        this.rows = rows;
        this.columns = columns;
        this.tileWidth = tileTexture.getRegionWidth();
        this.tileHeight = tileTexture.getRegionHeight();
        setSize(columns * tileWidth, rows * tileHeight);
    }
    public TileBackground(Texture texture, int rows, int columns ) {
        this(new Sprite(texture),rows,columns);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        float x = getX();
        float y = getY();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                batch.draw(tileSprite,x+col * tileWidth, y + row * tileHeight);
            }
        }
    }
    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        this.tileWidth = (int)(tileSprite.getRegionWidth() * scaleX);
        this.tileHeight = (int)(tileSprite.getRegionHeight() * scaleY);
        tileSprite.setSize(tileWidth, tileHeight);
    }

    @Override
    public void setScale(float scaleXY) {
        this.setScale(scaleXY, scaleXY);
    }

    @Override
    public void setScaleX(float scaleX) {
        this.setScale(scaleX,getScaleY());
    }
    @Override
    public void setScaleY(float scaleY) {
        this.setScale(getScaleX(),scaleY);
    }
}
