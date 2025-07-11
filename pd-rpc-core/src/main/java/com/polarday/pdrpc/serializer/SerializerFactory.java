package com.polarday.pdrpc.serializer;

import com.polarday.pdrpc.spi.SpiLoader;


public class SerializerFactory {

    // 加载接口
    static {
        SpiLoader.load(Serializer.class);
    }

    // 默认序列化器
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    // 获取实例
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }
}
