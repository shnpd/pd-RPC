package com.polarday.pdrpc.fault.tolerant;

import com.polarday.pdrpc.model.RpcResponse;

import java.util.Map;

// 容错策略：降级处理
public class FailBackTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        //TODO：自行扩展，获取降级的服务并调用
        return null;
    }
}
