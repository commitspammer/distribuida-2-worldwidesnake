package com.trivia247.snake.models.game;

import com.trivia247.snake.models.dto.DTO;
import com.trivia247.snake.models.dto.FoodDTO;

public class Food implements DTO<FoodDTO> {

	private Pos position;
	
	public Food(Pos position) {
		this.position = position;
	}

	public Pos pos() {
		return position;
	}

	public int power() {
		return 1;
	}

	@Override
	public FoodDTO toDTO() {
		return new FoodDTO(
				position.x,
				position.y
		);
	}

}
