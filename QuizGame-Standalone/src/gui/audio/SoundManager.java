package gui.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private static Clip backgroundMusicClip;
    private static boolean isMuted = false;
    private static FloatControl volumeControl;
    private static final float DEFAULT_VOLUME = -10.0f; // Adjust as needed (range: -80.0f to 6.0f)

    /**
     * Plays background music in a loop
     * @param musicFilePath Path to the audio file (supports .wav format)
     */
    public static void playBackgroundMusic(String musicFilePath) {
        try {
            // Stop any existing music
            stopBackgroundMusic();

            // Load the audio file
            File audioFile = new File(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get a clip resource
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);

            // Get volume control
            if (backgroundMusicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(DEFAULT_VOLUME);
            }

            // Loop the music continuously
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);

            // Apply mute state if already muted
            if (isMuted) {
                muteBackgroundMusic();
            } else {
                backgroundMusicClip.start();
            }

            System.out.println("[SoundManager] Background music started: " + musicFilePath);

        } catch (UnsupportedAudioFileException e) {
            System.err.println("[SoundManager] Unsupported audio file format: " + musicFilePath);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("[SoundManager] Error loading audio file: " + musicFilePath);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("[SoundManager] Audio line unavailable");
            e.printStackTrace();
        }
    }

    /**
     * Stops the background music
     */
    public static void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
            System.out.println("[SoundManager] Background music stopped");
        }
    }

    /**
     * Toggles mute/unmute for background music
     * @return true if now muted, false if now unmuted
     */
    public static boolean toggleMute() {
        isMuted = !isMuted;

        if (backgroundMusicClip != null) {
            if (isMuted) {
                muteBackgroundMusic();
            } else {
                unmuteBackgroundMusic();
            }
        }

        System.out.println("[SoundManager] Music " + (isMuted ? "MUTED" : "UNMUTED"));
        return isMuted;
    }

    /**
     * Mutes the background music
     */
    private static void muteBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    /**
     * Unmutes the background music
     */
    private static void unmuteBackgroundMusic() {
        if (backgroundMusicClip != null && !backgroundMusicClip.isRunning()) {
            backgroundMusicClip.start();
        }
    }

    /**
     * Checks if music is currently muted
     * @return true if muted, false otherwise
     */
    public static boolean isMuted() {
        return isMuted;
    }

    /**
     * Sets the volume of background music
     * @param volume Volume level in decibels (range: -80.0f to 6.0f)
     */
    public static void setVolume(float volume) {
        if (volumeControl != null) {
            // Clamp volume to valid range
            float clampedVolume = Math.max(volumeControl.getMinimum(),
                    Math.min(volumeControl.getMaximum(), volume));
            volumeControl.setValue(clampedVolume);
            System.out.println("[SoundManager] Volume set to: " + clampedVolume + " dB");
        }
    }

    /**
     * Plays a sound effect once (non-looping)
     * @param soundFilePath Path to the sound effect file
     */
    public static void playSoundEffect(String soundFilePath) {
        try {
            File audioFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Play once and close when done
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();

        } catch (Exception e) {
            System.err.println("[SoundManager] Error playing sound effect: " + soundFilePath);
            e.printStackTrace();
        }
    }
}