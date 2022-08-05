package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.persistence.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userR;
	
	@Override
	public String getWallet(String email) {
		Optional<User> user = userR.findById(email);
		if(user.isEmpty()) return "없는 email입니다.";
		return user.get().getWallet();
	}

}
