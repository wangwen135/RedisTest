package com.wwh.redis.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;

public class JsonRedisSerializer implements RedisSerializer<Object> {

    private static final String CHARSET_NAME = "UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(JsonRedisSerializer.class);

    @Override
    public byte[] serialize(Object data) throws SerializationException {
        if (data == null) {
            return null;
        }

        try {
            // 先转成String
            String json = JSON.toJSONString(data);
            // 包装
            SerializerObject so = new SerializerObject();
            so.setClassName(data.getClass().getName());
            so.setJsonString(json);
            // 再转
            String serializerStr = JSON.toJSONString(so);

            return serializerStr.getBytes(CHARSET_NAME);
        } catch (Exception e) {
            logger.error("RedisSerializer序列化异常", e);
            throw new SerializationException("RedisSerializer序列化异常");
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            String serializerStr = new String(bytes, CHARSET_NAME);
            SerializerObject deSerObj = JSON.parseObject(serializerStr, SerializerObject.class);

            Class<?> t = Class.forName(deSerObj.getClassName());
            return JSON.parseObject(deSerObj.getJsonString(), t);

        } catch (Exception e) {
            logger.error("RedisSerializer反序列化异常", e);
            throw new SerializationException("RedisSerializer反序列化异常");
        }
    }

}

class SerializerObject {
    private String className;
    private String jsonString;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

}
