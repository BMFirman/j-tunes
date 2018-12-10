import java.lang.*; 
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class JTunes {
    public static void main(String[] args) throws IOException {
        

        try {
            List<String> list = new ArrayList<String>(); 
            list.add("osascript");

            if (args[0].equals("play")) {
                //playbackToggle("play.scpt");
                list.add("play.scpt"); 
            } 
            if (args[0].equals("pause")) {
                //playbackToggle("pause.scpt");
                list.add("pause.scpt"); 
            }

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
}