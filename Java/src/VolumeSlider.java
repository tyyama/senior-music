import javafx.scene.control.TextFormatter;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
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
    // instance variables - replace the example below with your own
    private JSlider volumeSlider;
    private JLabel volume;
    private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

    /**
     * Constructor for objects of class VolumeSlider
     */
    public VolumeSlider()
    {
        volumeSlider=new JSlider(JSlider.VERTICAL, 0, 100, 50);
        volumeSlider.addChangeListener(this);
        volume=new JLabel("50");
        add(volumeSlider);
        add(volume);
        
        
    }
    
     public void actionPerformed(ActionEvent e){
    
    }
    
    public void mouseExited(MouseEvent e){
    
    }
    
    public void mouseEntered(MouseEvent e){
    
    }
    
    public void mouseReleased(MouseEvent e){
    
    }
    
    public void mousePressed(MouseEvent e){
    
    
    }
    
    public void mouseClicked(MouseEvent e){
    
    
    }
    
    public void stateChanged(ChangeEvent e){
        if(e.getSource() == volumeSlider){
            //System.out.println("skfaksdfhk");
            volume.setText(Integer.toString(volumeSlider.getValue()));
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
    
}
