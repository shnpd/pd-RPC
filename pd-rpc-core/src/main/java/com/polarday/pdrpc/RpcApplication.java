package com.polarday.pdrpc;

import com.polarday.pdrpc.config.RegistryConfig;
import com.polarday.pdrpc.config.RpcConfig;
import com.polarday.pdrpc.constant.RpcConstant;
import com.polarday.pdrpc.registry.Registry;
import com.polarday.pdrpc.registry.RegistryFactory;
import com.polarday.pdrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcApplication {
    // volatile解决指令重排问题
    private static volatile RpcConfig rpcConfig;

    // 框架初始化，支持传入自定义配置
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());

        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);
    }

    public static void init() {
        RpcConfig newRpcConfig;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e){
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }
    public static RpcConfig getRpcConfig() {
        // 双检锁单例模式
        // 第一次检查无锁，如果rpcConfig已存在则直接返回
        if (rpcConfig == null) {
            // 第二次检查有锁，确保在多线程环境下只初始化一次
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
