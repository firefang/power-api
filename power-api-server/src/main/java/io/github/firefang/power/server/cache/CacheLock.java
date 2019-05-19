package io.github.firefang.power.server.cache;

import java.time.Duration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author xinufo
 *
 */
public class CacheLock {
    private static final Duration LOCK_TIME = Duration.ofMillis(500);
    private static final Object LOCK_VAL = new Object();
    private static Cache<Object, Object> LOCKS = Caffeine.newBuilder().expireAfterWrite(LOCK_TIME).build();

    public static boolean tryLock(String key) {
        final boolean[] flag = new boolean[1];
        LOCKS.get(key, k -> {
            flag[0] = true;
            return LOCK_VAL;
        });
        return flag[0];
    }

}
