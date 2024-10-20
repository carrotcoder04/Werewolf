package com.clientstate.handler;

import com.message.data.TextMessage;
import com.message.io.MessageReader;
import com.message.tag.MessageTag;
import com.network.Client;


public class ClientConnected extends ClientMessageHandler {
    @Override
    public void onEnter(Client client) {
    }

    @Override
    public void onMessage(Client client,byte tag,MessageReader reader) {
        switch (tag) {
            case MessageTag.TEXT_MESSAGE:
                TextMessage textMessage = new TextMessage(tag,null,reader);
                System.out.println(textMessage.getData());
                break;
            default:
                break;
        }
    }

    @Override
    public void onExit(Client client) {

    }
}
