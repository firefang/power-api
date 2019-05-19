package io.github.firefang.power.server.auth;

import java.security.SecureRandom;
import java.util.Date;

import org.apache.tomcat.util.buf.HexUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.web.util.CurrentRequestUtil;

/**
 * @author xinufo
 *
 */
public abstract class TokenHelper {
    private static final String ISSUER = "power-api";
    private static final String CLAIM_UID_KEY = "uid";
    private static final long EXPIRE = 8 * 3600 * 1000; // 8h
    private static final int TOKEN_KEY_LEN = 8;

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 创建Token
     * 
     * @param key Token key
     * @param userId
     * @return
     */
    public static String create(String key, Integer userId) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        return JWT.create().withIssuer(ISSUER).withExpiresAt(getExpire()).withClaim(CLAIM_UID_KEY, userId)
                .sign(algorithm);
    }

    /**
     * 从Token中获取User ID
     * 
     * @param token
     * @return
     */
    public static Integer decodeUserId(String token) {
        return JWT.decode(token).getClaims().get(CLAIM_UID_KEY).asInt();
    }

    /**
     * 获取当前用户ID
     * 
     * @return
     */
    public static Integer decodeUserIdFromCurrentRequest() {
        String token = CurrentRequestUtil.getHeader(IPowerConstants.TOKEN_KEY);
        return decodeUserId(token);
    }

    /**
     * 验证Token
     * 
     * @param key
     * @param token
     * @return
     */
    public static boolean verify(String key, String token) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verify = JWT.require(algorithm).withIssuer(ISSUER).acceptExpiresAt(EXPIRE / 1000).build();
        try {
            verify.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    /**
     * 生成Token Key
     * 
     * @return
     */
    public static String generateKey() {
        byte[] bytes = RANDOM.generateSeed(TOKEN_KEY_LEN);
        return HexUtils.toHexString(bytes);
    }

    private static Date getExpire() {
        return new Date(System.currentTimeMillis() + EXPIRE);
    }

}
