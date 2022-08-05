package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dto.CareerDTO;
import com.example.demo.model.WalletResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class WalletService {
	private final static String BASE_URL = "http://localhost:3000";
	private final WebClient wc;

    public WalletResponse initWallet(String alias) {
        Mono<WalletResponse> walletmono = wc.post().uri(BASE_URL+"/MakeWallet").bodyValue("{\"Alias\""+alias+"}")
        		.retrieve().bodyToMono(WalletResponse.class);
                //.block(REQUEST_TIMEOUT); // 동기호출일 경우 사용. 
        
        return walletmono.share().block();
    }
    
    public String registerCareer(CareerDTO career) {
    	ObjectMapper mapper = new ObjectMapper();
    	String body = null;
		try {
			body = mapper.writeValueAsString(career);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	Mono<String> mono = wc.post().uri(BASE_URL+"/MakeWallet").bodyValue(body)
    			.retrieve().bodyToMono(String.class);
    	
    	return mono.share().block(); // 결과가 하나만 온다는 가정하에 mono<string>
    }
    
    
}
