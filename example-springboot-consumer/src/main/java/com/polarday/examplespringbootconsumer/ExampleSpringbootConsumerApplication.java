package com.polarday.examplespringbootconsumer;

import com.polarday.pdrpc.springboot.starter.annotation.EnableRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpc(needServer = false)
public class ExampleSpringbootConsumerApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ExampleSpringbootConsumerApplication.class, args);
        Thread.sleep(100000);
    }

}
