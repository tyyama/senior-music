import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Write a description of class VolumeSlider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VolumeSlider extends JPanel implements ChangeListener
{
    private static final int BORDER_SIZE = 10;
    private static final Insets insets = new Insets(BORDER_SIZE, BORDER_SIZE, 0, BORDER_SIZE);

    private static int STARTING_VOLUME = 10;
    // instance variables - replace the example below with your own
    private JSlider volumeSlider;
    private JLabel volumeLabel;
    private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

    /**
     * Constructor for objects of class VolumeSlider
     */
    public VolumeSlider(Color BGColor, Color FGColor)
    {
        setBackground(BGColor);
        setLayout(new GridBagLayout());

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, STARTING_VOLUME);
        volumeSlider.addChangeListener(this);
        volumeSlider.setBackground(BGColor);

        volumeLabel = new JLabel("Volume: " + Integer.toString(STARTING_VOLUME));
        volumeLabel.setForeground(FGColor);

        addComponent(this, volumeSlider, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this, volumeLabel, 1, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH);
        //add(volumeSlider);
        //add(volumeLabel);
        
        
    }
    
    public void stateChanged(ChangeEvent e){
        if(e.getSource() == volumeSlider){
            volumeLabel.setText("Volume: " + Integer.toString(volumeSlider.getValue()));

            for (ChangeListener listener : listeners) {
                listener.stateChanged(new ChangeEvent(this));
            }
        }
    }
    
    public JSlider getSlider(){
        return volumeSlider;
    
    }

    public int getResolution() {
        return volumeSlider.getMaximum();
    }

    public void addChangeListener(ChangeListener toAdd) {
        listeners.add(toAdd);
    }

    public int getValue() {
        return volumeSlider.getValue();
    }

    public static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx,  weighty, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
    
}
