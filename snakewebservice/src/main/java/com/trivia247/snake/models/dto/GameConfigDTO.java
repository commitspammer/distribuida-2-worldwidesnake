package com.trivia247.snake.models.dto;

import java.util.List;

public record GameConfigDTO(
		Integer rows,
		Integer cols,
		Boolean teleporting,
		Integer foodCount,
		List<PlayerDTO> players
) {}
