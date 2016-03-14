import java.io.*;

import javafx.embed.swing.JFXPanel;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import javafx.scene.media.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang.ObjectUtils;
import org.json.*;


/**
 * Created by Sawyer on 3/6/2016.
 */
public class MusicPlayer {
    private ArrayList<Song> songs = new ArrayList<Song>();
    private ArrayList<String> filePaths = new ArrayList<String>();
    private LinkedList<Song> nextMusic = new LinkedList<Song>();
    private LinkedList<Song> prevMusic = new LinkedList<Song>();
    private MusicList musicList;
    private Song curSong;
    private MediaPlayer mediaPlayer;
    private FileParser fp;
    private List<ChangeListener> listeners = new ArrayList<ChangeListener>();
    private boolean shuffle = false;

    public MusicPlayer() {
        // must initialize a JFXPanel for MediaPlayer to work
        new JFXPanel();

        fp = new FileParser();
        try { // read in music folders and parse for songs
            FileInputStream musicFile = new FileInputStream("musicFolders.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(musicFile));

            // loops through found music folders
            String line;
            while ((line = br.readLine()) != null) {
                filePaths.add(line);

                // get list of song filepaths from line
                ArrayList<String> songPaths = fp.parse(line);

                // make song from each song filepath
                for (String songPath : songPaths) {
                    Song song = new Song(songPath);
                    song.findMetadata();
                    songs.add(song);
                }
            }

            // updates the music list with found songs
            if (musicList != null) {
                musicList.updateSongList(songs);
            }
        } catch (IOException e) {
            System.err.println("Could not load music folders.");
        }


    }

    // MediaPlayer plays its current Song
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }

