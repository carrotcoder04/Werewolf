package com.werewolf.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import jdk.jfr.events.CertificateId;

public class TestScreen implements Screen {
    private Texture texture;
    private SpriteBatch batch;
    Color c = new Color(240f/255f, 240/255f, 240/255f, 1);
    Color d = new Color(140/255f, 140/255f, 140/255f, 1f);
    Sprite sprite;
    boolean on = false;

    @Override
    public void show() {
        batch = new SpriteBatch();
        int width = 200;
        int height = 50;
        int cornerRadius = 24;
        int borderThickness = 0;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // Bước 2: Đổ màu nền trong suốt
//        pixmap.setColor(0, 0, 0, 0);
//        pixmap.setColor(d);
//        pixmap.fillCircle(cornerRadius, cornerRadius, cornerRadius);
//        pixmap.fillCircle(width - cornerRadius-1, cornerRadius, cornerRadius);
//        pixmap.fillCircle(cornerRadius, height - cornerRadius-1, cornerRadius);
//        pixmap.fillCircle(width - cornerRadius-1, height - cornerRadius-1, cornerRadius);


        // Bước 3: Đổ màu cho hình chữ nhật
        pixmap.setColor(c);

        // Vẽ các phần thẳng của hình chữ nhật (trên, dưới, trái, phải)
        pixmap.fillRectangle(cornerRadius, 0, width - 2 * cornerRadius, height); // Phần giữa
        pixmap.fillRectangle(0, cornerRadius, width, height - 2 * cornerRadius); // Phần hai bên

        pixmap.fillCircle(cornerRadius, cornerRadius, cornerRadius-borderThickness);
        pixmap.fillCircle(width - cornerRadius-1, cornerRadius, cornerRadius-borderThickness);
        pixmap.fillCircle(cornerRadius, height - cornerRadius-1, cornerRadius-borderThickness);
        pixmap.fillCircle(width - cornerRadius-1, height - cornerRadius-1, cornerRadius-borderThickness);
//
//        pixmap.setColor(d);
//        for(int i=0;i<borderThickness;i++) {
//            pixmap.drawLine(cornerRadius, i, width - cornerRadius, i);
//            pixmap.drawLine(cornerRadius, height-i-1, width - cornerRadius, height-i-1);
//            pixmap.drawLine(i, cornerRadius,i , height - cornerRadius-1);
//            pixmap.drawLine(width - i-1, cornerRadius,width - i-1 , height - cornerRadius-1);
//        }
////         Chuyển Pixmap thành Texture để sử dụng trong game
        FileHandle fileHandle = Gdx.files.absolute("C:\\Users\\ASUS\\Desktop\\FolderManager\\JavaGame\\Werewolf\\assets\\background_text_field_name.png");
        PixmapIO.writePNG(fileHandle, pixmap);



        texture = new Texture(pixmap);
        sprite = new Sprite(texture);
        sprite.setPosition(550 / 2-width/2, 800 / 2);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 1, 1, 1);
        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            if(!on) {
                sprite.setColor(Color.GREEN);
            }
            else {
                sprite.setColor(c);
            }
            System.out.println(on);
            on = !on;

        }
        batch.begin();
        sprite.draw(batch,1);
        batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
