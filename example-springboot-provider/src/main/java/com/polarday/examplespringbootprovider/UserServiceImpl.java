package com.polarday.examplespringbootprovider;

import com.polarday.example.common.model.User;
import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
