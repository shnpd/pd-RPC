package com.polarday.pdrpc.springboot.starter.bootstrap;

import com.polarday.pdrpc.RpcApplication;
import com.polarday.pdrpc.config.RegistryConfig;
import com.polarday.pdrpc.config.RpcConfig;
import com.polarday.pdrpc.model.ServiceMetaInfo;
import com.polarday.pdrpc.registry.LocalRegistry;
import com.polarday.pdrpc.registry.Registry;
import com.polarday.pdrpc.registry.RegistryFactory;
import com.polarday.pdrpc.springboot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// 服务提供者启动类
@Slf4j
public class RpcProviderBootstrap implements BeanPostProcessor {

    // bean初始化后执行，注册服务
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        // 只处理 RpcService 注解
        if (rpcService != null) {
            // 需要注册服务
            // 1. 获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            // 默认值处理
            if (interfaceClass == void.class) {
                // 如果没有指定接口类，则使用实现类的第一个接口
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            // 2. 注册服务
            // 本地注册，将服务名与实现类绑定
            LocalRegistry.register(serviceName, beanClass);
            // 获取全局配置
            final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
                log.info("服务 {} 注册成功", serviceName);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
