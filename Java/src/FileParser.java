/**
 *
 * @author Tyler Yamamoto
 */
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import java.io.*;
import java.util.ArrayList;

public class FileParser{
    private static ArrayList<String> songPaths;

    public FileParser() {

    }

    private static boolean isAudioFile(String file){																//Check file type
        return file.endsWith(".flac")||file.endsWith(".mp3")||file.endsWith(".m4a");//||file.endsWith(".wav");
    }

    public static ArrayList<String> parse(String filePath) {
        songPaths = new ArrayList<String>();
        return parse(new File(filePath));
    }

    private static ArrayList<String> parse(File node) {
        String file = node.getAbsoluteFile().toString();
        if (isAudioFile(file)) {
            songPaths.add(file);
        }
        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                parse(new File(node, filename));
            }
        }

        return songPaths;
    }
}
