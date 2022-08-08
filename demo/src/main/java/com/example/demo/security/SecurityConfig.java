package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.demo.model.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final CustomOAuth2UserService cOAth;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.headers().frameOptions().disable() //h2-console화면을 확인하기위해 csrf토큰 해제 & 헤더 프레임옵션해제
			.and()
				.authorizeRequests() // URL 별 권한 관리를 설정하는 옵션의 시작점
				.antMatchers("/","/css/**","/images/**","/js/**","/join/**", "/career/wallet").permitAll()
				.antMatchers("/api/v1/**","/career/new", "/career/pending", "/career/pendingcareer").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
				.antMatchers("/admin/**").hasRole(Role.ADMIN.name())
				.anyRequest().authenticated()
			.and()
				.logout()
					.logoutSuccessUrl("/")
			.and()
				.oauth2Login()
					.userInfoEndpoint()
				  		.userService(cOAth);
		
	}
}
