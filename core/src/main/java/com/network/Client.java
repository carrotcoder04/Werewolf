package com.network;

import com.config.network.NetworkConfig;
import com.message.io.MessageReader;
import com.message.io.MessageWriter;
import com.clientstate.state.ClientState;
import com.clientstate.handler.ClientMessageHandler;
import com.message.data.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;


public class Client {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private ClientState clientState;
    private ClientMessageHandler stateHandler;
    public Client() {
    }
    public void connect() {
        try {
            socket = new Socket(NetworkConfig.IP_SERVER,NetworkConfig.PORT );
            in = socket.getInputStream();
            out = socket.getOutputStream();
            CompletableFuture.runAsync(this::readLoop);
            setClientState(ClientState.CLIENT_CONNECTED);
        }
        catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }
    private void readLoop() {
        byte[] buffer = new byte[1024];
        while (true) {
            try {
                int len = in.read(buffer);
                byte[] data = Arrays.copyOfRange(buffer, 0, len);
                receiveMessage(data);
            }
            catch (IOException e) {
                // e.printStackTrace();
                disconnect();
                break;
            }
        }
    }
    private void receiveMessage(byte[] data) {
        MessageReader reader = new MessageReader(data);
        receiveMessage(reader);
    }
    private void receiveMessage(MessageReader reader) {
        if(stateHandler != null) {
            byte tag = reader.readTag();
            stateHandler.onMessage(this,tag,reader);
        }
    }
    private void disconnect() {
        try {
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(byte[] data) {
        try {
            out.write(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void send(MessageWriter message) {
        send(message.getBuffer());
    }
    public void send(Message message) {
        send(message.getWriter());
    }
    public void sendAsync(Message message) {
        CompletableFuture.runAsync(() -> {
            send(message);
        });
    }
    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
        if(stateHandler != null) {
            stateHandler.onExit(this);
        }
        stateHandler = ClientMessageHandler.getStateHandler(clientState);
        stateHandler.onEnter(this);
    }
}
