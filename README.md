# Bomberman  

Bomberman is a classic multiplayer game written in **Java** using **JavaFX** and **Maven**. The game is designed for two players who move around the map, plant bombs, and try to eliminate each other. Players can choose between a **classic fixed map** or a **randomly generated map**.  

![bombermanExample](https://github.com/user-attachments/assets/1d2d952d-8818-46ba-9847-5712123b4366)

## Technologies  

- **Java 21+**  
- **JavaFX** for the graphical user interface  
- **Maven** for dependency management and project build  


## Game Features

- **Local multiplayer (2 players)**: Each player has their own controls.  
- **Maps**:  
  - **Classic fixed map**  
  - **Randomly generated map**  
- **Bombs**: Players can plant bombs, which explode after a set time, destroying nearby objects (e.g., walls) and potentially hitting the opponent.  
- **Boosts**: Players can pick up two types of temporary power-ups:  
  - **Speed Boost**: Doubles the player's speed.
  - **Bomb Range Boost**: Doubles the explosion range of bombs.



## Controls  

- **Player 1**:  
  - Movement: `W`, `A`, `S`, `D`  
  - Place bomb: Space bar  
- **Player 2**:  
  - Movement: Arrow keys  
  - Place bomb: `M`  


# How to Run the Game

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21+**  
  Verify the installation by running:  
  ```bash
  java -version
  ```

- **Maven**  
  Verify the installation by running:  
  ```bash
  mvn -version
  ```

## Steps to Run the Game

1. **Clone the Repository**  
   Clone the project repository to your local machine.
   ```bash
   git clone https://github.com/los-pavlos/Bomberman.git
   ```
   

3. **Navigate to the Project Directory**  
   Change to the home directory of the project.
   ```bash
   cd Bomberman
   ```

5. **Build the Project**  
   Run the following Maven command to clean and package the project:
   ```bash
   mvn clean package
   ```




6. **Navigate to the Target Directory**

   *(This step can be replaced by running run_project.bat which is located in the project's home directory.)*

   Move to the `target` directory and run following command:
   ```bash
   cd target
   java --module-path ".\project-0.0.1-SNAPSHOT.jar;.\libs" -m cz.vsb.project/cz.vsb.App
   ```


Now the game should be up and running!


