package com.trivia247.snake.services;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.ConcurrentModificationException;

import com.trivia247.snake.models.game.GameState;
import com.trivia247.snake.models.game.Direction;
import com.trivia247.snake.models.dto.GameStateDTO;
import com.trivia247.snake.models.dto.GameConfigDTO;
import com.trivia247.snake.models.dto.SnakeDTO;
import com.trivia247.snake.models.dto.SnakeConfigDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class GameService {

	private Map<Integer, GameState> games = new HashMap<>();
	private Map<Integer, SseEmitter> emitters = new HashMap<>();
	private Map<String, String> tokensToPlayers = new HashMap<>(); //no use yet
	private Ticker ticker;

	public GameService() {
		this.ticker = new Ticker(500);
		this.ticker.start();
	}

	public GameStateDTO createGame(GameConfigDTO gameConfig) {
		GameState game = new GameState(
				gameConfig.rows(),
				gameConfig.cols(),
				gameConfig.teleporting(),
				gameConfig.foodCount()
		);
		gameConfig.players().forEach(p -> game.addSnake(p.name())); //ignores player keys
		int id = games.size()+1;
		games.put(id, game);
		return game.toDTO().withId(id);
	}

	public GameStateDTO getGameById(Integer id) {
		try {
			return games.get(id).toDTO().withId(id);
		} catch (Exception e) {
			return null;
		}
	}

	public SnakeDTO updateSnake(Integer gameId, String snakeName, SnakeConfigDTO config) {
		try {
			games.get(gameId).redirectSnake(snakeName, Direction.parse(config.facing()));
			return games.get(gameId).snakeDTO(snakeName);
		} catch (Exception e) {
			return null;
		}
	}

	public SseEmitter getGameSseEmitterById(Integer id) {
		if (games.containsKey(id)) {
			SseEmitter emitter = new SseEmitter(-1L);
			emitters.put(id, emitter);
			return emitters.get(id);
		} else {
			return null;
		}
	}

	private class Ticker extends Thread {
		private final int ms;
		public Ticker(int ms) { this.ms = ms; }
		@Override
		public void run() {
			while (true) {
				try {
					games.values().forEach(game -> game.tick());
					emitters.forEach((id, emitter) -> {
						try {
							GameState game = games.get(id);
							emitter.send(SseEmitter.event().name("newState").data(
										game.toDTO()
							));
						} catch (Exception e) {
							emitter.completeWithError(e);
						}
					});
					Thread.sleep(ms);
				} catch (ConcurrentModificationException e) {
					System.out.println("Ticking: Concurrent error");
				} catch (InterruptedException e) {
					System.out.println("Ticking: Interrupted error");
					//break;
				}
			}
		}
	}

//	public Snake moveSnake(int id/*, Direction direction */) {
//		return null;
//	}

}
