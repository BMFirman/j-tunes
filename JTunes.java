import java.lang.*; 
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class JTunes {
    public static void main(String[] args) throws IOException {
        try {
            if (args[0] == "Play") {
                playbackToggle("play.scpt");
            } else if (args[0] == "Pause") {
                playbackToggle("pause.scpt");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No flag was provided.");
        }

    }

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
}