package Pacman;

import java.awt.Image;

public abstract class Game {

	 protected Image image;
	 protected int x, y;
	 protected Direction direction;


	public Game() {
		this.x = 0; // Initial X position
        this.y = 0; // Initial Y position
        this.direction = Direction.NONE; // Initial direction
     
    }

    // This move method can be overridden by subclasses if needed
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
    	    }// Do nothing if direction is NONE or an unrecognized value
    	    
    	    
    	    System.out.println("Moving: " + direction + " | New Position: x=" + x + ", y=" + y);
    }
    
   
        
}

