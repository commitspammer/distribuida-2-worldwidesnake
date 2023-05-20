package com.trivia247.snake.models.dto;

@FunctionalInterface
public interface DTO<T> {

	T toDTO();

}
