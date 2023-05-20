package com.worldwidesnake.apigateway.model.dto;

import java.util.List;

public record GameConfigDTO(
		Integer rows,
		Integer cols,
		Boolean teleporting,
		Integer foodCount,
		List<PlayerDTO> players
) {}
