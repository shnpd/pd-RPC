package com.polarday.example.provider;

import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.RpcApplication;
import com.polarday.pdrpc.config.RegistryConfig;
import com.polarday.pdrpc.config.RpcConfig;
import com.polarday.pdrpc.model.ServiceMetaInfo;
import com.polarday.pdrpc.registry.LocalRegistry;
import com.polarday.pdrpc.registry.Registry;
import com.polarday.pdrpc.registry.RegistryFactory;
import com.polarday.pdrpc.server.tcp.VertxTcpServer;

public class ProviderExample {

    public static void main(String[] args) {
        // rpc框架初始化
        RpcApplication.init();
        // 注册服务，将服务名与实现类绑定
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);
        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
