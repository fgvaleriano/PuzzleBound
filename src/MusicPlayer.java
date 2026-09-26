package src;

import javax.sound.sampled.*;
import java.io.IOException;

public class MusicPlayer {

    private Clip clip;
    private int currentTrack = 0;
    private String songPath = "/resource/Music/A-Lonely-Cherry-Tree-_.wav";

    public void playMusic() {

        if(currentTrack == 0){
            songPath = "/resource/Music/A-Lonely-Cherry-Tree-_.wav";
        } else if(currentTrack == 1){
            songPath = "/resource/Music/An-Ugly-Heart-But-It-does-Beats.wav";
        }

        try {
            // Load the sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(getClass().getResource(songPath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            
            new Thread(() -> {
                try {
                    Thread.sleep(clip.getMicrosecondLength() / 1000); // microsecconds to milliseconds
                    currentTrack++;

                    if (currentTrack >= 2) {
                        currentTrack = 0; // reset to first track
                    }

                    playMusic(); // play next track
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void victory(){
         try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(getClass().getResource("/resource/Music/victory.wav"));
            Clip clip2 = AudioSystem.getClip();
            clip2.open(audioInput);

            // Adjust volume
            if (clip2.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // Lower the volume
            }

            clip2.start(); // Play the sound

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void lose(){
         try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(getClass().getResource("/resource/Music/game over.wav"));
            Clip clip1 = AudioSystem.getClip();
            clip1.open(audioInput);
            clip1.start(); // Start playing the sound

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void jump(){
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(getClass().getResource("/resource/Music/Jump.wav"));
            Clip clip2 = AudioSystem.getClip();
            clip2.open(audioInput);

            // Adjust volume
            if (clip2.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // Lower the volume
            }

            clip2.start(); // Play the sound

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void clicked(){
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(getClass().getResource("/resource/Music/button.wav"));
            Clip clip1 = AudioSystem.getClip();
            clip1.open(audioInput);
            clip1.start(); // Start playing the sound

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
