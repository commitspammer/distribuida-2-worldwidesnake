package com.worldwidesnake.apigateway.service;

import java.util.List;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import com.worldwidesnake.apigateway.model.dto.GameConfigDTO;
import com.worldwidesnake.apigateway.model.dto.GameStateDTO;

@Service
public class SnakeGameService {

	@Autowired
	private RestTemplate restTemplate;
	
	private String baseURL = "http://localhost:8083";

	private String globalGameURL = null;

	@PostConstruct
	public void initGlobalGame() {
		GameConfigDTO config = new GameConfigDTO(15, 15, true, 5, List.of());
		String url = baseURL + "/games";
		HttpEntity<GameConfigDTO> req = new HttpEntity<>(config);
		try {
			GameStateDTO state = restTemplate.postForObject(url, req, GameStateDTO.class);
 			this.globalGameURL = baseURL + "/games/" + state.id();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
