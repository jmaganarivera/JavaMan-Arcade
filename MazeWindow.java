package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MazeWindow extends JPanel {
    private Maze currentMaze;
    private List<Maze> mazes;
    private PacCharacter pacMan;
    private List<Ghost> ghosts;
    private int score;
    private int lives;
    private boolean gameStarted; // Flag to determine if the game has started
    private Image heartImage; // Image for representing lives
    private int currentLevelIndex;
    private boolean gameOver; // Flag to track game over state
    private boolean gameWon; // Flag to track whether the game the player has won the game
 
    public MazeWindow(List<Maze> mazes) {
    	
    	
    	 Sound.playSound("pacman_beginning.wav");
    	this.currentLevelIndex = 0;
        this.currentMaze = mazes.get(currentLevelIndex);
        this.mazes = mazes;
        
        
        this.pacMan = new PacCharacter();
        this.score = 0;
        this.ghosts = new ArrayList<>();
        this.lives = 3; // Initializing lives to 3
        gameWon = false;
        gameOver = false;

        
        ghosts.add(new Ghost(100, 100)); // Ghost 1
        ghosts.add(new Ghost(120, 100)); // Ghost 2, slightly offset
        ghosts.add(new Ghost(140, 100)); // Ghost 3, further offset

        gameStarted = false; // Set the gameStarted flag to false initially

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            if (!gameWon) {
                if (gameStarted) {
                    if (!pacMan.checkCollisionWithWalls(currentMaze)) {
                        pacMan.move();
                    }

                    // Check if Pac-Man collects a dot
                    int row = pacMan.getY() / 20;
                    int col = pacMan.getX() / 20;
                    if (currentMaze.getMaze()[row][col] == 1) {
                        currentMaze.getMaze()[row][col] = -1; // Mark dot as collected
                        score += 10; // Increase score
                        
                        Sound.playDotSound("pacman_chomp.wav");
                    }

                    for (Ghost ghost : ghosts) {
                        ghost.move(currentMaze);
                        checkGhostCollisions();
                    }
                    
                    
                    if (isLevelComplete()) {
                        // Level is complete, handle level progression
                        goToNextLevel();
                    }
                    
                    
                    
                 
                    
                    
                    repaint(); // Ensures the component is repainted after the update
                
                }
                
            }
            
            
          }

			
            
        });
        
        
        
        timer.start();
        
        
        
        
       

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameStarted && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (gameOver) {
                        restartGame(); // Restart the game if it's over and space is pressed
                        Sound.playSound("pacman_beginning.wav");
                    } else {
                        gameStarted = true; // Start the game on pressing space
                    }
                } else {
                    // If the game has started, handle Pac-Man movements
                    if (gameStarted) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_LEFT:
                                pacMan.setDirection(Direction.LEFT);
                                break;
                            case KeyEvent.VK_RIGHT:
                                pacMan.setDirection(Direction.RIGHT);
                                break;
                            case KeyEvent.VK_UP:
                                pacMan.setDirection(Direction.UP);
                                break;
                            case KeyEvent.VK_DOWN:
                                pacMan.setDirection(Direction.DOWN);
                                break;
                        }
                    }
                }
            }
        });
        setFocusable(true); // To receive key events
        
        heartImage = new ImageIcon(getClass().getResource("/images/heart.png")).getImage();
    }
    
    
    private void checkGhostCollisions() {
    	for (Ghost ghost : ghosts) {
            // Check for collision with each ghost
            if (Math.abs(pacMan.getX() - ghost.getX()) < 20 && Math.abs(pacMan.getY() - ghost.getY()) < 20) {
                handleCollision();
                break; // Break out of the loop after handling the collision
            }
        }
    }
    
    
    private boolean isLevelComplete() {
        for (int row = 0; row < currentMaze.getMaze().length; row++) {
            for (int col = 0; col < currentMaze.getMaze()[row].length; col++) {
                if (currentMaze.getMaze()[row][col] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    

    private void handleCollision() {
    	
    	
    	
    	Sound.playCollisionSound("pacman_death.wav");
        lives--;
        if (lives <= 0) {
        	gameOver = true;
            gameStarted = false;
        } else {
            // Reset Pac-Man and ghosts to their starting positions
            pacMan.resetPosition(); 
            for (Ghost ghost : ghosts) {
                ghost.resetPosition(); 
            }
        }
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK); // Set the background to black

        if (!gameStarted && !gameWon) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            if (gameOver) {
                g.drawString("Game Over!", 90, 120);
                g.drawString("Press SPACE to restart", 40, 150);
            } else {
                g.drawString("Press SPACE to start", 50, 150);
            }
            return; // Stop further rendering until the game starts
        }
        
        if (gameWon) {
            // Draw win screen
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("You Won!", 90, 150);

        

            return; // Skip drawing the rest of the game
        }

        for (int row = 0; row < currentMaze.getMaze().length; row++) {
            for (int col = 0; col < currentMaze.getMaze()[0].length; col++) {
                if (currentMaze.getMaze()[row][col] == 0) {
                    g.setColor(Color.BLUE); // Wall color set to blue
                    g.fillRect(col * 20, row * 20, 20, 20);
                } else if (currentMaze.getMaze()[row][col] == 1) {
                    g.setColor(Color.WHITE); // Dot color set to white
                    g.fillOval(col * 20 + 8, row * 20 + 8, 4, 4);
                }
            }
        }

        g.drawImage(pacMan.getCurrentImage(), pacMan.getX(), pacMan.getY(), this);
        for (Ghost ghost : ghosts) {
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(), this);
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 15);
        
     // Display lives using heart images
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: ", 190, 15);
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, 240 + (i * 20), 3, 15, 15, this); // Display heart images as lives
        }
    }
    
    
    private void goToNextLevel() {
    	 currentLevelIndex++;
    	    if (currentLevelIndex < mazes.size()) {
    	        currentMaze = mazes.get(currentLevelIndex);
    	        resetGameForNewLevel();
    	    } else {
    	        // All levels are complete
    	        gameWon = true;
    	        gameStarted = false; // Stop the game
    	       
    	    }
    }
    
    
    
    private void resetGameForNewLevel() {
        // Reset Pac-Man and Ghosts to their starting positions
        pacMan.resetPosition();
        for (Ghost ghost : ghosts) {
            ghost.resetPosition();
        }
       
    }
    
    private void restartGame() {
        score = 0;
        lives = 3;
        gameOver = false;
        gameStarted = false;
        pacMan.resetPosition();
        for (Ghost ghost : ghosts) {
            ghost.resetPosition(); 
        }
        currentMaze.resetDots(); // Reset the collected dots in the maze
    }

    public static void main(String[] args) {
        List<Maze> mazes = new ArrayList<>();
        for (int level = 1; level <= 4; level++) {
            mazes.add(new Maze(level));
        }

        MazeWindow mazeWindow = new MazeWindow(mazes);
        
        

        JFrame frame = new JFrame("Pac-Man Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(310, 330);
        frame.add(mazeWindow);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
