package com.network.clientstate.handler;

import com.io.Reader;
import com.message.tag.MessageTag;
import com.network.Client;
import com.werewolf.InvokeOnMainThread;
import com.werewolf.game.PlayerBoard;
import com.werewolf.game.PlayerInfo;

public class RoomHandler extends ClientMessageHandler {
    @Override
    public void onEnter(Client client) {
        client.send(MessageTag.JOIN_ROOM);
    }
    @Override
    public void onMessage(Client client, byte tag, Reader reader) {
        System.out.println("tag: " + tag + " reader: " + reader.getBuffer().length);
        switch (tag) {
            case MessageTag.UPDATE_PLAYER:
                InvokeOnMainThread.invoke(() -> {
                    PlayerInfo playerInfo = new PlayerInfo(reader);
                    PlayerBoard.getInstance().updatePlayer(playerInfo);
                });
                break;
        }
    }
    @Override
    public void onExit(Client client) {

    }
}
