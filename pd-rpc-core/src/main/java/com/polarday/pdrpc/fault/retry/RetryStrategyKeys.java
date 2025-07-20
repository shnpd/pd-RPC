package com.polarday.pdrpc.fault.retry;

public interface RetryStrategyKeys {
    // 不重试
    String NO = "no";

    // 固定时间间隔重试
    String FIXED_INTERVAL = "fixedInterval";
}
