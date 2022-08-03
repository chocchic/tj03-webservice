package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.CareerDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.security.dto.SessionUser;
import com.example.demo.service.CareerService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {
	private final HttpSession httpSession;
    private final CareerService careerService;
	// 메인 홈
	@GetMapping("/")
	public String main(Model model) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		
		if(user!=null) {	// session에 저장된 값이 있을 때만 model에 userName으로 등록
			model.addAttribute("user", user);
		}
		
		return "main";
	}
		
	// 지갑 페이지
	@GetMapping("/career/wallet")
	public String walletPage(Model model, RedirectAttributes rAttr) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		if(user!=null) {
			model.addAttribute("user", user);
		}else {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "redirect:/main";
		}
		return "wallet";
	}
	
	@GetMapping("/career/new")
	public String newcareerPage(Model model, RedirectAttributes rAttr) {
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		if(user!=null) {
			model.addAttribute("id", user.getId());
		}else {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "redirect:/main";
		}
		return "newcareer";
	}
	
	@CrossOrigin("http://localhost:80")
	@PostMapping("/career/new")
	public String insertnewcareer(CareerDTO dto, RedirectAttributes rAttr) {
		if (careerService.registerCareer(dto) != null) {
			rAttr.addAttribute("result", "결제 후 관리자가 승인할 시 경력으로 등록됩니다. \n결제를 위해 심사중인 경력 리스트로 가시겠습니까?");
			return "redirect:/career/pending";
		}
		
		rAttr.addAttribute("result", "경력 등록에 실패했습니다.");
		return "redirect:/main";
	}
	
	@GetMapping("/career/pending")
	public String pendingList(Model model, @ModelAttribute("requestDTO")PageRequestDTO pageRequestDTO) {
		model.addAttribute("result", careerService.getTempCareerList(pageRequestDTO));
		return "pending";
	}
}
