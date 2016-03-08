import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*; 
/**
 * Write a description of class VolumeSlider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VolumeSlider extends JPanel implements ActionListener, MouseListener, ChangeListener
{
    // instance variables - replace the example below with your own
    private JSlider volumeSlider;
    private JLabel volume;

    /**
     * Constructor for objects of class VolumeSlider
     */
    public VolumeSlider()
    {
        volumeSlider=new JSlider(JSlider.VERTICAL);
        volumeSlider.addChangeListener(this);
        volume=new JLabel("Volume");
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
        if(e.getSource()==volumeSlider){
            System.out.println("Volume changed");
        }
    
    }
    
    public JSlider getSlider(){
        return volumeSlider;
    
    }


    
}
