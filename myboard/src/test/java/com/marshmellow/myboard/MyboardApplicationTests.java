package com.marshmellow.myboard;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.marshmellow.myboard.Comment.Comment;
import com.marshmellow.myboard.Comment.CommentRepo;
import com.marshmellow.myboard.Post.Post;
import com.marshmellow.myboard.Post.PostRepo;

@SpringBootTest
class MyboardApplicationTests {
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	
	@Test
	void contextLoads() {
		
		for(int i = 0;i<200;i++) {
			Post p1 = new Post();
			p1.setTitle("Test Data " + (i+1));
			p1.setContent("Testing...");
			p1.setCreateDate(LocalDateTime.now());
			this.postRepo.save(p1);
		}
		
		for(int i = 1;i<=10;i++) {
			Optional<Post> p = postRepo.findById(210);
			
			Comment c = new Comment();
			c.setPost(p.get());
			c.setContent("Pellentesque in ullamcorper diam. Vestibulum sit amet.");
			c.setCreateDate(LocalDateTime.now());
			this.commentRepo.save(c);
		}
	}

}
