package com.network;

import com.config.Config;
import com.config.ServerConfig;
import com.message.io.MessageReader;
import com.message.io.MessageWriter;
import com.message.structure.MessageStructure;
import com.message.structure.StringMessage;

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
    public Client() {
        try {
            ServerConfig serverConfig = Config.getServerConfig();
            socket = new Socket(serverConfig.getIpAddress(),serverConfig.getPort());
            in = socket.getInputStream();
            out = socket.getOutputStream();
            CompletableFuture.runAsync(this::readLoop);
        }
        catch (IOException e) {
            e.printStackTrace();
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
        StringMessage message = new StringMessage(reader);
        send(message);
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
    public void send(MessageStructure message) {
        send(message.getWriter());
    }
    public void sendAsync(MessageStructure message) {
        CompletableFuture.runAsync(() -> {
            send(message);
        });
    }
}
