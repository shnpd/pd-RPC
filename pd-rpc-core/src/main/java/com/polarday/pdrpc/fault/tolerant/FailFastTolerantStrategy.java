package com.polarday.pdrpc.fault.tolerant;

import com.polarday.pdrpc.model.RpcResponse;

import java.util.Map;

// 容错策略：快速失败（立刻通知外层调用方）
public class FailFastTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
