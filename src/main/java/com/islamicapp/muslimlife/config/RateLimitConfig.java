package com.islamicapp.muslimlife.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RateLimitConfig {

    @Getter
    @Value("${rate-limit.enabled:true}")
    private boolean rateLimitEnabled;

    @Value("${rate-limit.default-capacity:100}")
    private long defaultCapacity;

    @Value("${rate-limit.default-refill-tokens:100}")
    private long defaultRefillTokens;

    @Value("${rate-limit.default-refill-duration-minutes:1}")
    private long defaultRefillDurationMinutes;

    @Bean
    public Cache<String, Bucket> bucketCache() {
        return Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(10000)
                .build();
    }

    public Bucket resolveBucket(String key) {
        Cache<String, Bucket> cache = bucketCache();
        return cache.get(key, k -> createNewBucket());
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(defaultCapacity,
                Refill.greedy(defaultRefillTokens,
                        Duration.ofMinutes(defaultRefillDurationMinutes)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

}
