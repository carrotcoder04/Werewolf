package com.werewolf.game.role;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.resources.ResourcesManager;
import com.resources.Texture2D;

public class Role {
    private RoleInfo info;
    public Role(RoleInfo info){
        this.setInfo(info);
    }
    private void setInfo(RoleInfo info){
        this.info = info;

    }
    protected void showRoleInfo() {
        System.out.println(info);
    }
    public RoleInfo getInfo() {
        return this.info;
    }
    public Image getIcon() {
        Texture2D texture2D = ResourcesManager.getTexture("Role/icon_" + info.name().toLowerCase() + "_filled_small.png");
        Image icon = new Image(new TextureRegion(texture2D));
        icon.setScale(0.3f);
        icon.setPosition(80,3);
        icon.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showRoleInfo();
            }
        });
        return icon;
    }
    public static Role createRole(RoleInfo info){
        switch(info){
            case SEER:
                return new Seer();
            case MAYOR:
                return new Mayor();
            case MEDIUM:
                return new Medium();
            case PRIEST:
                return new Priest();
            case JAILER:
                return new Jailer();
            case WITCH:
                return new Witch();
            case DOCTOR:
                return new Doctor();
            case LOUD_MOUTH:
                return new LoudMounth();
            case WEREWOLF:
                return new WereWolf();
            case SHADOW_WOLF:
                return new ShadowWolf();
            case JUNIOR_WEREWOLF:
                return new JuniorWereWolf();
            case WOLF_SEER:
                return new WolfSeer();
            case FOOL:
                return new Fool();
            case CORRUPTOR:
                return new Corruptor();
            case ARSONIST:
                return new Arsonist();
        }
        return null;
    }
}
