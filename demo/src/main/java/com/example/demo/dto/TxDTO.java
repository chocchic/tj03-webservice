package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxDTO {
	private String txid;
	private String careerStart;
	private String careerEnd;
	private String job;
	private String proof;
	private String payment;
}

/*
 * "txID": null,
    "career": null,
    "company": null,
    "payment": null,
    "job": null,
    "proof": null
}
 * 
 * */
