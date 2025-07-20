package com.polarday.pdrpc.loadbalancer;

import com.polarday.pdrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// 一致性哈希负载均衡器
public class ConsistentHashLoadBalancer implements LoadBalancer {

    // 一致性哈希环
    private final TreeMap<Integer, ServiceMetaInfo> vituralNodes = new TreeMap<>();

    // 虚拟节点的数量
    private static final int VIRTUAL_NODE_COUNT = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (serviceMetaInfoList.isEmpty()) {
            return null;
        }

        // 构建虚拟节点环
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            for (int i = 0; i < VIRTUAL_NODE_COUNT; i++) {
                String nodeKey = serviceMetaInfo.getServiceAddress() + "#" + i;
                int hash = nodeKey.hashCode();
                vituralNodes.put(hash, serviceMetaInfo);
            }
        }

        // 获取调用请求的 hash 值
        int reqHash = requestParams.hashCode();

        // 选择最接近且大于等于调用请求 hash 的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = vituralNodes.ceilingEntry(reqHash);
        if (entry == null) {
            // 如果没有找到大于等于的节点，则返回环中的第一个节点
            entry = vituralNodes.firstEntry();
        }
        return entry.getValue();
    }
}
