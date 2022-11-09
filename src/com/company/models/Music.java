package com.company.models;
import com.apps.util.Prompter;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;


public class Music {

    FileGetter fileGetter = new FileGetter();
    Scanner scanner = new Scanner(System.in);
    File file = new File("");
    boolean playCompleted;

    public void playMusic(String musicLocation) {

        try {
            //using bufferedInputStream and file getter to use classpath/resources root
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(fileGetter.fileGetter(musicLocation)));
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            //Added loop
            if (musicLocation.equals("pirate-music.wav")){
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                playCompleted = false;
            }
            if (musicLocation.equals("arrr.wav")){
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                Thread.sleep(1000);
                playCompleted = true;
            }

           // playCompleted = false;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!playCompleted) {
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    audioClip.close();
                }
            });
            thread.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

    }

    public void stopMusic(String musicLocation) {
        playCompleted = true;
    }

}