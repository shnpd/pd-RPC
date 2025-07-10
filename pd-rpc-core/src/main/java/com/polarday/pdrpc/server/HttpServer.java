package com.polarday.pdrpc.server;

// 定义统一的服务器启动方法，便于后续的扩展
public interface HttpServer {

    void doStart(int port);
}
