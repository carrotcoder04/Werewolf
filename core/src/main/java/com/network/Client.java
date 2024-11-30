package com.network;

import com.config.NetworkConfig;
import com.io.Reader;
import com.io.Writer;
import com.network.clientstate.state.ClientState;
import com.network.clientstate.handler.ClientMessageHandler;
import com.serialization.Serializable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.CompletableFuture;


public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ClientMessageHandler stateHandler;
    private ClientState clientState;
    private static Client instance;
    public Client() {
        instance = this;
    }
    public void connect() {
        try {
            String serverIp = getServerIp();
            socket = new Socket(serverIp,NetworkConfig.PORT );
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            CompletableFuture.runAsync(this::readLoop);
            setClientState(ClientState.CLIENT_INFO_MESSAGE_HANDLER);
        }
        catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }
    private String getServerIp() {
        try {
            String url = "https://carrotcoder04.github.io/API/server.txt";
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void readLoop() {
        while (true) {
            try {
                int size = in.readShort();
                byte[] data = new byte[size];
                int len = 0;
                int byteRead = 0;
                while (byteRead < size) {
                    len = in.read(data, byteRead, size - byteRead);
                    if (len > 0) {
                        byteRead += len;
                    }
                    else {
                        throw new IOException();
                    }
                }
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
            out.writeShort(data.length);
            out.write(data);
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

    public void send(byte tag,Writer message) {
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
