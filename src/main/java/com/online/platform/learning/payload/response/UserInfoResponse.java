package com.online.platform.learning.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;
	private String status;
	private String token;
	private List<String> roles;

	public UserInfoResponse(Long id, String username, String email, String status,String token,List<String> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.status = status;
		this.token = token;
		this.roles = roles;
	}
}