        stateChanged();
    }

    // MediaPlayer plays the given Song
    public void play(Song newSong) {
        // stops the current song if it is already playing
        if (mediaPlayer != null) {
            stop();
        }
        // sets the currentSong to the newSong
        curSong = newSong;

        // creates a new MediaPlayer based on the given Song
        try {
            Media hit = new Media("file:///" + encode(curSong.filePath));

            mediaPlayer = new MediaPlayer(hit);

            // initializes functions to fire a state change when MediaPlayer state changes
            mediaPlayer.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    stateChanged();
                }
            });

            mediaPlayer.setOnStopped(new Runnable() {
                @Override
                public void run() {
                    curSong = null;
                    stateChanged();
                }
            });

            mediaPlayer.setOnPaused(new Runnable() {
                @Override
                public void run() {
                    stateChanged();
                }
            });

            // plays next song when current song ends
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    playNext();
                    stateChanged();
                }
            });

            // plays given song
            mediaPlayer.play();

            // MusicLists selects given song
            musicList.setSelectedSong(curSong);
        } catch (NullPointerException e) {
            System.err.println("No song selected or song not found");
        }
    }

    // pauses the MediaPlayer
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    // stops the MediaPlayer
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // skips to the current value (value from 0.0 to 1.0)
    public void seek(double value) {
        if (mediaPlayer != null) {
            //System.out.println(value);
            double newSeekTime = curSong.duration.getSeconds() * value;
            //System.out.println(newSeekTime);
            Duration newDuration = new Duration(newSeekTime * 1000);
            mediaPlayer.seek(newDuration);
        }
    }

    // returns the current percentage of time played (from 0.0 to 1.0)
    public double getPercentage() {
        if (mediaPlayer != null) {
            float thisDuration = curSong.duration.getSeconds();
            float curTime = (float) mediaPlayer.getCurrentTime().toSeconds();
            return curTime / thisDuration;
        }

        return 0;
    }


    // plays the previous song
    public void playPrev() {
        nextMusic.addFirst(curSong);
        play(getPrevSong());
    }

    // plays the next song
    public void playNext() {
        // add the current song to the prevMusic list
        prevMusic.addLast(curSong);

        // if there are more than 100 Songs in prevMusic, remove Songs
        while (prevMusic.size() > 100) {
            prevMusic.removeLast();
        }

        // if there is no music in the nextMusic list, generate some next music
        if (nextMusic.size() == 0) {
            generateQueue();
        }

        // play the first song from nextMusic
        play(nextMusic.removeFirst());
    }

    // adds a music folder and update the MusicList with found songs
    public void addMusicFolder(String folder) {
        try {
            String musicFolderFileName = "musicFolders.txt";

            // do not add if the folder is already in the filepath list
            for (String filePath : filePaths) {
                if (filePath.equals(folder)) {
                    return;
                }
            }
            filePaths.add(folder);

            // create the music folder file if it doesn't exist
            File musicFile = new File(musicFolderFileName);
            if (!musicFile.exists()) {
                musicFile.createNewFile();
            }

            // append the given folder to the music folder file
            Files.write(Paths.get(musicFolderFileName), (folder + "\r\n").getBytes(), StandardOpenOption.APPEND);

            // find song paths in the given folder
            ArrayList<String> newSongPaths = fp.parse(folder);

            // create Songs from found song paths and add them to the list of Songs
            for (String newSongPath : newSongPaths) {
                Song song = new Song(newSongPath);
                song.findMetadata();
                songs.add(song);
            }

            // update the MusicList with found songs
            if (musicList != null) {
                musicList.updateSongList(songs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // returns list of all songs
    public ArrayList<Song> getSongs() {
        return songs;
    }

    // returns the current song
    public Song getCurrentSong() {
        return curSong;
    }

    // sets the volume (takes value from 0.0 to 1.0)
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    // encodes the given file path to work nicely with Media object
    public static String encode(String filePath) {
        return filePath.replace(" ", "%20").replace("\\", "/").replace("[", "%5B").replace("]", "%5D");
    }

    // adds a ChangeListener to the list
    public void addChangeListener(ChangeListener toAdd) {
        listeners.add(toAdd);
    }

    // when the state of the MediaPlayer has changed, tell all the listeners
    public void stateChanged() {
        for (ChangeListener listener : listeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
    }

    // returns the status of the MediaPlayer ("PLAYING", "PAUSED", "STOPPED", "UNKNOWN")
    public String getStatus() {
        if (mediaPlayer != null) {
            return mediaPlayer.getStatus().toString();
        }

        return null;
    }

    // returns a Duration object for the duration of the current song
    public Duration getDuration() {
        if (mediaPlayer != null)
            return mediaPlayer.getStopTime();
        return null;
    }

    // returns a Duration object for how far into the Song the MediaPlayer is
    public Duration getCurrentTime() {
        if (mediaPlayer != null)
            return mediaPlayer.getCurrentTime();
        return null;
    }

    // flips between shuffling and not shuffling
    public boolean changeShuffle() {
        shuffle = !shuffle;
        generateQueue();
        return shuffle;
    }

    // generates new Songs for nextMusic
    public void generateQueue() {
        nextMusic.clear();

        // generate a shuffled list
        if (shuffle) {
            Random rand = new Random();
            for (int i = 0; i < 10; i++) {
                int randIndex = rand.nextInt(songs.size());
                nextMusic.addFirst(songs.get(randIndex));
            }
        } else { // generate an "in-order" list
            int startingIndex = songs.indexOf(curSong) + 1;
            if (startingIndex >= songs.size()) {
                startingIndex = 0;
            }

            ListIterator<Song> it = songs.listIterator(startingIndex);

            int size = songs.size();
            for (int i = startingIndex; i < startingIndex + 10; i++) {
                nextMusic.addLast(songs.get(i % size));
            }
        }
    }

    // stores the MusicList
    public void setMusicList(MusicList musicList) {
        this.musicList = musicList;
    }

    // returns the previously played Song
    private Song getPrevSong() {
        // if there is an item in prevMusic, return that
        if (prevMusic.peekLast() != null) {
            return prevMusic.removeLast();
        } else { // no item ini prevMusic, find a Song to return
            // if shuffling, return a random song
            if (shuffle) {
                Random rand = new Random();
                int randIndex = rand.nextInt(songs.size());
                return songs.get(randIndex);
            } else { // else, return the Song before the current one
                int curIndex = songs.indexOf(curSong);
                // current song is at the beginning of the list, set the currentIndex to after the end of the list
                if (curIndex == 0)
                    curIndex = songs.size();

                // return the song before the current index
                return songs.get(curIndex - 1);
            }
        }
    }
}
