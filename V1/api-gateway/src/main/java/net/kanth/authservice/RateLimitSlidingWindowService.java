package net.kanth.authservice;

import java.time.Duration;
import java.time.Instant;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import org.springframework.data.domain.Range;
@Service
public class RateLimitSlidingWindowService {

    private final ReactiveStringRedisTemplate redisTemplate;

	public RateLimitSlidingWindowService(ReactiveStringRedisTemplate redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}
    
    public Mono<Boolean> isAllowed(String userId, int maxRequests, int windowSeconds) {
    	
        String key = "rate_limit:" + userId;
        long now = Instant.now().toEpochMilli();
        long windowStart = now - (windowSeconds * 1000);

        return redisTemplate.opsForZSet()
                // remove old timestamps
                .removeRangeByScore(key, Range.closed(0.0, (double) windowStart))

                // count remaining requests in window
                .then(redisTemplate.opsForZSet().size(key))

                .flatMap(count -> {

                    if (count != null && count >= maxRequests) {
                        return Mono.just(false);
                    }

                    // add new request timestamp
                    return redisTemplate.opsForZSet()
                            .add(key, String.valueOf(now), now)
                            .then(redisTemplate.expire(key, Duration.ofSeconds(windowSeconds)))
                            .thenReturn(true);
                });
    
    }
    

}
