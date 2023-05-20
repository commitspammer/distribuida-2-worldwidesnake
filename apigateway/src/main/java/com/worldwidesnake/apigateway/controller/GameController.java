package com.worldwidesnake.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.worldwidesnake.apigateway.service.SnakeGameService;
import com.worldwidesnake.apigateway.model.dto.PlayerDTO;

@RestController
@RequestMapping("/game")
public class GameController {

	@Autowired
	private SnakeGameService snakeGameService;

	@GetMapping
	public String get() {
		return "Hello game!";
	}

	@PostMapping("/snakes")
	public PlayerDTO post(@RequestBody PlayerDTO player) {
		snakeGameService.registerSnake(player.name());
		return new PlayerDTO(player.name(), "BIGGER");
	}

}
