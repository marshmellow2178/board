package com.marshmellow.myboard.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.marshmellow.myboard.DataNotFoundException;
import com.marshmellow.myboard.Post.Post;
import com.marshmellow.myboard.member.Member;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentSvc {
	private final CommentRepo cRepo;
	
	private Specification<Comment> search(String uid){
		return new Specification<Comment>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				query.distinct(true); //중복제거
				Join<Comment, Member> c = root.join("member", JoinType.LEFT);
				return criteriaBuilder.like(c.get("uid"), uid);
			}
		};
	}
	
	public Comment create(Post p, String content, Member m) {
		Comment c = new Comment();
		c.setContent(content);
		c.setCreateDate(LocalDateTime.now());
		c.setPost(p);
		c.setMember(m);
		return this.cRepo.save(c);
	}
	
	public Comment getComment(Integer id) {
		Optional<Comment> comment = this.cRepo.findById(id);
		if(comment.isPresent()) {
			return comment.get();
		}else {
			throw new DataNotFoundException("No Data");
		}
	}
	
	public Page<Comment> getList(int page, Post p){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("voter"));
		sorts.add(Sort.Order.desc("id"));
		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.cRepo.findByPost(p, pageable);
	}
	
	public Page<Comment> getList(int page, String uid){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Comment> spec = search(uid);
		return this.cRepo.findAll(spec, pageable);
	}
	
	public void modify(Comment c, String content) {
		c.setContent(content);
		c.setModifyDate(LocalDateTime.now());
		this.cRepo.save(c);
	}
	
	public void delete(Comment c) {
		this.cRepo.delete(c);	
	}
	
	public void voteToggle(Comment c, Member m) {
		Set<Member> voters = c.getVoter();
		if(voters.contains(m)) {
			voters.remove(m);
		}else {
			voters.add(m);
		}
		c.setVoter(voters);
		this.cRepo.save(c);
	}
}
