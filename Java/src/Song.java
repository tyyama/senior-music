/**
 * Created by Sawyer on 3/6/2016.
 */
import java.io.*;
import java.time.Duration;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.audio.AudioHeader;

public class Song implements Comparable<Song> {
    public String title = "Not found";
    public String artist = "Not found";
    public String album = "Not found";
    public String filePath = "Not found";
    public String albumArtPath;
    public Duration duration;

    public Song(String filePath) throws IOException {
        this.filePath = filePath;
    }

    // finds the Song's metadata and updates the fields
    public void findMetadata() {
        try {
            // initialize metadata readers
            AudioFile f = AudioFileIO.read(new File(filePath));
            Tag tag = f.getTag();
            AudioHeader a = f.getAudioHeader();

            // get info from file and set the corresponding fields
            duration = Duration.ofSeconds((long) a.getTrackLength());
            title = tag.getFirst(FieldKey.TITLE);
            artist = tag.getFirst(FieldKey.ARTIST);
            album = tag.getFirst(FieldKey.ALBUM);
            albumArtPath = tag.getFirstArtwork().getImageUrl();

        } catch (Exception e) {
            System.err.println("Metadata not found for " + filePath);
        }
    }

    // returns the String reprentation of the Song, used for MusicList
    public String toString() {
        return title + " - " + artist;
    }

    // compares songs alphabetically by title
    public int compareTo(Song other) {
            return this.title.compareTo(other.title);
    }

}
