package file.parser;

/**
 *
 * @author Tyler Yamamoto
 */
import java.io.*;

public class FileParser{
        
	private static PrintWriter writer;
	
	static{																											//Static initializer block. Don't worry about it.
		try {
			writer = new PrintWriter("Songs.dat", "UTF-8");                                             			//Create file for directory listing, UTF-8 encoding (for foreign languages and symbols). 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main (String args[]){
                String arg = args[0];
                if(args.length>1){																					//If folder name has a space, concatenate array values and separate with spaces
                    for(int i=1;i<args.length;i++){
                        arg+=(" "+args[i]);
                    }
                }
                
		parse(new File(arg));
                writer.close();
	}
        
    private static boolean isAudioFile(String file){																//Check file type
        return file.endsWith(".flac")||file.endsWith(".mp3")||file.endsWith(".m4a")||file.endsWith(".wav");
    }
	
	public static void parse(File node){
		String file = node.getAbsoluteFile().toString();
		if(isAudioFile(file)){
			writer.println(file);                                                                         
		}
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				parse(new File(node, filename));
			}
                        
		}
		
	}
}
