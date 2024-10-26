package com.scene2d.element;


import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


public class TextInputField extends TextField {
    public TextInputField() {
        super("",new TextField.TextFieldStyle());

    }
    public TextInputField(String text, Skin skin) {
        super(text, skin);
    }

    public TextInputField(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public TextInputField(String text, TextFieldStyle style) {
        super(text, style);
    }
}
