package com.polarday.pdrpc.loadbalancer;

import com.polarday.pdrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

// 负载均衡器（服务消费者使用）
public interface LoadBalancer {

    // 选择服务调用
    ServiceMetaInfo select(Map<String,Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
