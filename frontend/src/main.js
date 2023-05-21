import kaboom from "kaboom";

kaboom({
  background: [0, 0, 0, 1],
});

const API_URL = "http://localhost:8080";
const block_size = 20;

scene("main", () => {
  async function routeGetter() {
    const response = await fetch(API_URL + "/game/events", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!response.ok) {
      throw new Error("Failed to send data to the API.");
    }

    const route = await response.json();
    return route;
  }

  let gameEvents;

  const teste = routeGetter()
    .then((response) => {
      gameEvents = new EventSource(response.href);

      gameEvents.addEventListener("newState", (e) => {
        const state = JSON.parse(e.data);
        updateGameScene(state);
      });
    })
    .catch((error) => {
      error;
    });

  function createBoard(height, width) {
    const board = [];

    const topWall = "=".repeat(width);
    board.push(topWall);

    for (let row = 1; row < height - 1; row++) {
      let rowString = "=";
      for (let col = 1; col < width - 1; col++) {
        rowString += " ";
      }
      rowString += "=";
      board.push(rowString);
    }

    const bottomWall = "=".repeat(width);
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
      "=": () => [
        rect(block_size, block_size),
        color(28, 26, 28),
        area(),
        "wall",
      ],
      " ": () => [
        rect(block_size, block_size),
        color(28, 26, 28),
        area(),
        "free",
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

  onKeyPress("left", () => {
    console.log("left");
  });
  onKeyPress("right", () => {
    console.log("right");
  });
  onKeyPress("up", () => {
    console.log("up");
  });
  onKeyPress("down", () => {
    console.log("down");
  });
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
        sessionStorage.setItem("token", response.token);
        sessionStorage.setItem("name", response.name);
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
