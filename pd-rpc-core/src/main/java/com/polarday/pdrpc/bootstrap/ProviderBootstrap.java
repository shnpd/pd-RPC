package com.polarday.pdrpc.bootstrap;

import com.polarday.pdrpc.RpcApplication;
import com.polarday.pdrpc.config.RegistryConfig;
import com.polarday.pdrpc.config.RpcConfig;
import com.polarday.pdrpc.model.ServiceMetaInfo;
import com.polarday.pdrpc.model.ServiceRegisterInfo;
import com.polarday.pdrpc.registry.LocalRegistry;
import com.polarday.pdrpc.registry.Registry;
import com.polarday.pdrpc.registry.RegistryFactory;
import com.polarday.pdrpc.server.tcp.VertxTcpServer;

import java.util.List;

// 服务提供者初始化
public class ProviderBootstrap {

    // 初始化
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // rpc框架初始化
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册，将服务名与实现类绑定
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());
            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
