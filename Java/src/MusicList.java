import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sawyer on 3/7/2016.
 */
public class MusicList extends JPanel {
    private static final int BORDER_SIZE = 10;
    private static final Insets insets = new Insets(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE);

    private static final Insets listInsets = new Insets(0, 0, 0, 0);

    private JList<Song> songList;
    private SongTable songTable;
    private MusicPlayer player;
    private JLabel searchLabel;
    private JTextField searchBox;


    public MusicList(Color BGColor) {
        // initialize panel
        setBackground(BGColor);
        setLayout(new GridBagLayout());

        // create the search area
        searchLabel = new JLabel("Search for songs, artists, or albums: ");
        searchBox = new JTextField();
        searchBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }
        });

        // create a new SongTable to hold the Songs
        songTable = new SongTable();
        songTable.setPreferredSize(new Dimension(10000, 10000));

        // add MouseListener to play songs when double-clicked
        songTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // if double-clicked, play-song
                if (e.getClickCount() == 2) {
                    if (player != null) {
                        player.play(getSelectedValue());
                        player.generateQueue();
                    }
                }
            }
        });


        // add JList to ScrollPane so the user can scroll through songs


        // add ScrollPane to panel
        addComponent(this, searchLabel, 0, 0, 1, 1, 1, .1, GridBagConstraints.CENTER, GridBagConstraints.NONE, false);
        addComponent(this, searchBox, 1, 0, 1, 1, 1, .1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, false);
        addComponent(this, songTable, 0, 1, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, false);
    }

    // returns the selected song
    public Song getSelectedValue() {
        return songList.getSelectedValue();
    }

    // adds a MusicPlayer so that MusicList can play a double-clicked song
    public void addPlayer(MusicPlayer player) {
        songTable.addPlayer(player);
        this.player = player;
    }

    // JList will set contents to the contents of the given Song list
    public void updateSongList(ArrayList<Song> newSongs) {
        songTable.updateSongList(newSongs);
    }

    public void search() {
        String searchString = searchBox.getText().toLowerCase().trim();
        ArrayList<Song> allSongs = player.getSongs();
        if (searchString.equals("")) {
            updateSongList(allSongs);
        } else {
            ArrayList<Song> matchedSongs = new ArrayList<Song>();
            for (Song song : allSongs) {
                String title = song.title.toLowerCase();
                String artist = song.artist.toLowerCase();
                String album = song.album.toLowerCase();
                if (title.contains(searchString) || artist.contains(searchString) || album.contains(searchString)) {
                    matchedSongs.add(song);
                }
            }

            updateSongList(matchedSongs);
        }
    }

    // highlights the selected song
    public void setSelectedSong(Song selected) {
        songTable.setSelectedValue(selected);
    }

    // adds components
    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, boolean isList) {
        GridBagConstraints gbc;
        if (isList) {
            gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, listInsets, 0, 0);
        } else {
            gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets, 0, 0);
        }
        container.add(component, gbc);
    }
}
