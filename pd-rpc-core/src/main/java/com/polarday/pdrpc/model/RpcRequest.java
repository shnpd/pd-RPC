package com.polarday.pdrpc.model;

import com.polarday.pdrpc.constant.RpcConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// rpc请求
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    // 服务名
    private String serviceName;

    // 服务版本
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    // 方法名
    private String methodName;

    // 参数类型列表
    private Class<?>[] parameterTypes;

    // 参数列表
    private Object[] args;
}
