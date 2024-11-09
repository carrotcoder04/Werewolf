package com.werewolf.game.role;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.resources.ResourcesManager;
public class Role {
    private RoleInfo info;
    private Image icon;
    public Role(RoleInfo info){
        this.setInfo(info);
    }
    private void setInfo(RoleInfo info){
        this.info = info;
        this.icon = new Image(ResourcesManager.getTexture("Role/icon_" + info.name().toLowerCase() + "_filled_small.png"));
        this.icon.setScale(0.5f);
        this.icon.setPosition(70,8);
        this.icon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showRoleInfo();
            }
        });
    }
    private void showRoleInfo() {

    }
    public RoleInfo getInfo() {
        return this.info;
    }
    public Image getIcon() {
        return this.icon;
    }
}
