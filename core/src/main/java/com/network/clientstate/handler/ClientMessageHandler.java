package com.network.clientstate.handler;

import com.io.Reader;
import com.network.clientstate.state.ClientState;
import com.network.Client;

import java.util.HashMap;
import java.util.Map;


public abstract class ClientMessageHandler {
    public abstract void onEnter(Client client);
    public abstract void onMessage(Client client, byte tag, Reader reader);
    public abstract void onExit(Client client);

}
