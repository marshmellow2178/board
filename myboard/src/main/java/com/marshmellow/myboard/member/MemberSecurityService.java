package com.marshmellow.myboard.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService{
	private final MemberRepo mRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> _member = this.mRepo.findByUid(username);
		if(_member.isEmpty()) {
			throw new UsernameNotFoundException("존재하지 않는 아이디입니다");
		}
		Member m = _member.get();
		List<GrantedAuthority> auths = new ArrayList<>();
		if("admin".equals(username)) {
			auths.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		}else {
			auths.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
		}
		return new User(m.getUid(), m.getPw(), auths);
	}
	
}
