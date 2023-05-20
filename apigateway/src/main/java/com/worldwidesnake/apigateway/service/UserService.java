package com.worldwidesnake.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

@Service
public class UserService {

//	private List<String> users;
//	private RestTemplate restTemplate = new RestTemplate();
//	
//	private String baseURL = "http://localhost:8081";
//
//	private record NicknameDTO(String nickname) {};
//	private record TokenDTO(String token) {};
//
//	public String registerNickname(String nickname) {
//		String url = baseURL + "/nicknames";
//		HttpEntity<NicknameDTO> req = new HttpEntity<>(new NicknameDTO(nickname));
//		try {
//			TokenDTO tokenDTO = restTemplate.postForObject(url, req, TokenDTO.class);
//			return tokenDTO.token();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public String retrieveNickname(String token) {
//		String url = baseURL + "/nicknames/" + token;
//		try {
//			NicknameDTO nicknameDTO = restTemplate.getForObject(url, NicknameDTO.class);
//			return nicknameDTO.nickname();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public String unregisterNickname(String token) {
//		String url = baseURL + "/nicknames/" + token;
//		try {
//			ResponseEntity<NicknameDTO> resp = restTemplate.exchange(
//					url,
//					HttpMethod.DELETE,
//					HttpEntity.EMPTY,
//					NicknameDTO.class
//			);
//			return resp.getBody().nickname();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

}
