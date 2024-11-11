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
        switch (tag) {
            case MessageTag.UPDATE_ROOM:
                InvokeOnMainThread.invoke(() -> {
                    PlayerInfo playerInfo = new PlayerInfo(reader);
                    PlayerBoard.getInstance().updateRoom(playerInfo);
                });
                break;
            case MessageTag.UPDATE_SLOT_EMPTY:
                int slotId = reader.nextInt();
                InvokeOnMainThread.invoke(() -> {
                    PlayerBoard.getInstance().updateSlotEmpty(slotId);
                });
        }
    }
    @Override
    public void onExit(Client client) {

    }
}
