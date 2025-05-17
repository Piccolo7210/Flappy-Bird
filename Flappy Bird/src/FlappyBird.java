import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    Timer loop;
    Timer placePipesTimer;

    int boardW = 360;
    int boardH = 640;

    // Images
    Image bgImage;
    Image birdImage;
    Image upperPipe;
    Image lowerPipe;

    // Bird
    int birdX = boardW / 8;
    int birdY = boardH / 2;
    int birdW = 34;
    int birdH = 24;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        if(restarting && gameStarted==false && e.getKeyCode()== KeyEvent.VK_SPACE) {
//            restarting = false;
//        return;
//        }
        if (!gameStarted && e.getKeyCode() == KeyEvent.VK_SPACE ) {

                gameStarted = true;
                placePipesTimer.start();
                loop.start();

        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE && gameStarted) {
            velocityY = -9;
            if (gameOver) {
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                updateTopScore();  // Update the top score
                score = 0;
                gameOver = false;
                loop.start();
                placePipesTimer.start();
                restarting=true;
                 gameStarted = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdW;
        int height = birdH;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // PIPES
    int pipeX = boardW;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    // Game logic
    int velocityX = -4;
    Bird bird;
    int velocityY = 0;
    int gravity = 1;
    ArrayList<Pipe> pipes;
    Random random = new Random();
    boolean gameOver = false;
    double score = 0;
    boolean gameStarted = false;
    boolean restarting = false;
    // Top score and player name
    String topPlayer = "No one";
    int topScore = 0;
    File scoreFile = new File("topscore.txt");

    FlappyBird() {
        setPreferredSize(new Dimension(boardW, boardH));
        setFocusable(true);
        addKeyListener(this);
        bgImage = new ImageIcon(getClass().getResource("./flappyBirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappyBird.png")).getImage();
        upperPipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        lowerPipe = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        bird = new Bird(birdImage);
        pipes = new ArrayList<>();
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        loop = new Timer(1000 / 60, this);

        // Load the top score and player from the file
        loadTopScore();
    }

    public void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardH / 4;
        Pipe topPipe = new Pipe(upperPipe);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(lowerPipe);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // Background
        g.drawImage(bgImage, 0, 0, boardW, boardH, null);
        // Bird
        g.drawImage(birdImage, bird.x, bird.y, bird.width, bird.height, null);

        // Draw pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman", Font.BOLD, 32));

        if (!gameStarted) {
            g.drawString("Press Space to Start", 50, boardW / 2);

            // Display Top Score and Player
            g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            g.drawString("Top Score: " + topScore, 120, 400);
            g.drawString("Player: " + topPlayer, 120, 440);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 80, 250);
            g.drawString("Press Space to Restart", 30, 350);
        }
    }

    public void move() {
        // Bird movement
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // Pipe movement
        for (Pipe pipe : pipes) {
            pipe.x += velocityX;
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5;
            }
            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }
        if (bird.y > boardH) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            placePipesTimer.stop();
            loop.stop();
        }
    }

    // Method to load the top score from a file
    public void loadTopScore() {
        if (scoreFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(scoreFile))) {
                topPlayer = br.readLine();
                topScore = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to save the top score to a file
    public void saveTopScore() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(scoreFile))) {
            bw.write(topPlayer);
            bw.newLine();
            bw.write(String.valueOf(topScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update the top score if the current score is higher
    public void updateTopScore() {
        if ((int) score > topScore) {
            topScore = (int) score;
            topPlayer = JOptionPane.showInputDialog(this, "New High Score! Enter your name:");
            saveTopScore();  // Save the new top score and player to the file
        }
    }
}
