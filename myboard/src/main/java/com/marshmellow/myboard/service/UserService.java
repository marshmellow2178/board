package com.marshmellow.myboard.service;

import com.marshmellow.myboard.entity.User;
import com.marshmellow.myboard.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    //조회
    @Transactional(readOnly = true)
    public User findByUsername(UserDetails userDetails) {
        return userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Transactional(readOnly = true)
    public User getUser(Long userId){
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Username not found"));
    }

    //회원가입
    @Transactional
    public Long create(
            String username,
            String password,
            String email
    ){
        if (userRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다");
        }
        if (userRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.create(username, encodedPassword, email);
        return userRepo.save(user).getId();
    }

    //정보수정
    @Transactional
    public void update(Long userId, String email){
        User user = getUser(userId);
        if(!user.getEmail().equals(email) && userRepo.existsByEmail(email)){
            throw new IllegalArgumentException("이메일 사용중");
        }
        user.updateEmail(email);
    }

    //비번변경
    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword){
        User user = getUser(userId);
        if (newPassword == null || newPassword.isBlank()) {
            return;
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다");
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("새 비밀번호가 이전 비밀번호와 같습니다");
        }
        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    //탈퇴
    @Transactional
    public boolean delete(Long userId){
        if(!userRepo.existsById(userId)){
            throw new EntityNotFoundException("user not found");
        }
        userRepo.deleteById(userId);
        return true;
    }
}
