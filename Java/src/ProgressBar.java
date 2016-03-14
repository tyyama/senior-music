import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sawyer on 3/12/2016.
 */
public class ProgressBar extends JPanel implements ChangeListener, MouseListener {
    private static final int BORDER_SIZE = 10;
    private static final Insets insets = new Insets(BORDER_SIZE, BORDER_SIZE, 0, BORDER_SIZE);

    private int PROGRESS_RES;
    private JSlider progress;
    private JLabel progressLabel;
    private ProgressUpdate progressUpdate;
    private MusicPlayer player;
    private boolean slidingProgress = false;

    public ProgressBar(int PROGRESS_RES, Color BGColor, Color FGColor) {
        // initializes panel
        this.PROGRESS_RES = PROGRESS_RES;
        setLayout(new GridBagLayout());
        setBackground(BGColor);

        // add slider and progress label
        progress = new JSlider(JSlider.HORIZONTAL, 0, PROGRESS_RES, 0);
        progress.setEnabled(false);

        progressLabel = new JLabel("0:00/0:00");
        progressLabel.setForeground(FGColor);

        // changes background of slider and progress label
        progress.setBackground(BGColor);
        progressLabel.setBackground(BGColor);

        // adds elements to panel
        addComponent(this, progress, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, progressLabel, 1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        // adds listeners to progress bar
        progress.addMouseListener(this);
        progress.addChangeListener(this);
    }

    // enabled/disabled the progress slider
    public void setEnabled(boolean enabled) {
        progress.setEnabled(enabled);
    }

    // adds a music player and links up music player and volume slider
    public void addMusicPlayer(MusicPlayer player) {
        this.player = player;
        progressUpdate = new ProgressUpdate(progress, player, PROGRESS_RES, progressLabel);
        progressUpdate.start();
    }

    // returns the value of the progress bar
    public int getValue() {
        return progress.getValue();
    }

    // passes the current status to progressUpdate
    public void setStatus(String status) {
        progressUpdate.setStatus(status);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // runs when progress slider is moved by the user
        if (e.getSource() == progress && slidingProgress) {
            int curValue = progress.getValue();

            // do not let the user scroll all the way to the end, because then the next song will play
            if (curValue == PROGRESS_RES)
                curValue -= 1;

            player.seek((double) curValue / PROGRESS_RES);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // user starts sliding progress bar
        if (e.getSource() == progress) {
            slidingProgress = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // user is not sliding progress bar anymore
        if (e.getSource() == progress) {
            slidingProgress = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // adds components
    private void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx,  weighty, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
}
