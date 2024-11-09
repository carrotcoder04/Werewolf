package com.network.clientstate.handler;

import com.io.Reader;
import com.message.tag.MessageTag;
import com.network.Client;
import com.network.clientstate.state.ClientState;
import com.werewolf.InvokeOnMainThread;
import com.werewolf.game.PlayerInfo;
import com.werewolf.screen.ClientInfoScreen;


public class ClientInfoHandler extends ClientMessageHandler {
    @Override
    public void onEnter(Client client) {
    }

    @Override
    public void onMessage(Client client, byte tag, Reader reader) {
        switch (tag) {
            case MessageTag.ROOM_FULL:
                System.out.println("Room Full");
                break;
            case MessageTag.YOUR_INFO:
                InvokeOnMainThread.invoke(() -> {
                    ClientInfoScreen.getInstance().play(new PlayerInfo(reader));
                    client.setClientState(ClientState.ROOM_HANDLER);
                });
                break;
        }
    }

    @Override
    public void onExit(Client client) {

    }
}
