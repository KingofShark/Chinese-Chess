# Chinese Chess (Xiangqi)

**[View in Vietnamese/ Xem bằng Tiếng Việt](README.md)**

Chinese Chess (Xiangqi) is a traditional Chinese two-player strategy game, recreated in this project as a Java application. The game offers an intuitive interface, supports single-player mode (against AI) or two-player mode (in development), and includes features such as a countdown timer, game saving, and surrender options. The project aims to deliver a smooth and engaging Xiangqi experience.

## Features

- **Game Modes**: Play against AI or another player (in development).
- **Countdown Timer**: Each player has a time limit per move, displayed with a visual countdown bar.
- **User-Friendly Interface**: The board and pieces are designed for clarity and ease of use.
- **Save and Load Game**: Save the game state to resume later.
- **Surrender and Replay**: Allow players to surrender or start a new game.
- **Sound Effects**: Enhance the experience with sounds for game start, end, and piece movements.

## System Requirements

- **Operating System**: Windows, macOS, or Linux.
- **Java**: Java Development Kit (JDK) version 8 or higher (tested with JDK 22.0.1).
- **RAM**: Minimum 512MB.
- **Disk Space**: Approximately 200MB for source code and resources.

## Installation Guide

### 1. Download the Source Code

Clone the repository to your machine using the following command:

```bash
git clone https://github.com/Mr-1504/Chinese-Chess.git
cd Chinese-Chess
```

Alternatively, download the ZIP file from GitHub and extract it.

### 2. Install Java

Ensure you have JDK 8 or higher installed. Check the Java version with:

```bash
java --version
```

Example output with JDK 22.0.1:

```
java 22.0.1 2024-04-16
Java(TM) SE Runtime Environment (build 22.0.1+8-16)
Java HotSpot(TM) 64-Bit Server VM (build 22.0.1+8-16, mixed mode, sharing)
```

If not installed, download and install JDK from Oracle's official website or use OpenJDK from Adoptium.

### 3. Compile and Run

The project uses Java Swing, which is included in the JDK, so no additional libraries are required.

1. **Compile the Source Code**: In the project directory, run the following command to compile:

   ```bash
   javac -d bin src/**/*.java
   ```

   This command compiles all Java files in the `src` directory and stores the `.class` files in the `bin` directory.

2. **Run the Game**: After compiling, run the program with:

   ```bash
   java -cp bin view.Home
   ```

   The `view.Home` class is the entry point, displaying the main menu of the game.

### 4. (Optional) Use an IDE

You can import the project into an IDE like IntelliJ IDEA, Eclipse, or NetBeans for easier compilation and execution:

- Open the IDE and import the project directory (`Chinese-Chess`).
- Configure the JDK (8 or higher) in the IDE.
- Run the `view.Home` class to start the game.

## How to Play

### 1. Start the Game

- From the main menu (`Home`), select a game mode:
  - **Play vs AI**: Compete against a simple AI.
  - **Play vs Human**: Two players on the same machine, taking turns (in development).
- Click "Start" to enter the game board.

### 2. Basic Rules

Xiangqi is a strategy game played on a 9x10 board, with each side controlling 16 pieces (King, Advisors, Elephants, Rooks, Cannons, Knights, Pawns). The objective is to checkmate the opponent's King. Basic rules include:

- **King**: Moves one square within the "palace" (3x3 grid at the board's end).
- **Advisors**: Move diagonally one square within the palace.
- **Elephants**: Move two squares diagonally, cannot cross the river.
- **Rooks**: Move any number of squares horizontally or vertically.
- **Cannons**: Move like Rooks but require jumping over one piece to capture.
- **Knights**: Move in an L-shape (two squares in one direction, one perpendicular), can be blocked.
- **Pawns**: Move one square forward; after crossing the river, they can move sideways.

For detailed rules, refer to Xiangqi Rules.

### 3. Game Controls

- **Move Pieces**: Left-click a piece to select it, then click the destination square. Valid moves are highlighted.
- **Countdown Timer**: Each player has a time limit (default: 10 minutes), shown on the player's avatar.
- **Surrender**: Click the "Surrender" button to end the game and show the "Replay" button.
- **Replay**: After the game ends (surrender or checkmate), click "Replay" to start a new game.
- **Return to Home**: Click the "Home" button to exit and return to the menu.

### 4. Save and Load Game

- **Save Game**: When returning to the home screen during a game, the state is automatically saved.
- **Load Game**: From the main menu, select "Previous Game" to load the saved game.

## Project Structure

- `src/controller`: Contains game control classes (`GameController`, `Notification`).
- `src/model`: Contains model classes like `ChessPiece`, `RoundedImageLabel`.
- `src/view`: Contains UI classes like `Home`, `ChessBoard`.
- `src/file`: Contains the `IOFile` class for saving and loading games.
- `resource`: Contains resources like piece images, sound effects, and menu icons.

## Contributing

We welcome contributions to improve the game! To contribute:

1. Fork this repository.
2. Create a new branch (`git checkout -b feature/feature-name`).
3. Make changes and commit (`git commit -m 'Add feature XYZ'`).
4. Push to your branch (`git push origin feature/feature-name`).
5. Create a Pull Request on GitHub.

Please read CONTRIBUTING.md for more details (if available).

## License

The project is distributed under the MIT License. See the LICENSE file for details.

## Contact

For questions or support, please open an issue on GitHub or contact via email: truongvan.minh1504@gmail.com.

---

**Author**: Trương Văn Minh\
**Repository**: https://github.com/Mr-1504/Chinese-Chess