package com.polarday.pdrpc.fault.tolerant;

import com.polarday.pdrpc.model.RpcResponse;

import java.util.Map;

// 容错策略：转移到其他节点
public class FailOverTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // TODO：自行扩展，获取其他节点并调用
        return null;
    }
}
