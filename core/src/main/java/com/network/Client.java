package com.network;

import com.config.Config;
import com.config.ServerConfig;
import com.message.io.MessageReader;
import com.message.io.MessageWriter;

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
    }
    private void receiveMessage(MessageReader reader) {

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
}
