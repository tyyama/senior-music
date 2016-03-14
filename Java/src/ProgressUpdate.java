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
        // store given values
        this.progress = progress;
        this.player = player;
        this.PROGRESS_RES = PROGRESS_RES;
        this.progressLabel = progressLabel;
    }

    @Override
    public void run() {
        // runs forever when MusicPlayer is created
        while (true) {
            // set the position  of ProgressBar to MusicPlayer's current percentage
            progress.setValue((int) Math.round(player.getPercentage() * PROGRESS_RES));

            // updates position and text when playing or paused
            if (status.equals("PLAYING") || status.equals("PAUSED")) {
                try {
                    // get total Song duration
                    int durationTotal = (int) player.getDuration().toSeconds();
                    String durationMinutes = Integer.toString(durationTotal / 60);
                    String durationSeconds = getFormattedSeconds(durationTotal % 60);

                    // get current Song time
                    int curTime = (int) player.getCurrentTime().toSeconds();
                    String curTimeMinutes = Integer.toString(curTime / 60);
                    String curTimeSeconds = getFormattedSeconds(curTime % 60);

                    // updates the label with the current time values
                    progressLabel.setText(curTimeMinutes + ":" + curTimeSeconds + " / " + durationMinutes + ":" + durationSeconds);
                } catch (NullPointerException e) {

                }
            } else { // song is stopped
                progressLabel.setText("0:00/0:00");
            }

            try {
                // pause for set amount of time
                Thread.sleep(5);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private String getFormattedSeconds(int seconds) {
        if (seconds < 10) {
            return "0" + seconds;
        } else {
            return "" + seconds;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
