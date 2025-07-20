package com.polarday.pdrpc.fault.retry;

import com.polarday.pdrpc.model.RpcResponse;
import org.junit.Test;

public class RetryStrategyTest {
    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    @Test
    public void doRetry(){
        try{
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("执行一次");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println("重试结果：" + rpcResponse);
        } catch (Exception e){
            System.out.println("重试失败");
            e.printStackTrace();
        }
    }
}
