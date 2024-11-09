package com.network.clientstate.state;

import com.network.clientstate.handler.ClientInfoHandler;
import com.network.clientstate.handler.RoomHandler;
import com.network.clientstate.handler.ClientMessageHandler;

public enum ClientState {
    CLIENT_INFO_HANDLER(new ClientInfoHandler()),
    ROOM_HANDLER(new RoomHandler());
    private final ClientMessageHandler clientMessageHandler;
    ClientState(ClientMessageHandler clientMessageHandler) {
        this.clientMessageHandler = clientMessageHandler;
    }
    public ClientMessageHandler getClientMessageHandler() {
        return clientMessageHandler;
    }
}
