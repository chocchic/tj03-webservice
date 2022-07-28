package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.security.dto.SessionUser;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {
	private final HttpSession httpSession;
    
	// 메인 홈
	@GetMapping("/")
	public String main(Model model) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		
		if(user!=null) {	// session에 저장된 값이 있을 때만 model에 userName으로 등록
			model.addAttribute("userName", user.getId());
		}
		
		return "main";
	}
	

    @GetMapping("/login")
    public String getLoginPage(Model model){

        return "login";
    }
	
	// 지갑 페이지
	@GetMapping("/career/wallet")
	public String walletPage(Model model, RedirectAttributes rAttr) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		if(user!=null) {
			model.addAttribute("user", user);
		}else {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "main";
		}
		return "wallet";
	}
	
	@GetMapping("/career/new")
	public String newcareerPage(Model model, RedirectAttributes rAttr) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		if(user!=null) {
			model.addAttribute("user", user);
		}else {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "main";
		}
		return "wallet";
	}
	
	@PostMapping("/career/new")
	public String insertnewcareerPage(Model model, RedirectAttributes rAttr) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		if(user!=null) {
			model.addAttribute("id", user.getId());
		}else {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "main";
		}
		return "newcareerr";
	}
	
}
