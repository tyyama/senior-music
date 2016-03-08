import java.io.IOException;
import java.io.PrintWriter;
import javafx.util.Duration;
import java.util.*;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Created by Sawyer on 3/6/2016.
 */
public class MusicPlayer {
    private static ArrayList<Song> songs = new ArrayList<Song>();
    private static ArrayList<String> filePaths = new ArrayList<String>();
    private static Queue<Song> nextMusic = new LinkedList<Song>();
    private static Stack<Song> prevMusic = new Stack<Song>();
    private static Song curSong;
    private static MediaPlayer mediaPlayer;
    private static FileParser fp;

    public MusicPlayer() {
        fp = new FileParser();
    }

    public static void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public static void play(Song newSong) {
        //prevMusic.push(curSong);
        if (mediaPlayer != null) {
            stop();
        }
        curSong = newSong;
        System.out.println("Now playing: " + curSong);
        Media hit = new Media("file:///" + curSong.filePath.replace(" ", "%20").replace("\\", "/"));
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }

    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public static void seek(int value) {
        if (mediaPlayer != null) {
            double newSeekTime = curSong.duration.getSeconds() * value / 100;
            System.out.println(newSeekTime);
            Duration newDuration = new Duration(newSeekTime * 1000);
            mediaPlayer.seek(newDuration);
        }
    }

    public static int getPercentage() {
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

    public static void playPrevSong() {

    }

    public static void playNextSong() {

    }

    public static void addMusicFolder(String folder) throws IOException {
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

    public static Song[] getSongs() {
        Collections.sort(songs);
        Song[] songArray = new Song[songs.size()];
        return songs.toArray(songArray);
    }
}
