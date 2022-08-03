package com.example.demo.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CareerDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.model.Tempcareer;
import com.example.demo.persistence.CareerRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService{
	private final CareerRepo careerRepo;
	
	@Value("${com.choc.upload.path}")
	private String uploadPath;
	
	private void makeFolder(String email) {
		File uploadPathDir = new File(uploadPath, email);
		if(uploadPathDir.exists() == false) {
			uploadPathDir.mkdirs();
		}
	}
	
	@Override
	public Long registerCareer(CareerDTO dto) {
		Tempcareer career = DtoToEntity(dto);
		MultipartFile uploadFile = dto.getProof();
		if(uploadFile.isEmpty() == false) {
			//이미지 파일 또는 pdf파일만 업로드 -> 아니면 작업 중단
			String filetype = uploadFile.getContentType();
			if(filetype.startsWith("image") == false && !filetype.equals("application/pdf")) {
				return null;
			}
			//원본 파일의 파일 이름 찾아오기
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(
					originalName.lastIndexOf("\\") + 1);
			
			makeFolder(dto.getApplier());
			
			//업로드 할 파일의 경로를 생성
			String uuid = UUID.randomUUID().toString(); //파일 이름의 중복을 피하기 위해서 생성
			String saveName = uploadPath + File.separator + dto.getApplier() + File.separator + uuid + fileName;
			Path savePath = Paths.get(saveName);
			try {
				//파일 업로드
				uploadFile.transferTo(savePath);
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		Tempcareer result = careerRepo.save(career);
		System.out.println("경력등록 성공 : " + result.getApplier());
		return result.getNum();
	}

	@Override
	public PageResponseDTO getTempCareerList(PageRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
