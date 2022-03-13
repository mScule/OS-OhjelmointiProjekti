package kasinoSimulaattori.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Musiikin hakeva ja käynnistävä apuluokka
 * 
 * @author Jonathan Methuen
 */
public class AudioPlayer {

	private static File musicPath = new File("music\\gamecorner.wav");
	private static Clip clip;
	private static AudioInputStream audioInput;

	/**
	 * Aloittaa musiikin soittamisen
	 */
	public static void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		if(clip != null && clip.isRunning()){
			stopMusic();
		}

		if (musicPath.exists()) {
			audioInput = AudioSystem.getAudioInputStream(musicPath);
			clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.setFramePosition(0);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);

			System.out.println(clip.isRunning());
			System.out.println(clip.isActive());
			System.out.println(clip.isOpen());
		}
	}

	/**
	 * Lopettaa musiikin soittamisen
	 */
	public static void stopMusic() {
		clip.stop();
	}
}
