package com.polarday.pdrpc.fault.retry;

import com.polarday.pdrpc.model.RpcResponse;

import java.util.concurrent.Callable;

// 重试策略
public interface RetryStrategy {
    // 重试
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
