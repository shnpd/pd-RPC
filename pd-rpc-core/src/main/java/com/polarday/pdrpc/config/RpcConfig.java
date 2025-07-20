package com.polarday.pdrpc.config;

import com.polarday.pdrpc.fault.retry.RetryStrategyKeys;
import com.polarday.pdrpc.fault.tolerant.TolerantStrategyKeys;
import com.polarday.pdrpc.loadbalancer.LoadBalancerKeys;
import com.polarday.pdrpc.serializer.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {

    private String name = "pd-rpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;

    private boolean mock = false;

    // 序列化器配置
    private String serializer = SerializerKeys.JDK;

    // 注册中心配置
    private RegistryConfig registryConfig = new RegistryConfig();

    // 负载均衡器配置
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    // 重试策略
    private String retryStrategy = RetryStrategyKeys.FIXED_INTERVAL;

    // 容错策略
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

}
