package cz.vsb;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundManager {
    // Sound effects
    private static final AudioClip PICKUP_SOUND = loadAudioClip("/sounds/collectBoostSound.mp3");
    private static final AudioClip DEATH_SOUND = loadAudioClip("/sounds/deathSound.mp3");
    private static final AudioClip EXPLOSION_SOUND = loadAudioClip("/sounds/explosionSound.mp3");
    private static final AudioClip CLICK_SOUND = loadAudioClip("/sounds/clickSound.mp3");
    // background music
    private static MediaPlayer backgroundMusic;

    private static AudioClip loadAudioClip(String path) {
        URL resource = SoundManager.class.getResource(path);
        if (resource == null) {
            System.out.println("Chyba: Nenalezen soubor " + path);
            return null;
        }
        return new AudioClip(resource.toString());
    }

    public static void playPickupSound() {
        if (PICKUP_SOUND != null) PICKUP_SOUND.play();
    }

    public static void playClickSound() {
        if (PICKUP_SOUND != null) CLICK_SOUND.play();
    }

    public static void playDeathSound() {

        if (DEATH_SOUND != null) DEATH_SOUND.play();
    }

    public static void playExplosionSound() {
        if (DEATH_SOUND != null) {
            EXPLOSION_SOUND.setVolume(0.03);
            EXPLOSION_SOUND.play();
        }
    }


    public static void playBackgroundMusic() {
        if (backgroundMusic == null) {
            String musicPath = SoundManager.class.getResource("/sounds/backgroundSound.mp3").toString();
            Media media = new Media(musicPath);
            backgroundMusic = new MediaPlayer(media);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // loop
            backgroundMusic.setVolume(0.5); // volume 50 %
        }
        backgroundMusic.play();
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }
}
