package com.swaroop.service;
import com.swaroop.model.Post;
import com.swaroop.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void likePost(Long postId) {
        String key = "post:" + postId + ":virality_score";
        redisTemplate.opsForValue().increment(key, 20);
    }

    public void addBotComment(Long postId, Long botId, Long userId) {
        String botCountKey = "post:" + postId + ":bot_count";
        Long count = redisTemplate.opsForValue().increment(botCountKey);

        if (count > 100) {
            throw new RuntimeException("429 Too Many Requests");
        }
        String cooldownKey = "cooldown:bot_" + botId + ":human_" + userId;

        if (redisTemplate.hasKey(cooldownKey)) {
            throw new RuntimeException("Cooldown active");
        }

        redisTemplate.opsForValue().set(cooldownKey, "1", Duration.ofMinutes(10));

        redisTemplate.opsForValue().increment("post:" + postId + ":virality_score", 1);
    }
}

