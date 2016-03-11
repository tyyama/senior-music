import javafx.embed.swing.JFXPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
/**
 * Write a description of class ButtonPanels here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ButtonPanels extends JPanel implements ActionListener, MouseListener, ChangeListener 
{
    private static int PROGRESS_RES = 1000000;
    private static int VOLUME_RES;

    // instance variables - replace the example below with your own
    private JButton play, stop, pause, prev, next, playImage;
    private JSlider progress;
    private ProgressUpdate progressUpdate;
    private VolumeSlider volume;
    private JLabel progressLabel;
    private MusicPlayer player;
    private FileParser fp;
    private MusicList musicList;
    private boolean playing = false;
    private boolean slidingProgress = false;

    /**
     * Constructor for objects of class ButtonPanels
     */
    public ButtonPanels(MusicList musicList, VolumeSlider volume) throws IOException
    {
        new JFXPanel();
        player = new MusicPlayer();
        fp = new FileParser();
        player.addMusicFolder("Sample Music");
        this.musicList = musicList;
        musicList.addPlayer(player);
        this.musicList.setSongs(player.getSongs());

        this.volume = volume;
        volume.addChangeListener(this);
        VOLUME_RES = volume.getResolution();

        playImage = generateImageButton("images/play-test.png");
        playImage.addActionListener(this);

        play=new JButton("Play Selected Song");
        pause=new JButton("Pause");
        stop=new JButton("Stop");
        prev = new JButton("Previous");
        next = new JButton("Next");
        play.addActionListener(this);
        pause.addActionListener(this);
        stop.addActionListener(this);
        prev.addActionListener(this);
        next.addActionListener(this);

        prev.setEnabled(false);
        next.setEnabled(false);

        progress=new JSlider(JSlider.HORIZONTAL, 0, PROGRESS_RES, 0);
        progress.setEnabled(false);
        progress.addChangeListener(this);
        progress.addMouseListener(this);
        progressLabel= new JLabel("Progress");
        progressUpdate = new ProgressUpdate(progress, player, PROGRESS_RES, progressLabel);
        progressUpdate.start();

        //add(play);
        add(playImage);
        add(pause);
        add(stop);
        add(prev);
        add(next);
        add(progress);
        add(progressLabel);

        player.addChangeListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==play || e.getSource() == playImage){
            System.out.println("I clicked play");
            try {
                Song curSong = musicList.getSelectedValue();
                player.play(curSong);
                playing = true;
            } catch (NullPointerException error) {
                System.out.println("No song selected");
            }
            /*JFileChooser chooser = new JFileChooser();
            int dialogBox=chooser.showOpenDialog(null);
            if(dialogBox==JFileChooser.APPROVE_OPTION){
            File f=chooser.getSelectedFile();
            player.play(new Song("test.mp3"));*/
        }
        else if(e.getSource()==pause){
            if (playing) {
                player.pause();
            } else {
                player.play();
            }
            playing = !playing;
        } else if(e.getSource()==stop){
            playing = false;
            player.stop();
            
        } else if (e.getSource() == prev) {
            player.playPrev();
        } else if (e.getSource() == next) {
            player.playNext();
        } else if (e.getSource() == playImage) {
            System.out.println("TEST TEST TEST");
        }
    
    }
    
    public void mouseExited(MouseEvent e){

    }
    
    public void mouseEntered(MouseEvent e){
    
    }

    public void mousePressed(MouseEvent e){
        if (e.getSource() == progress) {
            slidingProgress = true;
        }
    }
    
    public void mouseReleased(MouseEvent e){
        if (e.getSource() == progress) {
            slidingProgress = false;
        }
    }
    
    public void mouseClicked(MouseEvent e){
     
    }

    public void stateChanged(ChangeEvent e){
        if (e.getSource() == player) {
            String status = player.getStatus();
            progressUpdate.setStatus(status);
            if (status.equals("PLAYING")) {
                playing = true;
                player.setVolume((double) volume.getValue() / VOLUME_RES);
                pause.setText("Pause");
                pause.setEnabled(true);
                stop.setEnabled(true);
                prev.setEnabled(true);
                next.setEnabled(true);
                progress.setEnabled(true);
            } else if (status.equals("PAUSED")) {
                playing = false;
                pause.setText("Resume");
                pause.setEnabled(true);
                stop.setEnabled(true);
                prev.setEnabled(true);
                next.setEnabled(true);
                progress.setEnabled(true);
            } else if (status.equals("STOPPED")) {
                playing = false;
                pause.setText("Pause");
                pause.setEnabled(false);
                stop.setEnabled(false);
                prev.setEnabled(false);
                next.setEnabled(false);
                progress.setEnabled(false);
            }
        } else if (e.getSource() == progress && slidingProgress) {
            player.seek((double) progress.getValue() / PROGRESS_RES);
        } else if (e.getSource() == volume) {
            player.setVolume((double) volume.getValue() / VOLUME_RES);
        }
    }

    private JButton generateImageButton(String imagePath) {
        JButton button = new JButton();
        try {
            //Image img = ImageIO.read(getClass().getResource(imagePath));
            File imgFile = new File(imagePath);

            if(imgFile.exists())
                System.out.println("Image file found!");
            else
                System.out.println("Image file not found!");

            Image img = ImageIO.read(imgFile);
            button.setIcon(new ImageIcon(img));
            // to remote the spacing between the image and button's borders
            button.setMargin(new Insets(0, 0, 0, 0));
            // to add a different background
            //button.setBackground();
            // to remove the border
            button.setBorder(null);
        } catch (IOException ex) {
        }

        return button;
    }
}
