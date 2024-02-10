package com.marshmellow.myboard.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpForm {
	@Size(min=4, max=20)
	private String uid;
	
	private String pw;
	
	private String pw2;
	
	@Email
	private String email;
}
