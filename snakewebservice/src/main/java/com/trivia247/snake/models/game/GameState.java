package com.trivia247.snake.models.game;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

import com.trivia247.snake.models.dto.DTO;
import com.trivia247.snake.models.dto.SnakeDTO;
import com.trivia247.snake.models.dto.GameStateDTO;

public class GameState implements DTO<GameStateDTO> {

	private final int rows;
	private final int cols;
	private final boolean teleporting;
	private final Map<String, Snake> snakes;
	private final List<Food> foods;
	//private final List<Obstacle> obstacles;

	public GameState() {
		this.rows = 20;
		this.cols = 20;
		this.teleporting = true;
		this.snakes = new HashMap<>();
		this.foods = new ArrayList<>();
		for (int i = 0; i < 10; i++) spawnFood();
	}

	public GameState(
			int rows,
			int cols,
			boolean teleporting,
			int foodCount
	) {
		this.rows = rows;
		this.cols = cols;
		this.teleporting = teleporting;
		this.snakes = new HashMap<>();
		this.foods = new ArrayList<>();
		for (int i = 0; i < foodCount; i++) spawnFood();
	}

	public void tick() {
		snakes.forEach((k, v) -> {
			Pos nextPos = wrapPos(v.facing().normalVector().add(v.head()));
			Optional<Food> nextFood = foods.stream()
				.filter(f -> f.pos().equals(nextPos))
				.findAny();
			if (nextFood.isPresent()) {
				v.grow(nextPos);
				foods.remove(nextFood.get());
				spawnFood();
			} else {
				List<Pos> heads = snakes.values().stream()
					.map(s -> s.head())
					.collect(Collectors.toList());
				List<Pos> tails = snakes.values().stream()
					.map(s -> s.tail())
					.flatMap(l -> l.stream())
					.collect(Collectors.toList());
				Optional<Pos> obstacle = Stream.concat(
					heads.stream(), tails.stream()
				).filter(p -> p.equals(nextPos)).findAny();
				if (obstacle.isPresent()) {
					v.kill(randomFreePos());
				} else {
					v.step(nextPos);
				}
			}
		});
	}

	public int getRows() { return rows; }
	public int getCols() { return cols; }

	public void addSnake(String playerName) {
		Snake snake = new Snake(randomFreePos());
		snakes.putIfAbsent(playerName, snake);
	}

	public void removeSnake(String name) {
		snakes.remove(name);
	}

	public Pos getSnakeHead(String name) {
		return snakes.get(name).head(); //NullPointerExcep
	}

	public List<Pos> getSnakeTail(String name) {
		return snakes.get(name).tail(); //NullPointerExcep
	}

	public void redirectSnake(String name, Direction direction) {
		snakes.get(name).face(direction);
	}

	public List<Pos> getFoods() {
		return foods.stream().map(f -> f.pos()).collect(Collectors.toList());
	}

	public void spawnFood() {
		foods.add(new Food(randomFreePos()));
	}

	private Pos randomPos() {
		return Pos.of(
			(int)(Math.random() * rows),
			(int)(Math.random() * cols)
		);
	}

	private Pos randomFreePos() {
		List<Pos> fo = foods.stream()
			.map(f -> f.pos())
			.collect(Collectors.toList());
		List<Pos> sh = snakes.values().stream()
			.map(s -> s.head())
			.collect(Collectors.toList());
		List<Pos> st = snakes.values().stream()
			.map(s -> s.tail())
			.flatMap(l -> l.stream())
			.collect(Collectors.toList());
		List<Pos> free = new ArrayList<>();
		for (int y = 0; y < rows; y++)
			for (int x = 0; x < cols; x++)
				free.add(Pos.of(x, y));
		free.removeAll(fo);
		free.removeAll(sh);
		free.removeAll(st);
		return free.get((int)(Math.random() * free.size()));
	}

	private Pos wrapPos(Pos pos) {
		if (!teleporting) return pos;
		if (pos.x < 0) pos = Pos.of(cols-1, pos.y);
		if (pos.y < 0) pos = Pos.of(pos.x, rows-1);
		if (pos.x > cols-1) pos = Pos.of(0, pos.y);
		if (pos.y > rows-1) pos = Pos.of(pos.x, 0);
		return pos;
	}

	public SnakeDTO snakeDTO(String name) {
		return snakes.get(name).toDTO().withPlayerName(name); //NullPointerExcep
	}

	@Override
	public GameStateDTO toDTO() {
		return new GameStateDTO(
				null,
				rows,
				cols,
				teleporting,
				snakes.entrySet().stream()
					.map(e -> e.getValue().toDTO().withPlayerName(e.getKey()))
					.collect(Collectors.toList()),
				foods.stream().map(f -> f.toDTO()).collect(Collectors.toList())
		);
	}

}
