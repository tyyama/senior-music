import javafx.embed.swing.JFXPanel;

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
    private static final int PROGRESS_RES = 1000000;

    // instance variables - replace the example below with your own
    private JButton play, resume, stop, pause;
    private JList<Song> songList;
    private JSlider progress;
    private JLabel progressLabel;
    private MusicPlayer player;
    private FileParser fp;
    private MusicList musicList;
    private boolean playing = false;
    private boolean slidingProgress = false;

    /**
     * Constructor for objects of class ButtonPanels
     */
    public ButtonPanels(MusicList musicList) throws IOException
    {
        new JFXPanel();
        player = new MusicPlayer();
        fp = new FileParser();
        player.addMusicFolder("Sample Music");
        this.musicList = musicList;
        musicList.addPlayer(player);
        this.musicList.setSongs(player.getSongs());

        play=new JButton("Play Selected Song");
        pause=new JButton("Pause");
        stop=new JButton("Stop");
        play.addActionListener(this);
        pause.addActionListener(this);
        stop.addActionListener(this);
        progress=new JSlider(JSlider.HORIZONTAL, 0, PROGRESS_RES, 0);
        progress.addChangeListener(this);
        progress.addMouseListener(this);
        ProgressUpdate progressUpdate = new ProgressUpdate(progress, player, PROGRESS_RES);
        progressUpdate.start();
        progressLabel= new JLabel("Progress");
        add(play);
        add(pause);
        add(stop);
        add(progress);
        add(progressLabel);

        player.addChangeListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==play){
            System.out.println("I clicked play");
            Song curSong = musicList.getSelectedValue();
            player.play(curSong);
            playing = true;
            /*JFileChooser chooser = new JFileChooser();
            int dialogBox=chooser.showOpenDialog(null);
            if(dialogBox==JFileChooser.APPROVE_OPTION){
            File f=chooser.getSelectedFile();
            player.play(new Song("test.mp3"));*/
        }
        else if(e.getSource()==pause){
            if (playing) {
                player.pause();
                //pause.setText("Resume");
            } else {
                player.play();
                //pause.setText("Pause");
            }
            playing = !playing;
            System.out.println("I clicked pause");
        }
        
        else if(e.getSource()==stop){
            playing = false;
            player.stop();
            System.out.println("I clicked stop");
            
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
            System.out.println(status);
            if (status.equals("PLAYING")) {
                playing = true;
                pause.setText("Pause");
                pause.setEnabled(true);
                stop.setEnabled(true);
            } else if (status.equals("PAUSED")) {
                playing = false;
                pause.setText("Resume");
                pause.setEnabled(true);
                stop.setEnabled(true);
            } else if (status.equals("STOPPED")) {
                playing = false;
                pause.setText("Play");
                pause.setEnabled(false);
                stop.setEnabled(false);
            }
        } else if (e.getSource() == progress && slidingProgress) {
            //System.out.println("ran");
            System.out.println("***" + progress.getValue() + "***");
            player.seek((double) progress.getValue() / PROGRESS_RES);
            //progress.setValue(player.getPercentage());
        }
    }


}
