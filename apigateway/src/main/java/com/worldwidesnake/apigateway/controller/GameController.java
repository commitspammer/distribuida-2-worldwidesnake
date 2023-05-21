package com.worldwidesnake.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.worldwidesnake.apigateway.service.SnakeGameService;
import com.worldwidesnake.apigateway.model.dto.PlayerDTO;
import com.worldwidesnake.apigateway.model.dto.SnakeDTO;
import com.worldwidesnake.apigateway.model.dto.SnakeConfigDTO;
import com.worldwidesnake.apigateway.model.dto.LinkDTO;

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
	public PlayerDTO postSnake(@RequestBody PlayerDTO player) {
		snakeGameService.registerSnake(player.name());
		return new PlayerDTO(player.name(), "token");
	}

	@GetMapping("/events")
	public LinkDTO getEvents() {
		return snakeGameService.getStateEventsLink();
	}

	@PutMapping("/snakes/{name}")
	public SnakeDTO putSnake(@PathVariable String name, @RequestBody SnakeConfigDTO config) {
		return snakeGameService.redirectSnake(name, config.facing());
	}

	@DeleteMapping("/snakes/{name}")
	public void deleteSnake(@PathVariable String name) {
		snakeGameService.unregisterSnake(name);
	}

}
