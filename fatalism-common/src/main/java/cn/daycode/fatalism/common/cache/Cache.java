package cn.daycode.fatalism.common.cache;

import java.util.Set;

public interface Cache {

    Set<String> getKeys();

    Set<String> getKeys(String pattern);

    Boolean exists(String key);

    void del(String key);

    void set(String key, String value);

    void set(String key, String value, Integer expiration);

    String get(String key);

    void expire(String key, int expire);

    void append(String key, String value);

    String getSet(String key, String newValue);

    boolean setNX(String key, String value);

    Long incrBy(String key, Long delta);
}
