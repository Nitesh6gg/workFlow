package com.workflow.serviceImpl;

import com.workflow.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // String operations
    @Override
    public void setStringValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getStringValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    // List operations
    @Override
    public void addToList(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    // Set operations
    @Override
    public void addToSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // Hash operations
    @Override
    public void addToHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public Object getFromHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }
}
