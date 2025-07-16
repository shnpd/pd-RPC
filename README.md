# pd-RPC

java手写rpc项目

项目结构：

example-common：公共模块，定义调用的接口和模型

example-consumer：消费者模块，调用common中的接口

example-provider：实现common中的接口，启动web服务器接受consumer的请求

pd-rpc-easy：rpc的核心实现，定义请求响应模型，生成服务代理供消费者调用，定义本地服务注册器将服务名与实现类绑定，定义序列化器，定义provider中使用的web服务器处理consumer的请求

