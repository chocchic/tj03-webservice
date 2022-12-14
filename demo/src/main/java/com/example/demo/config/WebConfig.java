package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
	private final LoginUserArgumentResolver luArgumentR;
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> arR) {
		arR.add(luArgumentR);
	}
}
