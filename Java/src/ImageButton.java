import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Sawyer on 3/10/2016.
 */
public class ImageButton extends JPanel implements MouseListener {
    private java.util.List<MouseListener> listeners = new ArrayList<MouseListener>();
    private JLabel picLabel;

    public ImageButton(String imagePath) {
        try {
            BufferedImage myPicture = ImageIO.read(new File(imagePath));
            picLabel = new JLabel(new ImageIcon(myPicture));
            /*picLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("test");
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });*/
            add(picLabel);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("asdbkajsdbkj");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void addMouseListener(MouseListener toAdd) {
        listeners.add(toAdd);
    }

}
