package com.polarday.pdrpc.config;

import lombok.Data;

@Data
public class RpcConfig {

    private String name = "pd-rpc";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;

    private boolean mock = false;

}
