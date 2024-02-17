package com.marshmellow.myboard.Reply;

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
import com.marshmellow.myboard.Comment.Comment;
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
public class ReplySvc {
	private final ReplyRepo rRepo;
	
	private Specification<Reply> search(String uid){
		return new Specification<Reply>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				query.distinct(true); //중복제거
				Join<Reply, Member> c = root.join("member", JoinType.LEFT);
				return criteriaBuilder.like(c.get("uid"), uid);
			}
		};
	}
	
	public Reply create(String content, Member m, Comment c) {
		Reply r = new Reply();
		r.setComment(c);
		r.setMember(m);
		r.setContent(content);
		r.setPost(c.getPost());
		r.setCreateDate(LocalDateTime.now());
		return this.rRepo.save(r);
	}
	
	public Reply get(Integer id) {
		Optional<Reply> _reply = this.rRepo.findById(id);
		if(_reply.isEmpty()) {
			throw new DataNotFoundException("no data");
		}else {
			return _reply.get();
		}
	}
	
	public Page<Reply> getList(int page, String uid){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Reply> spec = search(uid);
		return this.rRepo.findAll(spec, pageable);
	}
	
	public void voteToggle(Reply r, Member m) {
		Set<Member> voters = r.getVoter();
		if(voters.contains(m)) {
			voters.remove(m);
		}else {
			voters.add(m);
		}
		r.setVoter(voters);
		this.rRepo.save(r);
	}
	
	public Reply modify(Reply r, String content) {
		r.setContent(content);
		r.setModifyDate(LocalDateTime.now());
		return this.rRepo.save(r);
	}
	
	public void delete(Reply r) {
		this.rRepo.delete(r);
	}

}
