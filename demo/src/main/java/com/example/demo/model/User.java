package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User{
	@Id
	private String id;
	private String link;
	private String wallet;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public User update(String wallet) {
		this.wallet = wallet;
		
		return this;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
}