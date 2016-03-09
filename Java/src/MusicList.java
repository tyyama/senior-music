import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;

/**
 * Created by Sawyer on 3/7/2016.
 */
public class MusicList extends JPanel {
    private JList<Song> songList;
    private MusicPlayer player;


    public MusicList() {
        songList = new JList<Song>();
        songList.setModel(new DefaultListModel());
        songList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (player != null) {
                        player.play(songList.getSelectedValue());
                    }
                }
            }
        });

        //buildLayout();
        JScrollPane s = new JScrollPane(songList);
        s.setPreferredSize(new Dimension(600, 600));
        add(s);
        setSize(new Dimension(600, 600));
    }

    public Song getSelectedValue() {
        return songList.getSelectedValue();
    }

    private void  buildLayout() {

    }

    public void addPlayer(MusicPlayer player) {
        this.player = player;
    }

    public void setSongs(Song[] songs) {
        DefaultListModel listModel = (DefaultListModel) songList.getModel();
        listModel.removeAllElements();

        for (Song song : songs) {
            listModel.addElement(song);
        }
    }
}