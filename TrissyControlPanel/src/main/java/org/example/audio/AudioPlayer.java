package org.example.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private AudioInputStream audioInputStream;


    public AudioPlayer(){
        //Costruttore vuoto

    }

    public void play(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream= AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
        clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public void stop(){
        clip.stop();
        clip.close();
    }




}
