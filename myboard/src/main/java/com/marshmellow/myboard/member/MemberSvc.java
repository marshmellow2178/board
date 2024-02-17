package com.marshmellow.myboard.member;

import java.util.Optional;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marshmellow.myboard.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSvc {
	
	private final MemberRepo mRepo;
	private final PasswordEncoder passwordEncoder;
	
	public Member create(String uid, String pw, String email) {
		Member m = new Member();
		m.setUid(uid);
		m.setEmail(email);
		m.setPw(passwordEncoder.encode(pw));
		return this.mRepo.save(m);
	}
	
	public Member getMember(String uid) {
		Optional<Member> member = this.mRepo.findByUid(uid);
		if(member.isPresent()) {
			return member.get();
		}else {
			throw new DataNotFoundException("사용자가 존재하지 않습니다");
		}
	}
	
	public String findPw(Member m) {
		String newPw = "";
		Random r = new Random();
		for(int i = 0;i<8;i++) {
			char c = (char)(65+r.nextInt(25));
			newPw += c;
		}
		m.setPw(passwordEncoder.encode(newPw));
		this.mRepo.save(m);
		return newPw;
	}
	
	public void changePw(Member m, String oldPw, String newPw) {
		if(!passwordEncoder.matches(oldPw, m.getPw())) {
			throw new DataNotFoundException("비밀번호 불일치");
		}
		m.setPw(passwordEncoder.encode(newPw));
		this.mRepo.save(m);
	}
}
