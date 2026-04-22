package com.swaroop.Controller;
import com.swaroop.model.Post;
import com.swaroop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return "Liked!";
    }

    @PostMapping("/{postId}/bot-comment")
    public String botComment(@PathVariable Long postId,
                             @RequestParam Long botId,
                             @RequestParam Long userId) {

        postService.addBotComment(postId, botId, userId);
        return "Bot Comment Added";
    }
}