package com.polarday.example.comsumer;

import com.polarday.example.common.model.User;
import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.proxy.ServiceProxyFactory;

public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("polarday");

        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println("返回的用户名：" + newUser.getName());
        } else {
            System.out.println("未能获取用户信息");
        }
    }
}
