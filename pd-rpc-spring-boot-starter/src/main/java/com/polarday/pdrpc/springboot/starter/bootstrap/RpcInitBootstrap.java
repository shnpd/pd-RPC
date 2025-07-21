package com.polarday.pdrpc.springboot.starter.bootstrap;

import com.polarday.pdrpc.RpcApplication;
import com.polarday.pdrpc.config.RpcConfig;
import com.polarday.pdrpc.server.tcp.VertxTcpServer;
import com.polarday.pdrpc.springboot.starter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

// rpc框架启动
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {

    // Spring 初始化时执行，初始化 RPC 框架
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取 EnableRpc 注解的属性值
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName())
                .get("needServer");

        // RPC 框架初始化
        RpcApplication.init();

        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        } else {
            log.info("不启动 server");
        }
    }
}
