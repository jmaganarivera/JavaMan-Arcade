package Pacman;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PacCharacter{

	 private Image rightImage;
	 private Image leftImage;
	 private Image upImage;
	 private Image downImage;
	 private Image currentImage; // The image that should currently be displayed
	 private int x, y; // Pac-Man's position
	 private Direction direction = Direction.NONE;
	 
	 public PacCharacter() {
	        // Load images
	        rightImage = new ImageIcon(getClass().getResource("/images/right.gif")).getImage();
	        leftImage = new ImageIcon(getClass().getResource("/images/left.gif")).getImage();
	        upImage = new ImageIcon(getClass().getResource("/images/up.gif")).getImage();
	        downImage = new ImageIcon(getClass().getResource("/images/down.gif")).getImage();
	        currentImage = new ImageIcon(getClass().getResource("/images/pacman.png")).getImage(); // Initial image
	        
	        // Set initial position (this should probably be the starting position in the maze)
	        x = 20; // For example, starting at (1, 1) in the maze
	        y = 20;
	    }
	 
	 
	 
	 
	 
	// Getters
	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public Image getCurrentImage() {
	        return currentImage;
	    }

	    // Setters
	    public void setX(int x) {
	        this.x = x;
	    }

	    public void setY(int y) {
	        this.y = y;
	    }

	    public void setCurrentImage(Image currentImage) {
	        this.currentImage = currentImage;
	    }
	    
	    public void move() {
	        
	    	 int speed = 20; // Speed of Pac-Man's movement; adjust as needed
	    	    switch (direction) {
	    	        case LEFT:
	    	            this.x -= speed;
	    	            break;
	    	        case RIGHT:
	    	            this.x += speed;
	    	            break;
	    	        case UP:
	    	            this.y -= speed;
	    	            break;
	    	        case DOWN:
	    	            this.y += speed;
	    	            break;
	    	        default:
	    	            break;
	    	    }
}

	    public void setDirection(Direction newDirection) {
	        this.direction = newDirection;
	        // Update the currentImage based on the new direction
	        switch (newDirection) {
	            case LEFT:
	                setCurrentImage(leftImage);
	                break;
	            case RIGHT:
	                setCurrentImage(rightImage);
	                break;
	            case UP:
	                setCurrentImage(upImage);
	                break;
	            case DOWN:
	                setCurrentImage(downImage);
	                break;
	            default:
	                break;
	        }
	    }
	    
	    
	    public boolean checkCollisionWithWalls(Maze maze) {
	        int nextX = x;
	        int nextY = y;
	        switch (direction) {
	            case LEFT: nextX -= 20; break;
	            case RIGHT: nextX += 20; break;
	            case UP: nextY -= 20; break;
	            case DOWN: nextY += 20; break;
	            default: break;
	    
	        }
	        
	        if (maze.getMaze()[nextY / 20][nextX / 20] == 0) {
	            // Wall is represented by '0' in the maze array
	            return true;
	        }
	        return false;
	    }
	    
	
}	    
	    

