package com.marshmellow.myboard.member;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwFindForm {
	private String uid;
	
	@Email
	private String email;
}
