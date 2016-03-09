import javafx.scene.media.MediaPlayer;

import javax.swing.*;

/**
 * Created by Sawyer on 3/7/2016.
 */
public class ProgressUpdate extends Thread {
    JSlider progress;
    MusicPlayer player;
    int PROGRESS_RES;

    public ProgressUpdate(JSlider progress, MusicPlayer player, int PROGRESS_RES) {
        this.progress = progress;
        this.player = player;
        this.PROGRESS_RES = PROGRESS_RES;
    }

    @Override
    public void run() {
        while (true) {
            progress.setValue((int) Math.round(player.getPercentage() * PROGRESS_RES));

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
