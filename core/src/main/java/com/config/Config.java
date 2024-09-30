package com.config;


public class Config {
    public static final int MAX_BUFFER_WRITER = 256;



    private static ServerConfig serverConfig;
    public static ServerConfig getServerConfig() {
        if(serverConfig == null) {
            serverConfig = new ServerConfig("localhost",8080);
        }
        return serverConfig;
    }
}
