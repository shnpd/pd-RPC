package com.polarday.pdrpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.polarday.pdrpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class SpiLoader {
    // 存储已加载的类：接口名 =》key =》实现类
    private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    // 对象实例缓存，避免重复new，类路径 =》对象实例（单例模式）
    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    // 系统spi目录
    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    // 用户自定义spi目录
    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom/";

    // 扫描路径
    private static final String[] SCAN_DIRS = new String[]{RPC_SYSTEM_SPI_DIR, RPC_CUSTOM_SPI_DIR};

    // 动态加载的类列表
    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

    // 加载所有类型
    public static void loadAll() {
        log.info("加载所有spi");
        for (Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }

    // 获取tClass接口对应的key的实现类，并返回该实现类的实例
    public static <T> T getInstance(Class<?> tClass, String key) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
        }
        if (!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader 的 %s 类型未找到 key = %s 的实现类", tClassName, key));
        }
        // 获取到要加载的实现类型，从实例缓存中加载指定类型的实例，如果不存在则创建一个新的实例并添加到缓存中
        Class<?> implClass = keyClassMap.get(key);
        String implClassName = implClass.getName();
        if (!instanceCache.containsKey(implClassName)) {
            try {
                instanceCache.put(implClassName, implClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                String errorMsg = String.format("%s 类实例化失败", implClassName);
                throw new RuntimeException(errorMsg, e);
            }
        }
        return (T) instanceCache.get(implClassName);
    }

    // 加载loadClass接口的实现类，根据接口资源文件中配置的key和实现类的全限定名返回对应的map
    public static Map<String, Class<?>> load(Class<?> loadClass) {
        log.info("加载类型为 {} 的spi", loadClass.getName());
        Map<String, Class<?>> keyClassMap = new HashMap<>();
        // 扫描路径，用户自定义的SPI优先级高于系统SPI（在SCAN_DIRS中自定义SPI在后，会覆盖前面的系统SPI）
        for (String scanDir : SCAN_DIRS) {
            // 加载的资源为对应的路径+loadClass的全限定名
            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
            for (URL resource : resources) {
                try {
                    // 读取资源文件
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] strArray = line.split("=");
                        if (strArray.length > 1) {
                            String key = strArray[0];
                            String className = strArray[1];
                            // 根据接口的文件保存key和对应的实现类
                            keyClassMap.put(key,Class.forName(className));
                        }
                    }
                }catch (Exception e){
                    log.error("加载SPI资源失败: {}", resource, e);
                }
            }
        }
        // 保存接口对应的各个实现类
        loaderMap.put(loadClass.getName(), keyClassMap);
        return keyClassMap;
    }
}
