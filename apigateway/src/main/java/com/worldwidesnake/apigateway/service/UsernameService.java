package com.worldwidesnake.apigateway.service;

//import java.util.List;
//import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

//import com.worldwidesnake.apigateway.model.dto.GameConfigDTO;
//import com.worldwidesnake.apigateway.model.dto.GameStateDTO;
import com.worldwidesnake.apigateway.model.dto.PlayerDTO;
import com.worldwidesnake.apigateway.model.dto.UsernameDTO;
//import com.worldwidesnake.apigateway.model.dto.SnakeDTO;
//import com.worldwidesnake.apigateway.model.dto.SnakeConfigDTO;
//import com.worldwidesnake.apigateway.model.dto.LinkDTO;

@Service
public class UsernameService {

	@Autowired
	private RestTemplate restTemplate;
	
	private String baseURL = "http://localhost:8081";

//	@PostConstruct
//	public void initGlobalGame() {
//		GameConfigDTO config = new GameConfigDTO(15, 15, true, 5, List.of());
//		String url = baseURL + "/games";
//		HttpEntity<GameConfigDTO> req = new HttpEntity<>(config);
//		try {
//			GameStateDTO state = restTemplate.postForObject(url, req, GameStateDTO.class);
// 			this.globalGameURL = baseURL + "/games/" + state.id();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public UsernameDTO addUsername(String name) {
		UsernameDTO halfUsername = new UsernameDTO(name, null);
		String url = baseURL + "/usernames";
		HttpEntity<UsernameDTO> req = new HttpEntity<>(halfUsername);
		try {
			UsernameDTO user = restTemplate.postForObject(url, req, UsernameDTO.class);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public UsernameDTO getUsernameByToken(String token) {
		String url = baseURL + "/usernames/" + token;
		try {
			UsernameDTO user = restTemplate.getForObject(url, UsernameDTO.class);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public LinkDTO getStateEventsLink() {
//		return new LinkDTO(globalGameURL + "/events", "GET", "text/event-stream");
//	}
//
//	public SnakeDTO redirectSnake(String name, String facing) {
//		SnakeConfigDTO config = new SnakeConfigDTO(facing);
//		String url = globalGameURL + "/snakes/" + name;
//		HttpEntity<SnakeConfigDTO> req = new HttpEntity<>(config);
//		try {
//			ResponseEntity<SnakeDTO> resp = restTemplate.exchange(
//					url,
//					HttpMethod.PUT,
//					req,
//					SnakeDTO.class
//			);
//			return resp.getBody();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

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
