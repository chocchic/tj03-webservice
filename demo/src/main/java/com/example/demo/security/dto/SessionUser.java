package com.example.demo.security.dto;

import java.io.Serializable;

import com.example.demo.model.User;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String wallet;
	private String link;
	
	public SessionUser(User user) {
		this.id = user.getId();
		this.link = user.getLink();
		this.wallet = user.getWallet();
				
	}
}
