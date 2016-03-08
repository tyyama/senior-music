import javafx.scene.media.MediaPlayer;

import javax.swing.*;

/**
 * Created by Sawyer on 3/7/2016.
 */
public class ProgressUpdate extends Thread {
    JSlider progress;
    MusicPlayer player;

    public ProgressUpdate(JSlider progress, MusicPlayer player) {
        this.progress = progress;
        this.player = player;
    }

    @Override
    public void run() {
        while (true) {
            progress.setValue(player.getPercentage());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
