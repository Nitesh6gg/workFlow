package com.workFlow.service;

import java.util.List;
import java.util.Set;

public interface RedisService{

    // String operations
    void setStringValue(String key, String value);

    String getStringValue(String key);

    // List operations
    void addToList(String key, String value);

    List<Object> getList(String key);

    // Set operations
    void addToSet(String key, String value);

    Set<Object> getSet(String key);

    // Hash operations
    void addToHash(String key, String hashKey, String value);

    Object getFromHash(String key, String hashKey);


}
