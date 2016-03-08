import javax.swing.*;
import java.awt.*;

/**
 * Created by Sawyer on 3/7/2016.
 */
public class MusicList extends JPanel {
    private JList<Song> songList;


    public MusicList() {
        songList = new JList<Song>();
        songList.setModel(new DefaultListModel());
        JScrollPane s = new JScrollPane(songList);
        s.setPreferredSize(new Dimension(600, 600));
        add(s);
        setSize(new Dimension(600, 600));
    }

    public Song getSelectedValue() {
        return songList.getSelectedValue();
    }

    public void setSongs(Song[] songs) {
        DefaultListModel listModel = (DefaultListModel) songList.getModel();
        listModel.removeAllElements();

        for (Song song : songs) {
            listModel.addElement(song);
        }
    }
}
