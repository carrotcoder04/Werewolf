package com.network.clientstate.state;

import com.network.clientstate.handler.ClientInfoMessageHandler;
import com.network.clientstate.handler.RoomMessageHandler;
import com.network.clientstate.handler.ClientMessageHandler;

public enum ClientState {
    CLIENT_INFO_MESSAGE_HANDLER(new ClientInfoMessageHandler()),
    ROOM_MESSAGE_HANDLER(new RoomMessageHandler());
    private final ClientMessageHandler clientMessageHandler;
    ClientState(ClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }
    public ClientMessageHandler getClientMessageHandler() {
        return clientMessageHandler;
    }
}
