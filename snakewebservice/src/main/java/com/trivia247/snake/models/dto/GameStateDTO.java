package com.trivia247.snake.models.dto;

import java.util.List;

public record GameStateDTO(
		Integer id,
		Integer rows,
		Integer cols,
		Boolean teleporting,
		List<SnakeDTO> snakes,
		List<FoodDTO> foods
) {

	public GameStateDTO withId(Integer id) {
		return new GameStateDTO(
				id,
				this.rows,
				this.cols,
				this.teleporting,
				this.snakes,
				this.foods
		);
	}

}
