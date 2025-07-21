# pd-RPC

使用java开发的rpc框架，支持服务注册和发现、负载均衡、容错、重试等功能。

开发者引入pd-rpc-spring-boot-starter就可以方便的使用注解的方式实现服务注册和引用

## 框架图
![](https://cdn.jsdelivr.net/gh/shnpd/blog-pic@main/javastudy/%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

## 项目结构

- example-common：公共模块，定义调用的接口和模型
- example-consumer：服务消费者模块 
- example-provider：服务提供者模块 
- pd-rpc-easy：rpc的简易实现 
- pd-rpc-core: rpc的核心实现
  - bootstrap：启动类
  - config：配置类，保存全局配置
  - constant：常量类，保存全局常量
  - fault：错误处理
    - retry：重试机制
    - tolerant：容错机制
  - loadbalancer：负载均衡器
  - model：模型类，请求/响应模型，服务注册模型等
  - protocol：协议类，定义传输协议的格式，编码器和解码器
  - proxy：代理类，提供给消费者服务调用的代理
  - registry：注册中心，服务注册和发现
  - serializer：序列化器
  - server：rpc服务器，对rpc请求进行处理
  - spi：spi机制，实现spi的加载和扩展
  - utils：工具类，主要是加载配置文件
  - RpcApplication：提供rpc初始化的功能
- pd-rpc-spring-boot-starter：提供注解和自动配置，实现注解驱动的启动机制
- example-springboot-consumer：使用注解启动的服务消费者
- example-springboot-provider：使用注解启动的服务提供者

## 使用方式

在使用本框架时只需要用户选择好注册中心即可启用，目前本项目支持etcd和zookeeper两种注册中心。

开发者可以通过配置文件对注册中心进行配置，比如：
rpc.registryConfig.registry=zookeeper
rpc.registryConfig.address=localhost:2181

这里的底层实现是通过SPI机制加载的，在项目初始化时会加载配置文件，在发现要使用zookeeper作为注册中心时，会去查找注册中心接口的spi配置文件com.polarday.pdrpc.registry.Registry，找到zookeeper对应的实现类，然后实例化该类作为注册中心的实现。

除了注册中心之外，负载均衡器、序列化器等也可以通过SPI机制进行扩展，可以去到对应目录下的Keys接口中查询配置的key，然后采用类似的方式通过资源文件配置

目前支持的各个组件类型有：
- 注册中心
  - EtcdRegistry：etcd注册中心
  - ZookeeperRegistry：zookeeper注册中心
- 序列化器
  - JdkSerializer：JDK序列化器
  - JsonSerializer：JSON序列化器
  - KryoSerializer：Kryo序列化器
  - HessianSerializer：Hessian序列化器
- 负载均衡器
  - RoundRobinLoadBalancer：轮询负载均衡器
  - RandomLoadBalancer：随机负载均衡器
  - ConsistentHashLoadBalancer：一致性哈希负载均衡器
- 重试机制
  - NoRetryStrategy：不重试
  - FixedIntervalRetryStrategy：固定间隔重试
- 容错机制
  - FailFastTolerantStrategy：快速失败
  - FailSafeTolerantStrategy：静默处理