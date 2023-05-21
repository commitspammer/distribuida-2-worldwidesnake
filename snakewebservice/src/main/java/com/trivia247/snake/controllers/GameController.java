package com.trivia247.snake.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.trivia247.snake.services.GameService;
import com.trivia247.snake.models.dto.GameStateDTO;
import com.trivia247.snake.models.dto.GameConfigDTO;
import com.trivia247.snake.models.dto.SnakeDTO;
import com.trivia247.snake.models.dto.SnakeConfigDTO;
import com.trivia247.snake.models.dto.PlayerDTO;

@RestController
@RequestMapping("/games")
public class GameController {

	@Autowired
	private GameService gameService;

	@PostMapping
	public GameStateDTO post(@RequestBody GameConfigDTO configDTO) {
		return gameService.createGame(configDTO);
	}

	@GetMapping("/{id}")
	public GameStateDTO get(@PathVariable Integer id) {
		return gameService.getGameById(id);
	}

	@GetMapping("/{id}/events")
	public SseEmitter subscribe(@PathVariable Integer id) {
		return gameService.getGameSseEmitterById(id);
	}

	@PostMapping("{id}/snakes")
	public SnakeDTO postSnake(@PathVariable Integer id, @RequestBody PlayerDTO playerDTO) {
		return gameService.addSnake(id, playerDTO.name());
	}

	@PutMapping("{id}/snakes/{name}")
	public SnakeDTO putSnake(@PathVariable Integer id, @PathVariable String name, @RequestBody SnakeConfigDTO configDTO) {
		return gameService.updateSnake(id, name, configDTO);
	}

	@DeleteMapping("{id}/snakes/{name}")
	public void deleteSnake(@PathVariable Integer id, @PathVariable String name) {
		gameService.removeSnake(id, name);
	}

}
