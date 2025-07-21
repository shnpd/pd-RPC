package com.polarday.pdrpc.springboot.starter.bootstrap;

import com.polarday.pdrpc.proxy.ServiceProxyFactory;
import com.polarday.pdrpc.springboot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

// Rpc服务消费者启动
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {

    // Bean初始化后执行，注入服务
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        // 遍历对象的所有属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            // 找到 RpcReference 注解的属性
            if (rpcReference != null) {
                // 1. 获取服务接口类
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if (interfaceClass == void.class) {
                    // 如果没有指定接口类，则使用属性的类型
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                // 2. 获取代理对象
                Object proxyObject = ServiceProxyFactory.getProxy(interfaceClass);
                try {
                    // 3. 将代理对象注入到属性中
                    field.set(bean, proxyObject);
                    field.setAccessible(false);
                    log.info("将服务代理对象注入到字段: {}.{}", beanClass.getName(), field.getName());

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败: " + field.getName(), e);
                }

            }
        }

        return bean;
    }
}
