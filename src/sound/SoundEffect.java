package sound;

import file.IOFile;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public class SoundEffect {
    private FloatControl volumeControlBG;
    private float newVolumeBG, newVolumeEF;
    private int effect, background;

    public SoundEffect() {
        Vector<Integer> newVolume = IOFile.getVolume();
        this.background = newVolume.elementAt(0);
        this.effect = newVolume.elementAt(1);
        this.newVolumeEF = 70f * (float) (this.effect) / 100f - 64f;
        this.newVolumeBG = 70f * (float) (this.background) / 100f - 64f;

        System.out.println(this.newVolumeBG + " " + this.newVolumeEF);
    }

    public void playSoundMove() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File((System.getProperty("user.dir") + "/resource/sound/move.wav")));
            Clip soundEffectMove = AudioSystem.getClip();
            soundEffectMove.open(audioInputStream);
            FloatControl volumeMove = (FloatControl) soundEffectMove.getControl(FloatControl.Type.MASTER_GAIN);
            volumeMove.setValue(this.newVolumeEF);
            soundEffectMove.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Cannot read audio");
        }
    }

    public void playSounEffect(String name) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File((System.getProperty("user.dir") + "/resource/sound/" + name + ".wav")));
            Clip soundEffectMove = AudioSystem.getClip();
            soundEffectMove.open(audioInputStream);
            FloatControl volumeMove = (FloatControl) soundEffectMove.getControl(FloatControl.Type.MASTER_GAIN);
            volumeMove.setValue(this.newVolumeEF);
            soundEffectMove.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Cannot read audio " + name);
        }
    }

    public void playSoundCheckMate() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "/resource/sound/chieu.wav"));
            Clip soundEffectMove = AudioSystem.getClip();
            soundEffectMove.open(audioInputStream);
            FloatControl volumeMove = (FloatControl) soundEffectMove.getControl(FloatControl.Type.MASTER_GAIN);
            volumeMove.setValue(this.newVolumeEF);
            soundEffectMove.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Cannot read audio");
        }
    }

    public void playBackgroundMusic() {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "/resource/sound/bgsound.wav"));
            Clip soundTrack = AudioSystem.getClip();
            soundTrack.open(inputStream);
            this.volumeControlBG = (FloatControl) soundTrack.getControl(FloatControl.Type.MASTER_GAIN);
            this.volumeControlBG.setValue(this.newVolumeBG);
            soundTrack.loop(Clip.LOOP_CONTINUOUSLY);
            Thread musicThread = new Thread(new MusicRunnable(soundTrack));
            musicThread.start();
        } catch (Exception e) {
            System.out.println("Cannot read audio");
        }
    }

    public void setVolumeSoundTrack(int volume) {
        this.background = volume;
        this.newVolumeBG = 70f * (float) (volume) / 100f - 64f;
        this.volumeControlBG.setValue(this.newVolumeBG);
        IOFile.saveVolume(this.background, this.effect);
        System.out.println("New volumeBG: " + this.newVolumeBG);
    }

    public void setVolumeSoundEffect(int volume) {
        this.effect = volume;
        this.newVolumeEF = 70f * (float) (volume) / 100f - 64f;
        IOFile.saveVolume(this.background, this.effect);
        System.out.println("New volumeEF: " + this.newVolumeEF);
    }

    static class MusicRunnable implements Runnable {
        private final Clip clip;

        public MusicRunnable(Clip clip) {
            this.clip = clip;
        }

        @Override
        public void run() {
            try {
                clip.start();
                while (!Thread.interrupted())
                    Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }
}
