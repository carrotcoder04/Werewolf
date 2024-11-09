package com.network;

import com.config.NetworkConfig;
import com.io.Reader;
import com.io.Writer;
import com.network.clientstate.state.ClientState;
import com.network.clientstate.handler.ClientMessageHandler;
import com.serialization.Serializable;

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
    private ClientMessageHandler stateHandler;
    private ClientState clientState;
    private static Client instance;
    public Client() {
        instance = this;
    }
    public void connect() {
        try {
            socket = new Socket(NetworkConfig.IP_SERVER,NetworkConfig.PORT );
            in = socket.getInputStream();
            out = socket.getOutputStream();
            CompletableFuture.runAsync(this::readLoop);
            setClientState(ClientState.CLIENT_INFO_HANDLER);
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
                System.out.println("len: " + len);
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
        Reader reader = new Reader(data);
        receiveMessage(reader);
    }
    private void receiveMessage(Reader reader) {
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
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send(byte tag) {
        byte[] buffer = new byte[1];
        buffer[0] = tag;
        send(buffer);
    }

    private void send(byte tag,Writer message) {
        message.writeTag(tag);
        send(message.getBuffer());
    }
    public void send(byte tag,Serializable message) {
        send(tag,message.serialize());
    }
    public void sendAsync(byte tag,Serializable message) {
        CompletableFuture.runAsync(() -> {
            send(tag,message);
        });
    }
    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
        if(stateHandler != null) {
            stateHandler.onExit(this);
        }
        stateHandler = clientState.getClientMessageHandler();
        stateHandler.onEnter(this);
    }
    public static Client getInstance() {
        return instance;
    }
}
