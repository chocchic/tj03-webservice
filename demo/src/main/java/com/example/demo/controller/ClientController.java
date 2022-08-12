package com.example.demo.controller;


import java.io.File;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.config.LoginUser;
import com.example.demo.dto.CareerDTO;
import com.example.demo.dto.NewWalletDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.TxDTO;
import com.example.demo.security.dto.SessionUser;
import com.example.demo.service.CareerService;
import com.example.demo.service.UserService;
import com.example.demo.service.WalletService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ClientController {
    private final CareerService careerService;
    private final UserService userS;
    private final WalletService walletS;
    
	// 메인 홈
	@GetMapping({"/main", "/"})
	public String main(Model model, @LoginUser SessionUser user) {
		if(user!=null) {	// session에 저장된 값이 있을 때만 model에 userName으로 등록
			model.addAttribute("user", user);
		}
		
		return "/main";
	}
		
	// 지갑 페이지
	@GetMapping("/career/wallet")
	public String walletPage(Model model, RedirectAttributes rAttr, @LoginUser SessionUser user) {
		if(user!=null) {
			model.addAttribute("user", user);
			model.addAttribute("wallet",userS.getWallet(user.getId()));
		}else {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "redirect:/main";
		}
		return "/career/wallet";
	}
	
	@PostMapping("/career/wallet")
	public String createwallet(@RequestParam("alias") String alias, Model model, RedirectAttributes rAttr, @LoginUser SessionUser user) {
		NewWalletDTO response = walletS.initWallet(alias);
		// 백엔드로부터 받은 pk보여주기
		if(response.getAlias().equals(alias)) {
			userS.registerWallet(user.getId(), response.getAddress(), alias);
		}
		rAttr.addAttribute("msg", response.getPrivateKey());
		return "redirect:/career/wallet";
	}
	
	@GetMapping("/career/new")
	public String newcareerPage(Model model, RedirectAttributes rAttr, @LoginUser SessionUser user) {
		if(user!=null) {
			model.addAttribute("id", user.getId());
		}else {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "redirect:/main";
		}
		return "/career/newcareer";
	}
	
	@PostMapping("/career/new")
	public String insertnewcareer(CareerDTO dto, RedirectAttributes rAttr, @LoginUser SessionUser user) {
		if (user == null) {
			rAttr.addAttribute("result", "사용자 정보가 없습니다.");
			return "/";
		}
		long num = careerService.registerCareer(dto);
		if (num > -1) {
			rAttr.addAttribute("email", dto.getApplier());
			rAttr.addAttribute("num", num);
			return "redirect:/pay/kakaoPay";
		}
		
		return "redirect:/main";
	}
		
	@GetMapping("/career/pending")
	public String pendingList(Model model, RedirectAttributes rAttr, @LoginUser SessionUser user) {
		if (user == null) {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "redirect:/main";
		}
		model.addAttribute("result", careerService.getTempCareerList(user.getId()));
		model.addAttribute("email", user.getId());
		return "/career/pending";
	}
	
	@GetMapping("/career/pendingcareer")
	public String detail(@RequestParam("num")Long num, Model model) {
		CareerDTO dto = careerService.getTempCareer(num);
		model.addAttribute("dto", dto);
		return "/career/pendingcareer";
	}
	
	@GetMapping("/career/mine")
	public String myCareer(Model model, RedirectAttributes rAttr, @LoginUser SessionUser user) {
		if (user == null) {
			rAttr.addAttribute("error", "로그인 해주세요!");
			return "redirect:/main";
		}
		String wallet = userS.getWallet(user.getId());
		List<TxDTO> tdto = walletS.getTxs(wallet);
		
		model.addAttribute("dto", tdto);
		return null;
	}
	
	
	
}
