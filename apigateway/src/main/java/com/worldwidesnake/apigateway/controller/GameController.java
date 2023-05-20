package com.worldwidesnake.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.worldwidesnake.apigateway.service.SnakeGameService;

@RestController
@RequestMapping("/game")
public class GameController {

	@Autowired
	private SnakeGameService snakeGameService;

	@GetMapping
	public String get() {
		return "Hello game!";
	}

}
