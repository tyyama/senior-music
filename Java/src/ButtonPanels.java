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
    // instance variables - replace the example below with your own
    private JButton play, resume, stop, pause;
    private JList<Song> songList;
    private JSlider progress;
    private JLabel progressLabel;
    private MusicPlayer player;
    private FileParser fp;
    private MusicList musicList;
    private boolean playing = false;

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
        progress=new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        progress.addMouseListener(this);
        ProgressUpdate progressUpdate = new ProgressUpdate(progress, player);
        progressUpdate.start();
        progressLabel= new JLabel("Progress");
        add(play);
        add(pause);
        add(stop);
        add(progress);
        add(progressLabel);
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
                pause.setText("Resume");
            } else {
                player.play();
                pause.setText("Pause");
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
    
    public void mouseReleased(MouseEvent e){
        if (e.getSource() == progress) {
            //System.out.println("ran");
            player.seek(progress.getValue());
            //progress.setValue(player.getPercentage());
        }
    }
    
    public void mousePressed(MouseEvent e){
    
    
    }
    
    public void mouseClicked(MouseEvent e){
     
    }

    public void stateChanged(ChangeEvent e){

    }


}
