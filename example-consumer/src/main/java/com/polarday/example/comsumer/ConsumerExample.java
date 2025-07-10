package com.polarday.example.comsumer;

import com.polarday.pdrpc.RpcApplication;
import com.polarday.pdrpc.config.RpcConfig;
import com.polarday.pdrpc.utils.ConfigUtils;

public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = RpcApplication.getRpcConfig();
        System.out.println(rpc);
    }
}
