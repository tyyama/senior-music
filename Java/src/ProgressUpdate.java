import javafx.scene.media.MediaPlayer;

import javax.swing.*;

/**
 * Created by Sawyer on 3/7/2016.
 */
public class ProgressUpdate extends Thread {
    JSlider progress;
    MusicPlayer player;
    int PROGRESS_RES;
    JLabel progressLabel;
    String status = "STOPPED";

    public ProgressUpdate(JSlider progress, MusicPlayer player, int PROGRESS_RES, JLabel progressLabel) {
        this.progress = progress;
        this.player = player;
        this.PROGRESS_RES = PROGRESS_RES;
        this.progressLabel = progressLabel;
    }

    @Override
    public void run() {
        while (true) {
            progress.setValue((int) Math.round(player.getPercentage() * PROGRESS_RES));

            if (status.equals("PLAYING") || status.equals("PAUSED")) {
                try {
                    int durationTotal = (int) player.getDuration().toSeconds();
                    String durationMinutes = Integer.toString(durationTotal / 60);
                    String durationSeconds = Integer.toString(durationTotal % 60);

                    if (durationTotal % 60 < 10) {
                        durationSeconds = "0" + durationSeconds;
                    }

                    int curTime = (int) player.getCurrentTime().toSeconds();
                    String curTimeMinutes = Integer.toString(curTime / 60);
                    String curTimeSeconds = Integer.toString(curTime % 60);

                    if (curTime % 60 < 10) {
                        curTimeSeconds = "0" + curTimeSeconds;
                    }

                    progressLabel.setText(curTimeMinutes + ":" + curTimeSeconds + " / " + durationMinutes + ":" + durationSeconds);
                } catch (NullPointerException e) {

                }
            } else {
                progressLabel.setText("Progress");
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
