package com.polarday.example.provider;

import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.registry.LocalRegistry;
import com.polarday.pdrpc.server.HttpServer;
import com.polarday.pdrpc.server.VertxHttpServer;

public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务器
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
