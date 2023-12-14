package Pacman;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Ghost {
	
	
	 private int x, y;
	 private Direction direction;
	 private Image image;
	 private Random random = new Random();

	    public Ghost(int startX, int startY) {
	    	 this.x = startX;
	         this.y = startY;
	         this.direction = getRandomDirection();
	         this.image = new ImageIcon(getClass().getResource("/images/ghost.gif")).getImage();
	       
	    
	    }
	
	    public void move(Maze maze) {
	        if (random.nextDouble() < 0.3) { // 30% chance to change direction randomly
	            direction = getRandomDirection();
	        }

	        int potentialNextX = getNextX(x, direction);
	        int potentialNextY = getNextY(y, direction);

	        if (!isValidMove(maze, potentialNextX, potentialNextY)) {
	            direction = findNewValidDirection(maze);
	            potentialNextX = getNextX(x, direction);
	            potentialNextY = getNextY(y, direction);
	        }

	        x = potentialNextX;
	        y = potentialNextY;
	    }

	    
	    public void resetPosition() {
	        this.x = 100;
	        this.y = 100;
	        // You might also want to reset the ghost's direction
	        this.direction = getRandomDirection(); // or set to a default direction
	        
	    }
	    
	    
	    private Direction findNewValidDirection(Maze maze) {
	        Direction newDirection;
	        do {
	            newDirection = getRandomDirection();
	        } while (newDirection == direction || !isValidMove(maze, getNextX(x, newDirection), getNextY(y, newDirection)));

	        return newDirection;
	    }

	  

	    private int getNextX(int currentX, Direction dir) {
	        switch (dir) {
	            case LEFT: return currentX - 20;
	            case RIGHT: return currentX + 20;
	            default: return currentX;
	        }
	    }

	    private int getNextY(int currentY, Direction dir) {
	        switch (dir) {
	            case UP: return currentY - 20;
	            case DOWN: return currentY + 20;
	            default: return currentY;
	        }
	    }

	    private boolean isValidMove(Maze maze, int nextX, int nextY) {
	        // Convert nextX and nextY to row and column indexes
	        int col = nextX / 20; // Assuming each cell 
	        int row = nextY / 20; // Assuming each cell 

	        // Check if the next position is a wall (0)
	        return maze.getMaze()[row][col] != 0; // Valid move if not a wall
	    }
	    
	    private Direction getRandomDirection() {
	        Direction[] directions = Direction.values();
	        return directions[random.nextInt(directions.length)];
	    }
	
	
	 public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public Image getImage() {
	        return image;
	    }
	    
	    // Setter for the ghost's direction
	    public void setDirection(Direction direction) {
	        this.direction = direction;
	    }
}
