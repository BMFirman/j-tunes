import java.lang.*; 
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.nio.file.Paths;

class JTunes {
    public static void main(String[] args) throws IOException {
        

        try {
            List<String> list = new ArrayList<String>();
            list.add("osascript");
            list.add("script.scpt");
            /*
            list.add("osascript");

            if (args[0].equals("play")) {
                //playbackToggle("play.scpt");
                list.add("play.scpt"); 
            } 
            if (args[0].equals("pause")) {
                //playbackToggle("pause.scpt");
                list.add("pause.scpt"); 
            }
            */
            playPlaylist(args[0]);

            ProcessBuilder build = new ProcessBuilder(list); 
            Process p = build.start();
            System.out.println("command: " + build.command()); 

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No flag was provided.");
        }

    }
    /*
    static void playbackToggle(String playbackSelection){
        try {
            // creating list of process 
            List<String> list = new ArrayList<String>(); 
            list.add("osascript"); 
            list.add(playbackSelection); 
          
            // create the process 
            ProcessBuilder build = new ProcessBuilder(list); 
            Process p = build.start();
            //p.waitFor();
        } catch (IOException e) {
            System.out.println("Failed to run osascript");
        }
    }
    */

    static void playPlaylist(String playlistName) {
        String output =
        "tell application \"iTunes\"\n" +
        "  play the playlist named \"" +
        playlistName +
        "\"\n" +
        "end tell";

        writeScript(output);
    }

    static void writeScript(String command) {

        String filename = "script.scpt";
        String workingDirectory = System.getProperty("user.dir");

        Formatter output = null;
        output = initFormatter(filename, workingDirectory, output);

        output.format("-- JTunes auto script\n");
        output.format(command);
        

        output.close();
    }


    public static Formatter initFormatter(String filename, String workingDirectory, Formatter output) {

        try {
            output = new Formatter(Paths.get(workingDirectory) + "/" + filename);
        } catch (SecurityException secExc) {
            System.err.println("Can't write to .scpt!");
            System.exit(1);
        } catch (FileNotFoundException fnfExc) {
            System.err.println("Can't find path to .scpt!");
            System.exit(1);
        }

        return output;
    }
}