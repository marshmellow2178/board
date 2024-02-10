package com.marshmellow.myboard.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {
	Optional<Member> findByUid(String uid);
}
