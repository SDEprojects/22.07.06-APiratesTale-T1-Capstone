package com.company.models;
import com.apps.util.Prompter;
import com.company.client.GameMain;


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
    private float currentVolume = 0;
    private FloatControl floatControl;
    private boolean fxOff;
    private GameMain gm;

    public Music(GameMain gm){
        this.gm = gm;
    }

    public void playMusic(String musicLocation) {

        try {
            //using bufferedInputStream and file getter to use classpath/resources root
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(fileGetter.fileGetter(musicLocation)));
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            setFloatControl((FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN));
            //Added loop
            if (musicLocation.equals(gm.getUi().getMusicFile())){
                audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                playCompleted = false;
            }
            else{
                if (!fxOff){
                    playCompleted = false;
                    audioClip.start();
                }
            }
            getFloatControl().setValue(getCurrentVolume());

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

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

    }

    public void volumeUp(){
        currentVolume += 1.0f;
        if (getCurrentVolume() > 6.0f){
            setCurrentVolume(6.0f);
        }
        getFloatControl().setValue(getCurrentVolume());
    }

    public void volumeDown(){
        currentVolume -= 1.0f;
        if (getCurrentVolume() < -80.0f){
            setCurrentVolume(-80.0f);
        }
        getFloatControl().setValue(getCurrentVolume());
    }

    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

    }

    public void stopMusic(String musicLocation) {
        playCompleted = true;
    }

    public void stopFx(){
        setFxOff(true);
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(float currentVolume) {
        this.currentVolume = currentVolume;
    }

    public FloatControl getFloatControl() {
        return floatControl;
    }

    public void setFloatControl(FloatControl floatControl) {
        this.floatControl = floatControl;
    }

    public boolean isFxOff() {
        return fxOff;
    }

    public void setFxOff(boolean fxOff) {
        this.fxOff = fxOff;
    }
}