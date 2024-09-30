package com.config;

public class ServerConfig {
    private final String ipAddress;
    private final int port;
    ServerConfig(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public int getPort() {
        return port;
    }
}
