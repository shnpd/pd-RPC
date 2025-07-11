package com.polarday.pdrpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements Serializer {
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  // 创建内存输出流
        HessianOutput ho = new HessianOutput(bos);               // 绑定 Hessian 序列化器
        ho.writeObject(object);                                  // 序列化对象到内存流
        return bos.toByteArray();                                // 返回字节数组
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes); // 将字节数组包装为输入流
        HessianInput hi = new HessianInput(bis);                   // 创建 Hessian 反序列化器
        return (T) hi.readObject(tClass);                          // 读取并转换为目标类型
    }
}

