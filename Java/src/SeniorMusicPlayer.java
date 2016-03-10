import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;

/**
 * Write a description of class SeniorMusicPlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SeniorMusicPlayer  
{
    public static void main(String[] args) throws IOException {
        JFrame win=new JFrame("Music Player For Senior Citizens");
        win.setSize(800,700);
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MusicList musicList = new MusicList();
        VolumeSlider vSlider = new VolumeSlider();

        ButtonPanels buttons=new ButtonPanels(musicList, vSlider);
        buttons.setSize(new Dimension(50,50));
        win.getContentPane().add(buttons,BorderLayout.SOUTH);
        win.getContentPane().add(vSlider, BorderLayout.EAST);
        win.getContentPane().add(musicList, BorderLayout.CENTER);
        win.setVisible(true);
    
    }
}
