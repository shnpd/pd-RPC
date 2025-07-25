package com.polarday.pdrpc.fault.retry;

import com.polarday.pdrpc.spi.SpiLoader;

public class RetryStrategyFactory {
    static {
        SpiLoader.load(RetryStrategy.class);
    }

    // 默认重试器
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    // 获取实例
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
