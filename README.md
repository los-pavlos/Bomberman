# Bomberman

Bomberman is a classic multiplayer game written in Java using Maven. The game is designed for two players who move around the map, plant bombs, and try to eliminate their opponent. Players can choose between a classic map or a randomly generated map.

## Game Features

- **Local multiplayer (2 players)**: Each player has their own controls.
- **Maps**:
  - Classic fixed map
  - Randomly generated map
- **Bombs**: Players can plant bombs, which explode after a set time, destroying nearby objects (e.g., walls) and potentially hitting the opponent.
  
## Installation and Running the Game

### Requirements
- Java 8 or newer
- Maven 3.6 or newer

### Installation steps

1. Clone this repository:
    ```bash
    git clone https://github.com/your-repository/bomberman.git
    ```

2. Navigate to the project directory:
    ```bash
    cd bomberman
    ```

3. Build the project using Maven:
    ```bash
    mvn clean install
    ```

4. Run the game:
    ```bash
    mvn exec:java -Dexec.mainClass="com.yourpackage.Main"
    ```

## Controls

- **Player 1**:
  - Movement: `W`, `A`, `S`, `D`
  - Place bomb: Space bar
- **Player 2**:
  - Movement: Arrow keys
  - Place bomb: `M`

## Map Options

Before starting the game, players can choose between two types of maps:
1. **Classic map**: A fixed grid-based map that remains the same every time.
2. **Randomly generated map**: The map is dynamically generated each time, offering a unique gameplay experience with every match.

