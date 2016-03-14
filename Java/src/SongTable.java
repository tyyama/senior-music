import net.didion.jwnl.data.Exc;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sawyer on 3/14/2016.
 */
public class SongTable extends JPanel {
    private static final Insets insets = new Insets(0, 0, 0, 0);
    private JTable infoTable;
    private SongTableModel model;
    private ArrayList<Song> songs;
    private MusicPlayer player;

    public SongTable() {
        setLayout(new GridBagLayout());

        infoTable = new JTable(new SongTableModel());
        infoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        infoTable.setFillsViewportHeight(true);
        infoTable.setAutoCreateRowSorter(true);

        infoTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (player != null) {
                        player.play(getSelectedValue());
                        player.generateQueue();
                    }
                }
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
        });

        TableColumn tcol = infoTable.getColumnModel().getColumn(3);
        infoTable.getColumnModel().removeColumn(tcol);

        model = (SongTableModel) infoTable.getModel();

        JScrollPane s = new JScrollPane(infoTable);

        addComponent(this, s, 0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
    }

    public Song getSelectedValue() {
        int selectedRowIndex = infoTable.getSelectedRow();
        Song selectedObject = (Song) model.getValueAt(selectedRowIndex, 3);
        return selectedObject;
    }

    public void updateSongList(ArrayList<Song> newSongs) {
        Collections.sort(newSongs);
        songs = newSongs;
        int rows = model.getRowCount();
        for(int i = rows - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        for (Song song : newSongs) {
            model.addRow(new Object[]{song.title, song.artist, song.album, song});
        }
        //Song[] songArray = newSongs.toArray(new Song[0]);
        //songList.setListData(songArray);
    }

    public void setSelectedValue(Song song) {
        int rows = model.getRowCount();
        for(int i = 0; i < rows; i++) {
            Song thisSong = (Song) model.getValueAt(i, 3);
            if (thisSong == song) {
                infoTable.setRowSelectionInterval(i, i);
                return;
            }
        }
    }

    public ArrayList<Song> getVisibleMusic() {
        return songs;
    }

    public void addPlayer(MusicPlayer player) {
        this.player = player;
    }

    private class SongTableModel extends DefaultTableModel {
        public SongTableModel() {
            addColumn("Title");
            addColumn("Artist");
            addColumn("Album");
            addColumn("Song");
        }

        public boolean isCellEditable(int row, int column){
            return false;
        }
    }

    private class SongTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx,  weighty, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
}
