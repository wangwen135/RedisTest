package com.wwh.redis.spring.Annotation;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class RedisConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String host;
    private Integer port;
    private Long timeout;
    private Integer maxTotal;
    private Integer maxIdle;
    private Long maxWaitMillis;
    private Boolean testOnBorrow;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(Long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RedisConfig [");
        if (host != null) {
            builder.append("host=");
            builder.append(host);
            builder.append(", ");
        }
        if (port != null) {
            builder.append("port=");
            builder.append(port);
            builder.append(", ");
        }
        if (timeout != null) {
            builder.append("timeout=");
            builder.append(timeout);
            builder.append(", ");
        }
        if (maxTotal != null) {
            builder.append("maxTotal=");
            builder.append(maxTotal);
            builder.append(", ");
        }
        if (maxIdle != null) {
            builder.append("maxIdle=");
            builder.append(maxIdle);
            builder.append(", ");
        }
        if (maxWaitMillis != null) {
            builder.append("maxWaitMillis=");
            builder.append(maxWaitMillis);
            builder.append(", ");
        }
        if (testOnBorrow != null) {
            builder.append("testOnBorrow=");
            builder.append(testOnBorrow);
        }
        builder.append("]");
        return builder.toString();
    }

}
