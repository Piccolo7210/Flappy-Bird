# Flappy Bird Java Game

A Java implementation of the classic Flappy Bird game using Swing for the graphical interface.

## Description

This is a Java-based clone of the popular Flappy Bird game. The game features a bird that the player can control to navigate through pipes while trying to achieve the highest score possible.

## Features

- Classic Flappy Bird gameplay mechanics
- Score tracking system
- High score persistence with player names
- Simple and intuitive controls
- Smooth animations and collision detection
- Background graphics and sprite images

## Controls

- **Space Bar**: 
  - Press to start the game
  - Press to make the bird jump/flap
  - Press to restart after game over

## Technical Details

- Built using Java and Swing framework
- Object-oriented design with separate classes for game components
- Includes collision detection system
- File I/O for storing and loading high scores

## Requirements

- Java Development Kit (JDK) 17 or higher
- Java Runtime Environment (JRE)

## How to Run

1. Clone or download the repository
2. Navigate to the project directory
3. Compile the Java files:
   ```bash
   javac src/*.java
   ```
4. Run the game:
   ```bash
   java -cp src app
   ```

## Game Features

### Gameplay
- Navigate the bird through gaps between pipes
- Each successful pipe passage awards points
- Game ends if the bird collides with pipes or falls to the ground

### Scoring System
- Score increases as you successfully pass through pipes
- High scores are saved locally with player names
- Top scores are displayed on the start screen

## File Structure

```
├── src/
│   ├── app.java           # Main application entry point
│   ├── FlappyBird.java    # Game logic and rendering
│   ├── flappybird.png     # Bird sprite
│   ├── flappybirdbg.png   # Background image
│   ├── toppipe.png        # Upper pipe sprite
│   └── bottompipe.png     # Lower pipe sprite
└── topscore.txt           # High score storage
```

## Credits

This is a educational implementation of the classic Flappy Bird game. The original Flappy Bird game was created by Dong Nguyen.
