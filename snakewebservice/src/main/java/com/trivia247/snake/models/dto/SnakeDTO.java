package com.trivia247.snake.models.dto;

import java.util.List;

public record SnakeDTO(
		String name,
		PosDTO head,
		List<PosDTO> tail,
		String facing
) {

	public SnakeDTO withPlayerName(String name) {
		return new SnakeDTO(
				name,
				this.head,
				this.tail,
				this.facing
		);
	}

}
