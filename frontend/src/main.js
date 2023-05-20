import kaboom from "kaboom";

kaboom({
  background: [28, 26, 28, 1],
});

const API_URL = "http://localhost:8080";
const block_size = 20;

scene("main", () => {
  const gameEvents = new EventSource(`${API_URL}/game/events`);

  gameEvents.addEventListener("newState", (e) => {
    const state = JSON.parse(e.data);
    updateGameScene(state);
  });

  function createBoard(height, width) {
    const board = [];

    const topWall = "=".repeat(width + 2);
    board.push(topWall);

    for (let row = 1; row < height + 1; row++) {
      let rowString = "=";
      for (let col = 1; col < width + 1; col++) {
        rowString += " ";
      }
      rowString += "=";
      board.push(rowString);
    }

    const bottomWall = "=".repeat(width + 2);
    board.push(bottomWall);

    return board;
  }

  function updateGameScene(state) {
    const { rows, cols, snakes, foods } = state;

    updateBoardSize(rows, cols);
    updateSnakes(snakes);
    updateFoods(foods);
  }

  function updateBoardSize(rows, cols) {
    const map = addLevel(createBoard(rows, cols), {
      width: block_size,
      height: block_size,
      pos: vec2(0, 0),
      "=": () => [
        rect(block_size, block_size),
        color(255, 0, 0),
        area(),
        "wall",
      ],
    });
  }

  function updateSnakes(snakes) {
    destroy("snake");

    snakes.forEach((snake) => {
      const { name, head, tail, facing } = snake;

      const snakeHead = add([
        rect(block_size, block_size),
        pos(head.x * block_size, head.y * block_size),
        color(125, 124, 125),
        area(),
        { name: `snake-${name}-head`, type: "snake" },
      ]);

      tail.forEach((tailSegment) => {
        const snakeTail = add([
          rect(block_size, block_size),
          pos(tailSegment.x * block_size, tailSegment.y * block_size),
          color(255, 255, 255),
          area(),
          { type: "snake" },
        ]);
      });
    });
  }

  function updateFoods(foods) {
    destroy("food");

    foods.forEach((food, index) => {
      const foodEntity = add([
        rect(block_size, block_size),
        pos(food.x * block_size, food.y * block_size),
        color(0, 255, 0),
        area(),
        "food",
      ]);
    });
  }

  const mockState = {
    rows: 35,
    cols: 35,
    snakes: [
      {
        name: "snake1",
        head: { x: 5, y: 5 },
        tail: [
          { x: 4, y: 5 },
          { x: 3, y: 5 },
        ],
        facing: "right",
      },
      {
        name: "snake2",
        head: { x: 10, y: 15 },
        tail: [
          { x: 10, y: 16 },
          { x: 10, y: 17 },
        ],
        facing: "up",
      },
    ],
    foods: [
      { x: 8, y: 8 },
      { x: 12, y: 20 },
    ],
  };

  updateGameScene(mockState);
});

scene("login", () => {
  let curSize = 48;
  const pad = 24;

  const nameText = add([
    text("Enter your name:", {
      width: width() - pad * 2,
      size: curSize,
      align: "center",
      lineSpacing: 8,
      letterSpacing: 1,
      transform: (idx, ch) => ({
        color: hsl2rgb((time() * 0.2 + idx * 0.1) % 1, 0.7, 0.8),
        pos: vec2(0, wave(-4, 4, time() * 4 + idx * 0.5)),
        scale: wave(1, 1.2, time() * 3 + idx),
        angle: wave(-9, 9, time() * 3 + idx),
      }),
    }),
    pos(width() / 2, height() / 2 - 50),
    origin("center"),
  ]);

  const input = add([
    pos(width() / 2, height() / 2),
    origin("center"),
    text([], {
      width: width() - pad * 2,
      size: curSize,
      align: "center",
      lineSpacing: 8,
      letterSpacing: 1,
    }),
  ]);
  onCharInput((ch) => {
    input.text += ch;
  });

  onKeyPressRepeat("enter", () => {
    postData(input.text)
      .then((response) => {
        console.log("API response:", response);
        go("main");
      })
      .catch((error) => {
        console.error("Error:", error);
        go("main");
      });
  });

  onKeyPressRepeat("backspace", () => {
    input.text = input.text.substring(0, input.text.length - 1);
  });

  async function postData(name) {
    const response = await fetch(API_URL + "/game/snakes", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: name,
      }),
    });

    if (!response.ok) {
      throw new Error("Failed to send data to the API.");
    }

    return response.json();
  }
});

go("login");
