package com.trivia247.snake.models.game;

public enum Direction {

	N, S, E, W;

	public Pos normalVector() {
		return
			this == N ? Pos.of(0 , 1) :
			this == S ? Pos.of(0 , -1) :
			this == E ? Pos.of(1, 0) :
			this == W ? Pos.of(-1 , 0) :
			null;
	}

	public Direction opposite() {
		return
			this == N ? S :
			this == S ? N :
			this == E ? W :
			this == W ? E :
			null;
	}

	public static Direction parse(String s) {
		char d = s.toUpperCase().charAt(0);
		return
			d == 'N' ? N :
			d == 'S' ? S :
			d == 'E' ? E :
			d == 'W' ? W :
			null;
	}

	@Override
	public String toString() {
		return
			this == N ? "NORTH" :
			this == S ? "SOUTH" :
			this == E ? "EAST" :
			this == W ? "WEST" :
			null;
	}

}
