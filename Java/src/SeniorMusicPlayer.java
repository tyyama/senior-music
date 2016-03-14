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
    // default spacing for all panels
    private static final int PANEL_SPACING = 10;
    private static final Insets insets = new Insets(PANEL_SPACING, PANEL_SPACING, PANEL_SPACING, PANEL_SPACING);

    public static void main(String[] args) throws IOException {
        // initialize window
        Color BGColor = new Color(255, 137, 81); //Color.getHSBColor(0, 0, .07f);
        Color DarkBGColor = BGColor; // Color(230, 123, 73);
        Color FGColor = Color.BLACK; //new Color(255, 255, 255);
        JFrame win=new JFrame("EasyPlayer");
        win.setSize(1100,700);
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        win.setLayout(new GridBagLayout());
        win.getContentPane().setBackground(BGColor);

        // create elements
        MusicList musicList = new MusicList(BGColor);
        InfoPanel infoPanel = new InfoPanel(DarkBGColor, FGColor);
        ProgressBar progressBar = new ProgressBar(1000000, DarkBGColor, FGColor);
        VolumeSlider vSlider = new VolumeSlider(DarkBGColor, FGColor);
        ButtonPanels buttons=new ButtonPanels(musicList, infoPanel, vSlider, progressBar, DarkBGColor);

        // Music List (top left)
        addComponent(win, musicList, 0, 0, 1, 2, .8, .9, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        // Controls and Info (top right)
        addComponent(win, infoPanel, 1, 0, 1, 1, .2, .8, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(win, buttons, 1, 1, 1, 1, .2, .2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        // Progress Bar (bottom left)
        addComponent(win, progressBar, 0, 2, 1, 1, .7, .1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        // Volume Slider (bottom right)
        addComponent(win, vSlider, 1,2, 1, 1, .3, .1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);

        win.setVisible(true);

    
    }

    // adds components
    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx,  weighty, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
}
