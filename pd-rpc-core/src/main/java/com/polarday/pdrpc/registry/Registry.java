package com.polarday.pdrpc.registry;

import com.polarday.pdrpc.config.RegistryConfig;
import com.polarday.pdrpc.model.ServiceMetaInfo;

import java.util.List;

// 注册中心接口，后续可以实现多种不同的注册中心，使用spi机制动态加载
public interface Registry {

    // 初始化
    void init(RegistryConfig registryConfig);

    // 注册服务（服务提供者）
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    // 注销服务（服务提供者）
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    // 服务发现（服务消费者）
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    // 服务销毁
    void destroy();

    // 心跳检测（服务提供者）
    void heartBeat();
}
