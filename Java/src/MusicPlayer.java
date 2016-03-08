import java.io.IOException;
import java.io.PrintWriter;
import javafx.util.Duration;
import java.util.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Created by Sawyer on 3/6/2016.
 */
public class MusicPlayer {
    private ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<String> filePaths = new ArrayList<String>();
    private Queue<Song> nextMusic = new LinkedList<Song>();
    private Stack<Song> prevMusic = new Stack<Song>();
    private Song curSong;
    private MediaPlayer mediaPlayer;
    private FileParser fp;

    public MusicPlayer() {
        fp = new FileParser();
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void play(Song newSong) {
        //prevMusic.push(curSong);
        if (mediaPlayer != null) {
            stop();
        }
        curSong = newSong;
        System.out.println("Now playing: " + curSong);
        Media hit = new Media("file:///" + encode(curSong.filePath));//.replace(" ", "%20").replace("\\", "/"));
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void seek(int value) {
        if (mediaPlayer != null) {
            double newSeekTime = curSong.duration.getSeconds() * value / 100;
            System.out.println(newSeekTime);
            Duration newDuration = new Duration(newSeekTime * 1000);
            mediaPlayer.seek(newDuration);
        }
    }

    public int getPercentage() {
        if (mediaPlayer != null) {
            float thisDuration = curSong.duration.getSeconds();
            float curTime = (float) mediaPlayer.getCurrentTime().toSeconds();
            /*System.out.println("This Duration: " + thisDuration);
            System.out.println("Current Time: " + curTime);
            System.out.println("Percentage: " + (curTime / thisDuration));*/
            return Math.round(curTime / thisDuration * 100);
        }

        return 0;
    }

    public void playPrevSong() {

    }

    public void playNextSong() {

    }

    public void addMusicFolder(String folder) throws IOException {
        filePaths.add(folder);
        //PrintWriter writer = new PrintWriter("musicFolders.txt", "UTF-8");
        for (String filePath : filePaths) {
            ArrayList<String> songPaths = fp.parse(filePath);
            for (String songPath : songPaths) {
                Song song = new Song(songPath);
                song.findMetadata();
                songs.add(song);
            }

            //writer.println(filePath);
        }
        //writer.close();
    }

    public Song[] getSongs() {
        Collections.sort(songs);
        Song[] songArray = new Song[songs.size()];
        return songs.toArray(songArray);
    }

    public static String encode(String filePath) {
        return filePath.replace(" ", "%20").replace("\\", "/").replace("[", "%5B").replace("]", "%5D");
    }
}
