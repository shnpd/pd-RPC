package com.polarday.example.provider;

import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.RpcApplication;
import com.polarday.pdrpc.registry.LocalRegistry;
import com.polarday.pdrpc.server.HttpServer;
import com.polarday.pdrpc.server.VertxHttpServer;

public class ProviderExample {

    public static void main(String[] args) {
        RpcApplication.init();
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务器
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
