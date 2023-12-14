package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MazeWindow extends JPanel {
    private Maze maze;
	private PacCharacter pacMan;

    public MazeWindow(Maze maze) {
        this.maze = maze;
        this.pacMan = new PacCharacter();
        
        
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 if (!pacMan.checkCollisionWithWalls(maze)) {
                     pacMan.move();
            	 }
                repaint(); // Ensures the component is repainted after the update
            }
        });
        timer.start();
        
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
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
        });
        setFocusable(true); // To receive key events
        
        
        
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK); // Set the background to black

        for (int row = 0; row < maze.getMaze().length; row++) {
            for (int col = 0; col < maze.getMaze()[0].length; col++) {
                if (maze.getMaze()[row][col] == 0) {
                    g.setColor(Color.BLUE); // Wall color set to blue
                    g.fillRect(col * 20, row * 20, 20, 20);
                } else if (maze.getMaze()[row][col] == 1) {
                    g.setColor(Color.WHITE); // Dot color set to white
                    g.fillOval(col * 20 + 8, row * 20 + 8, 4, 4);
                }
            }
        }
        
        g.drawImage(pacMan.getCurrentImage(), pacMan.getX(), pacMan.getY(), this);
    }
    
    


    public static void main(String[] args) {
    	 JFrame frame = new JFrame("Pac-Man Maze");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(310, 330); // Slightly larger to account for window decorations
         frame.add(new MazeWindow(new Maze()));
         frame.setLocationRelativeTo(null); // Center the window
         frame.setVisible(true);
    }
}

