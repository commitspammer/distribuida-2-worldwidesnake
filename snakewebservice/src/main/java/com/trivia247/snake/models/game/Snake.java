package com.trivia247.snake.models.game;

import java.util.Deque;
import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;

import com.trivia247.snake.models.dto.DTO;
import com.trivia247.snake.models.dto.SnakeDTO;

public class Snake implements DTO<SnakeDTO> {

	private Deque<Pos> body;
	private Direction facing;
	private boolean hasMoved;
	
	public Snake() {
		this.body = new LinkedList<>();
		this.body.addFirst(Pos.of(0,0));
		this.facing = Direction.E;
		this.hasMoved = false;
	}
	
	public Snake(Pos headPos) {
		this.body = new LinkedList<>();
		this.body.addFirst(headPos);
		this.facing = Direction.E;
		this.hasMoved = false;
	}
	
	public Snake(Direction direction, Pos headPos) {
		this.body = new LinkedList<>();
		this.body.addFirst(headPos);
		this.facing = direction;
		this.hasMoved = false;
	}

	public void face(Direction direction) {
		if (hasMoved && this.facing != direction.opposite()) {
			facing = direction;
			hasMoved = false;
		}
	}

	public void step() {
		body.addFirst(this.facing.normalVector().add(body.getFirst()));
		body.removeLast();
		hasMoved = true;
	}

	public void step(Pos pos) {
		body.addFirst(pos);
		body.removeLast();
		hasMoved = true;
	}

	public void grow() {
		body.addFirst(this.facing.normalVector().add(body.getFirst()));
		hasMoved = true;
	}

	public void grow(Pos pos) {
		body.addFirst(pos);
		hasMoved = true;
	}

	public Pos head() {
		return body.getFirst();
	}

	public List<Pos> tail() {
		return body.stream().skip(1).collect(Collectors.toList());
	}

	public Direction facing() {
		return facing;
	}

	public void kill(Pos pos) {
		body.clear();
		body.addFirst(pos);
	}

	@Override
	public SnakeDTO toDTO() {
		return new SnakeDTO(
				null,
				head().toDTO(),
				tail().stream().map(p -> p.toDTO()).collect(Collectors.toList()),
				facing.toString()
		);
	}

}
