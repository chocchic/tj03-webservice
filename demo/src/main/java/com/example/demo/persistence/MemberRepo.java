package com.example.demo.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface MemberRepo extends JpaRepository<User, String> {
}