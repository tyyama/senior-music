import java.io.IOException;
import javafx.util.Duration;
import java.util.*;
import javafx.scene.media.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Created by Sawyer on 3/6/2016.
 */
public class MusicPlayer {
    private ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<String> filePaths = new ArrayList<String>();
    private Deque<Song> nextMusic = new LinkedList<Song>();
    private Stack<Song> prevMusic = new Stack<Song>();
    private Song curSong;
    private MediaPlayer mediaPlayer;
    private FileParser fp;
    private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

    public MusicPlayer() {
        fp = new FileParser();
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }

        stateChanged();
    }

    public void play(Song newSong) {
        //prevMusic.push(curSong);
        if (mediaPlayer != null) {
            stop();
        }
        curSong = newSong;
        System.out.println("Now playing: " + curSong);
        Media hit = new Media("file:///" + encode(curSong.filePath));//.replace(" ", "%20").replace("\\", "/"));
        generateQueue();

        mediaPlayer = new MediaPlayer(hit);

        mediaPlayer.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                stateChanged();
            }
        });

        mediaPlayer.setOnStopped(new Runnable() {
            @Override
            public void run() {
                stateChanged();
            }
        });

        mediaPlayer.setOnPaused(new Runnable() {
            @Override
            public void run() {
                stateChanged();
            }
        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (nextMusic.peekFirst() != null) {
                    prevMusic.push(curSong);
                    curSong = nextMusic.removeFirst();
                    play(curSong);
                }
            }
        });

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

    public void seek(double value) {
        if (mediaPlayer != null) {
            System.out.println(value);
            double newSeekTime = curSong.duration.getSeconds() * value;
            System.out.println(newSeekTime);
            Duration newDuration = new Duration(newSeekTime * 1000);
            mediaPlayer.seek(newDuration);
        }
    }

    public double getPercentage() {
        if (mediaPlayer != null) {
            float thisDuration = curSong.duration.getSeconds();
            float curTime = (float) mediaPlayer.getCurrentTime().toSeconds();
            /*System.out.println("This Duration: " + thisDuration);
            System.out.println("Current Time: " + curTime);
            System.out.println("Percentage: " + (curTime / thisDuration));*/
            return curTime / thisDuration;
        }

        return 0;
    }

    public void playPrev() {

    }

    public void playNext() {

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

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public static String encode(String filePath) {
        return filePath.replace(" ", "%20").replace("\\", "/").replace("[", "%5B").replace("]", "%5D");
    }

    public void addChangeListener(ChangeListener toAdd) {
        listeners.add(toAdd);
    }

    public void stateChanged() {
        for (ChangeListener listener : listeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    public String getStatus() {
        return mediaPlayer.getStatus().toString();
    }

    public Duration getDuration() {
        if (mediaPlayer != null)
            return mediaPlayer.getStopTime();
        return null;
    }

    public Duration getCurrentTime() {
        if (mediaPlayer != null)
            return mediaPlayer.getCurrentTime();
        return null;
    }

    private void generateQueue() {
        int startingIndex = songs.indexOf(curSong) + 1;
        if (startingIndex >= songs.size()) {
            startingIndex = 0;
        }
        nextMusic.clear();
        ListIterator<Song> it = songs.listIterator(startingIndex);
        while (it.hasNext()) {
            nextMusic.addFirst(it.next());
        }
    }
}
