package Pacman;


import java.io.IOException;

import javax.sound.sampled.*;


public class Sound {

	
	private static Clip dotClip = null;

    public static void playDotSound(String filename) {
        try {
            if (dotClip == null) {
                // Load the sound file if it's not already loaded
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    Sound.class.getResource("/sounds/" + filename));
                dotClip = AudioSystem.getClip();
                dotClip.open(audioInputStream);
            }

            if (!dotClip.isRunning()) {
                dotClip.setFramePosition(0); // Rewind to the beginning
                dotClip.start(); // Start playing
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    
    private static Clip collisionClip = null;

    public static void playCollisionSound(String filename) {
        try {
            if (collisionClip == null) {
                // Load the sound file if it's not already loaded
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    Sound.class.getResource("/sounds/" + filename));
                collisionClip = AudioSystem.getClip();
                collisionClip.open(audioInputStream);
            }

            if (!collisionClip.isRunning()) {
                collisionClip.setFramePosition(0); // Rewind to the beginning
                collisionClip.start(); // Start playing
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void playSound(String filename) {
        try {
            // Load the sound file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                Sound.class.getResource("/sounds/" + filename));
            
            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();
            
            // Open audio clip and load samples from the audio input stream
            clip.open(audioInputStream);
            
            // Start playing the sound
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
	
}
