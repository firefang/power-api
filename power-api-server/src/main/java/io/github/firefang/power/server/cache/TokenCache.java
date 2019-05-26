package io.github.firefang.power.server.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author xinufo
 *
 */
public class TokenCache {
    // {token: userId}
    private static final Cache<String, Integer> CACHE = Caffeine.newBuilder().maximumSize(100).build();

    public static Integer getUserId(String token) {
        return CACHE.getIfPresent(token);
    }

    public static void setUserId(String token, Integer userId) {
        CACHE.put(token, userId);
    }

    public static void invalid(String token) {
        CACHE.invalidate(token);
    }

}
