package com.marshmellow.myboard.config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //대상 없음
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleNotFound(EntityNotFoundException ex, Model m) {
        m.addAttribute("error", ex.getMessage());
        return "error/404";
    }

    //권한 없음
    @ExceptionHandler(AccessDeniedException.class)
    public String handleDenied(AccessDeniedException ex, Model m) {
        m.addAttribute("error", ex.getMessage());
        return "error/403";
    }

    //회원가입 중복 에러메시지
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleSignUpError(IllegalArgumentException ex, Model m) {
        m.addAttribute("error", ex.getMessage());
        return "signup_form";
    }
}

