package com.marshmellow.myboard.Post;

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
import com.marshmellow.myboard.Category.Category;
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
public class PostSvc {

	private final PostRepo postRepo;
	
	private Specification<Post> search(String keyword, Optional<Category> category){
		return new Specification<Post>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				query.distinct(true); //중복제거
				Join<Post, Member> writer = root.join("member", JoinType.LEFT);
				Predicate predicate = criteriaBuilder.or(
						criteriaBuilder.like(root.get("title"), keyword),
						criteriaBuilder.like(root.get("content"), keyword),
						criteriaBuilder.like(writer.get("uid"), keyword)
						);
				
				if(category.isEmpty()) {
					return predicate;
				}else if(category.get().getName().equals("자유")){
					Predicate getFree = criteriaBuilder.or(
							criteriaBuilder.isNull(root.get("category")),
							criteriaBuilder.equal(root.get("category"), category.get())
							);
					return criteriaBuilder.and(
							predicate,
							getFree
							);
				}else {
					return criteriaBuilder.and(
							predicate,
							criteriaBuilder.equal(root.get("category"), category.get())
					);
				}
			}
		};
	}
	
	private Specification<Post> search(String uid){
		return new Specification<Post>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				query.distinct(true); //중복제거
				Join<Post, Member> writer = root.join("member", JoinType.LEFT);
				return criteriaBuilder.like(writer.get("uid"), uid);
			}
		};
	}
	
	public Page<Post> getList(int page, String keyword, Optional<Category> category){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Post> spec = search(keyword, category);
		return postRepo.findAll(spec, pageable);
	}
	
	public Page<Post> getList(int page, String uid){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Post> spec = search(uid);
		return postRepo.findAll(spec, pageable);
	}
	
	public Post getPost(Integer id) {
		Optional<Post> p = postRepo.findById(id);
		if(p.isEmpty()) {
			throw new DataNotFoundException("NO DATA");
		}else {
			Post post =  p.get();
			post.setRead(post.getRead()+1);
			return postRepo.save(post);
		}
	}
	
	public void createPost(String title, String content, Category ctgr, Member m) {
		Post p = new Post();
		p.setTitle(title);
		p.setContent(content);
		p.setCreateDate(LocalDateTime.now());
		p.setCategory(ctgr);
		p.setMember(m);
		this.postRepo.save(p);
	}
	
	public void modify(Post p, String title, String content, Category ctgr) {
		p.setContent(content);
		p.setTitle(title);
		p.setModifyDate(LocalDateTime.now());
		p.setCategory(ctgr);
		this.postRepo.save(p);
	}
	
	public void delete(Post p) {
		this.postRepo.delete(p);
	}
	
	public void voteToggle(Post p, Member m) {
		Set<Member> voters = p.getVoter();
		if(voters.contains(m)) {
			voters.remove(m);
		}else {
			voters.add(m);
		}
		p.setVoter(voters);
		this.postRepo.save(p);
	}
}
