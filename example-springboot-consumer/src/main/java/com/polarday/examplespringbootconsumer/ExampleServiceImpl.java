package com.polarday.examplespringbootconsumer;

import com.polarday.example.common.model.User;
import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("polarday");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
