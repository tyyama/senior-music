import javax.swing.*;
import java.awt.*;

/**
 * Created by Sawyer on 3/13/2016.
 */
public class InfoPanel extends JPanel {
    private static final int BORDER_SIZE = 10;
    private static final Insets insets = new Insets(BORDER_SIZE, BORDER_SIZE, 0, BORDER_SIZE);
    
    private String albumArtPath;
    private JLabel albumArtLabel, titleLabel, artistLabel, albumLabel;

    public InfoPanel(Color BGColor, Color FGColor) {
        // initializes panel
        setLayout(new GridBagLayout());
        setBackground(BGColor);
        setForeground(Color.getHSBColor(0, 0, .95f));

        // initializes labels
        albumArtLabel = new JLabel("Album Art Not Found");
        albumArtLabel.setForeground(FGColor);

        titleLabel = new JLabel("Title Not Found");
        titleLabel.setForeground(FGColor);

        artistLabel = new JLabel("Artist Name Not Found");
        artistLabel.setForeground(FGColor);

        albumLabel = new JLabel("Album Name Not Found");
        albumLabel.setForeground(FGColor);

        // adds labels to panel
        double albumArtHeight  = .9;
        double labelHeight = (1 - albumArtHeight) / 3;
        addComponent(this, albumArtLabel, 0, 0, 1, 1, 1, albumArtHeight, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComponent(this, titleLabel, 0, 1, 1, 1, 1, labelHeight, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComponent(this, artistLabel, 0, 2, 1, 1, 1, labelHeight, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComponent(this, albumLabel, 0, 3, 1, 1, 1, labelHeight, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    }

    // updates the information with the info from the given song
    public void update(Song song) {
        if (song != null) {
            titleLabel.setText(song.title);
            artistLabel.setText(song.artist);
            albumLabel.setText(song.album);
        }
    }

    // adds components
    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx,  weighty, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }


}
