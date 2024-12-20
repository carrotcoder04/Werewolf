package com.werewolf.game;

import com.io.Reader;
import com.io.Writer;
import com.serialization.Serializable;

public class PlayerInfo implements Serializable {
    private int id;
    private String name;
    private Avatar avatar;
    public PlayerInfo(int id,String name, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
    public PlayerInfo(Reader reader) {
        deserialize(reader);
    }
    public String getName() {
        return name;
    }
    public Avatar getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void deserialize(Reader reader) {
        this.id = reader.nextInt();
        this.name = reader.nextString();
        this.avatar = new Avatar(reader);
    }

    @Override
    public Writer serialize() {
        Writer writer = new Writer(1024);
        writer.writeInt(id);
        writer.writeString(name);
        writer.write(avatar);
        return writer;
    }

    @Override
    public String toString() {
        return "PlayerInfo [id=" + id + ", name=" + name + ", avatar=" + avatar + "]";
    }
}
