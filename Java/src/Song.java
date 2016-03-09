/**
 * Created by Sawyer on 3/6/2016.
 */
import java.io.*;
import java.time.Duration;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.IOExceptionWithCause;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.gagravarr.tika.FlacParser;
import org.jdom2.Content;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class Song implements Comparable<Song> {
    public String title;
    public String artist;
    public String album;
    public String filePath;
    public String albumArtPath;
    public String fileType;
    public Duration duration;

    public Song(String filePath) throws IOException {
        this.filePath = filePath;
        if (filePath.endsWith(".mp3")) {
            fileType = "MP3";
        } else if (filePath.endsWith(".flac")) {
            fileType = "FLAC";
        } else if (filePath.endsWith(".m4a")) {
            fileType = "M4A";
        } else {
            throw new IOException("Unsupported file type");
        }
    }

    public void findMetadata() {
        try {
            InputStream input = new FileInputStream(new File(filePath));
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser;
            if (fileType.equals("MP3")) {
                parser = new Mp3Parser();
            } else if (fileType.equals("M4A")) {
                parser = new MP4Parser();
            } else if (fileType.equals("FLAC")) {
                parser = new FlacParser();
            } else {
                parser = new Mp3Parser();
            }

            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);
            input.close();

            title = metadata.get("title");
            artist = metadata.get("xmpDM:artist");
            album = metadata.get("xmpDM:album");
            float seconds = Float.parseFloat(metadata.get("xmpDM:duration"));
            duration = Duration.ofMillis((long) (seconds * 1000));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return title + " - " + artist;// + "\t\t" + album;
        //return "{Title: " + title + ", Artist: " + artist + ", Album: " + album + "}";
    }

    public int compareTo(Song other) {
        return this.title.compareTo(other.title);
    }

}
