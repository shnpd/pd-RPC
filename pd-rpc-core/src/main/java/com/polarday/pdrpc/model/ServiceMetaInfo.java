package com.polarday.pdrpc.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
// 服务元信息（注册信息）
public class ServiceMetaInfo {

    // 服务名称
    private String serviceName;

    // 服务版本
    private String serviceVersion = "1.0";

    // 服务域名
    private String serviceHost;

    // 服务端口
    private Integer servicePort;

    // 服务分组
    private String serviceGroup = "default";

    // 获取服务键名
    public String getServiceKey() {
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    // 获取服务提供者键名
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    // 获取完整服务地址
    public String getServiceAddress() {
        if(!StrUtil.contains(serviceHost,"http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}
