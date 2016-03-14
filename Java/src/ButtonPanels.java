import javafx.embed.swing.JFXPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
/**
 * Write a description of class ButtonPanels here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ButtonPanels extends JPanel implements ActionListener, MouseListener, ChangeListener 
{
    private static final int BORDER_SIZE = 10;
    private static final Insets insets = new Insets(BORDER_SIZE, BORDER_SIZE, 0, BORDER_SIZE);

    private JButton chooseMusic, shuffle;
    private JButton playPauseImage, prevImage, nextImage;
    private VolumeSlider volume;
    private ProgressBar progressBar;
    private InfoPanel infoPanel;
    private MusicPlayer player;
    private MusicList musicList;
    private boolean playing = false;

    /**
     * Constructor for objects of class ButtonPanels
     */
    public ButtonPanels(MusicList musicList, InfoPanel infoPanel, VolumeSlider volume, ProgressBar progressBar, Color BGColor) {
        // initializes the panel
        setBackground(BGColor);
        setLayout(new GridBagLayout());

        // creats a new MusicPlayer
        player = new MusicPlayer();

        // saves the given MusicList object and links it to the player
        this.musicList = musicList;
        musicList.addPlayer(player);
        this.musicList.updateSongList(player.getSongs());

        // links the player to the MusicList
        player.setMusicList(musicList);

        // saves the InfoPanel
        this.infoPanel = infoPanel;

        // saves the VolumeSlider and adds a ChangeListener
        this.volume = volume;
        volume.addChangeListener(this);

        // saves the ProgressBar and links it to the MusicPlayer
        this.progressBar = progressBar;
        progressBar.addMusicPlayer(player);

        // creates Buttons with custom images to control MusicPlayer and adds ActionListeners
        chooseMusic = generateImageButton("Choose");
        chooseMusic.addActionListener(this);

        shuffle = generateImageButton("Shuffle");
        shuffle.addActionListener(this);

        playPauseImage = generateImageButton("Play");
        playPauseImage.addActionListener(this);

        prevImage = generateImageButton("Previous");
        prevImage.addActionListener(this);

        nextImage = generateImageButton("Next");
        nextImage.addActionListener(this);

        // adds the buttons to the panel
        addComponent(this, prevImage, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, playPauseImage, 1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, nextImage, 2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, chooseMusic, 0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, shuffle, 2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        // adds a ChangeListener for when the MusicPlayer's status changes
        player.addChangeListener(this);
    }

    // runs whenever a button is clicked
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == playPauseImage){
            if (playing) {
                player.pause();
            } else {
                if (player.getCurrentSong() != null) {
                    player.play();
                } else {
                    player.play(musicList.getSelectedValue());
                }
            }
        } else if (e.getSource() == prevImage) {
            player.playPrev();
        } else if (e.getSource() == nextImage) {
            player.playNext();
        } else if (e.getSource() == chooseMusic) {
            // opens a JFileChooser for the user to add a music location
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int dialogueBox = chooser.showOpenDialog(null);

            // tells the MusicPlayer to add the chosen folder
            if (dialogueBox == JFileChooser.APPROVE_OPTION) {
                String filePath = chooser.getSelectedFile().getAbsolutePath();
                player.addMusicFolder(filePath);
            }
        } else if (e.getSource() == shuffle) {
            boolean isShuffling = player.changeShuffle();
            if (isShuffling) {
                // change to depressed button
            } else {
                // change to raised button
            }
        }
    }
    
    public void mouseExited(MouseEvent e){

    }
    
    public void mouseEntered(MouseEvent e){
    
    }

    public void mousePressed(MouseEvent e){

    }
    
    public void mouseReleased(MouseEvent e){

    }
    
    public void mouseClicked(MouseEvent e){
     
    }

    public void stateChanged(ChangeEvent e){
        // runs when player status changes
        if (e.getSource() == player) {
            String status = player.getStatus();
            if (status != null) {
                infoPanel.update(player.getCurrentSong());
                progressBar.setStatus(status);
                if (status.equals("PLAYING")) {
                    // sets volume to normalized volume level
                    player.setVolume((double) volume.getValue() / volume.getResolution());
                    progressBar.setEnabled(true);
                    changeButtonImage(playPauseImage, "Pause");
                    playing = true;
                } else if (status.equals("PAUSED")) {
                    progressBar.setEnabled(true);
                    playing = false;
                    changeButtonImage(playPauseImage, "Play");
                } else if (status.equals("STOPPED")) {
                    playing = false;
                    progressBar.setEnabled(false);
                    changeButtonImage(playPauseImage, "Play");
                }
            }
        } else if (e.getSource() == volume) { // runs when volume slider changes
            // sets volume to normalized volume level
            player.setVolume((double) volume.getValue() / volume.getResolution());
        }
    }

    // returns a new JButton with an image to click on
    private JButton generateImageButton(String ButtonName) {
        JButton button = new JButton();
        try {
            // look for the given image
            File fileHover =     new File("images/" + ButtonName + "Button/CS360_" + ButtonName + "_Hover.png");
            File filePressed =   new File("images/" + ButtonName + "Button/CS360_" + ButtonName + "_Pressed.png");
            File fileUnpressed = new File("images/" + ButtonName + "Button/CS360_" + ButtonName + "_UnPressed.png");

            Image imgHover =        ImageIO.read(fileHover);
            Image imgPressed =      ImageIO.read(filePressed);
            Image imgUnpressed =    ImageIO.read(fileUnpressed);

            // customer button with the image
            button.setIcon(new ImageIcon(imgUnpressed));
            button.setRolloverIcon(new ImageIcon(imgHover));
            button.setPressedIcon(new ImageIcon(imgPressed));

            button.setMargin(new Insets(0, 0, 0, 0));
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setBorder(null);
        } catch (IOException ex) { // image wasn't found
            System.err.println("Image not found!");
        }

        return button;
    }

    // changes the given button's image to the new image
    private void changeButtonImage(JButton button, String ButtonName) {
        try {
            // look for the given image
            File fileHover =     new File("images/" + ButtonName + "Button/CS360_" + ButtonName + "_Hover.png");
            File filePressed =   new File("images/" + ButtonName + "Button/CS360_" + ButtonName + "_Pressed.png");
            File fileUnpressed = new File("images/" + ButtonName + "Button/CS360_" + ButtonName + "_UnPressed.png");

            Image imgHover =        ImageIO.read(fileHover);
            Image imgPressed =      ImageIO.read(filePressed);
            Image imgUnpressed =    ImageIO.read(fileUnpressed);

            // customer button with the image
            button.setIcon(new ImageIcon(imgUnpressed));
            button.setRolloverIcon(new ImageIcon(imgHover));
            button.setPressedIcon(new ImageIcon(imgPressed));
        } catch (IOException ex) { // image wasn't found
            System.err.println("Image not found!");
        }
    }

    // adds component
    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx,  weighty, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
}
