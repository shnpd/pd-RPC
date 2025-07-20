package com.polarday.pdrpc.fault.tolerant;

import com.polarday.pdrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

// 容错策略：静默处理（正常返回，好像没有出现过报错）
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("容错策略：静默处理，异常信息：{}", e.getMessage());
        return new RpcResponse();
    }
}
