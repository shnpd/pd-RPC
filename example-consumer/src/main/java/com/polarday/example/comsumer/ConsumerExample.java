package com.polarday.example.comsumer;

import com.polarday.example.common.model.User;
import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.proxy.ServiceProxyFactory;

public class ConsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("polarday");
        User newUser = userService.getUser(user);
        System.out.println(newUser.getName());
    }
}
