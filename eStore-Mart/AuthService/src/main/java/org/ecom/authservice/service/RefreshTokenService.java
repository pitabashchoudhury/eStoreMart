package org.ecom.authservice.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {

    private static final String PREFIX = "auth:refresh:";

    private final RedisTemplate<String, String> redisTemplate;

    public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeRefreshToken(String username, String refreshToken) {
        String key = PREFIX + username;

        //  Overwrites old token → single device login
        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                7, TimeUnit.DAYS
        );
    }

    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(PREFIX + username);
    }

    public void deleteRefreshToken(String username) {
        redisTemplate.delete(PREFIX + username);
    }
}

