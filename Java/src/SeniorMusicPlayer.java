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
        win.setSize(700,700);
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MusicList musicList = new MusicList();
        musicList.setSize(new Dimension(600, 600));
        ButtonPanels buttons=new ButtonPanels(musicList);
        buttons.setSize(new Dimension(50,50));
        win.getContentPane().add(buttons,BorderLayout.SOUTH);
        win.getContentPane().add(musicList, BorderLayout.CENTER);
        win.setVisible(true);
    
    }
}
