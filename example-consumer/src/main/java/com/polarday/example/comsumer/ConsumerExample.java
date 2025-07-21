package com.polarday.example.comsumer;

import com.polarday.example.common.model.User;
import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.bootstrap.ConsumerBootstrap;
import com.polarday.pdrpc.proxy.ServiceProxyFactory;

public class ConsumerExample {
    public static void main(String[] args) {
        // 服务消费者初始化
        ConsumerBootstrap.init();
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("polarday");
        // 调用
        User newUser = userService.getUser(user);
        System.out.println(newUser.getName());
    }
}
