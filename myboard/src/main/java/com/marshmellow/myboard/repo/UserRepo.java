package com.marshmellow.myboard.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marshmellow.myboard.entity.UserInfo;

public interface UserRepo extends JpaRepository<UserInfo, Integer> {
	UserInfo findByIdAndPw(String id, String pw);
}
