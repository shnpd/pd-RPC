package com.polarday.pdrpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

public class ConfigUtils {

    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        // 加载配置文件
        Props props = new Props(configFileBuilder.toString());
        // 将配置文件中的属性映射到目标类的字段，仅处理配置文件中以prefix开头的属性
        return props.toBean(tClass, prefix);
    }
}
