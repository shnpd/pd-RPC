package com.polarday.example.comsumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.polarday.example.common.model.User;
import com.polarday.example.common.service.UserService;
import com.polarday.pdrpc.model.RpcRequest;
import com.polarday.pdrpc.model.RpcResponse;
import com.polarday.pdrpc.serializer.JdkSerializer;
import com.polarday.pdrpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



/// /静态代理
//public class UserServiceProxy implements UserService {
//
//    @Override
//    public User getUser(User user) {
//        Serializer serializer = new JdkSerializer();
//
//        RpcRequest rpcRequest = RpcRequest.builder()
//                .serviceName(UserService.class.getName())
//                .methodName("getUser")
//                .parameterTypes(new Class[]{User.class})
//                .args(new Object[]{user})
//                .build();
//        try {
//            // 序列化请求
//            byte[] bodyBytes = serializer.serialize(rpcRequest);
//            byte[] result;
//            // 发送HTTP请求
//            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
//                    .body(bodyBytes)
//                    .execute()) {
//                result = httpResponse.bodyBytes();
//            }
//            // 反序列化响应
//            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
//            return (User) rpcResponse.getData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
