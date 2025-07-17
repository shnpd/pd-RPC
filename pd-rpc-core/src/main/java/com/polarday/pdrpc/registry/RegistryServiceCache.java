package com.polarday.pdrpc.registry;

import com.polarday.pdrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 注册中心服务本地缓存
public class RegistryServiceCache {
    // 服务缓存
    Map<String, Map<String, ServiceMetaInfo>> serviceCache = new ConcurrentHashMap<>();

    // 写缓存
    void writeCache(String serviceKey, String serviceNodeKey, ServiceMetaInfo newServiceCache) {
        if (!serviceCache.containsKey(serviceKey)) {
            Map<String, ServiceMetaInfo> serviceMetaInfoMap = new ConcurrentHashMap<>();
            serviceMetaInfoMap.put(serviceNodeKey, newServiceCache);
            serviceCache.put(serviceKey, serviceMetaInfoMap);
        } else {
            serviceCache.get(serviceKey).put(serviceNodeKey, newServiceCache);
        }
    }

    // 读缓存
    List<ServiceMetaInfo> readCache(String serviceKey) {
        Map<String, ServiceMetaInfo> serviceMetaInfoMap = serviceCache.get(serviceKey);
        return serviceMetaInfoMap == null ? null : List.copyOf(serviceMetaInfoMap.values());
    }

    // 节点下线删除缓存
    void removeCache(String serviceKey, String serviceNodeKey) {
        serviceCache.get(serviceKey).remove(serviceNodeKey);
        // 如果该服务没有节点了，则删除该服务，这样在查询该服务的缓存时才会返回null说明没有缓存，否则返回空列表会导致判断错误
        if (serviceCache.get(serviceKey).isEmpty()) {
            serviceCache.remove(serviceKey);
        }
    }
}
