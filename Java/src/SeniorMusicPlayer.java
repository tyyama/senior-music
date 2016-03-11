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
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Color BGColor = Color.getHSBColor(0, 0, .07f);
        JFrame win=new JFrame("Music Player For Senior Citizens");
        win.setSize(900,700);
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MusicList musicList = new MusicList(800, 550, BGColor);
        VolumeSlider vSlider = new VolumeSlider(BGColor);

        ButtonPanels buttons=new ButtonPanels(musicList, vSlider, BGColor);
        buttons.setSize(new Dimension(50,50));

        win.setBackground(Color.YELLOW);

        win.getContentPane().add(buttons,BorderLayout.SOUTH);
        win.getContentPane().add(vSlider, BorderLayout.EAST);
        win.getContentPane().add(musicList, BorderLayout.CENTER);
        win.setVisible(true);
    
    }
}
