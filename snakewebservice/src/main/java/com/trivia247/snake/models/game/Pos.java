package com.trivia247.snake.models.game;

import com.trivia247.snake.models.dto.DTO;
import com.trivia247.snake.models.dto.PosDTO;

public class Pos implements DTO<PosDTO> {

	public final int x;
	public final int y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Pos of(int x, int y) {
		return new Pos(x, y);
	}

	public Pos add(Pos point) {
		return new Pos(this.x + point.x, this.y + point.y);
	}

	public Pos sub(Pos point) {
		return new Pos(this.x - point.x, this.y - point.y);
	}

	@Override
	public String toString() {
		return "(" + String.valueOf(x) + ", " + String.valueOf(y) + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
    		result = prime * result + (x ^ (x >>> 32));
    		result = prime * result + (y ^ (x >>> 32));
    		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o == null || getClass() != o.getClass()) {
			return false;
		} else {
			Pos point = (Pos) o;
			return this.x == point.x && this.y == point.y;
		}
	}

	@Override
	public PosDTO toDTO() {
		return new PosDTO(x, y);
	}

}
