package jtunes;
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
            boolean scriptOutputFlag = false;
            List<String> commandList = new ArrayList<String>();
            commandList.add("osascript");

            if (args.length == 0) {

                commandList.add("scripts/playPause.scpt");

            } else if (args[0].equals("pp")) {

                commandList.add("scripts/playPause.scpt");

            } else if (args[0].equals("pl")) {

                commandList.add("scripts/script.scpt"); 
                writePlaylist(args);

            } else if (args[0].equals("n")) {

                commandList.add("scripts/nextTrack.scpt");

            } else if (args[0].equals("pr")) {

                commandList.add("scripts/nextTrack.scpt");

            } else if (args[0].equals("l")) {

                commandList.add("scripts/trackinfo.scpt");
                scriptOutputFlag = true;

            } else if (args[0].equals("s")) {

                commandList.add("scripts/shuffleOn.scpt");

            } else if (args[0].equals("so")) {

                commandList.add("scripts/shuffleOff.scpt");
            
            } else if (args[0].equals("t")) {

                commandList.add("scripts/script.scpt");
                writeSong(args);

            } else if (args[0].equals("r")) {

                commandList.add("scripts/repeatAll.scpt");

            } else if (args[0].equals("ro")) {

                commandList.add("scripts/repeatOff.scpt");
            
            } else if (args[0].equals("r1")) {

                commandList.add("scripts/repeatOne.scpt");
                writeSong(args);

            } else {
                commandList.add("scripts/playPause.scpt");
            }
 
            executor(commandList, scriptOutputFlag);

        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("No flag was provided.");
        }

    }

    // scriptOutputflag toggles printing of script output
    static void executor(List<String> commandList, boolean scriptOutputFlag) throws IOException {
        
        ProcessBuilder build = new ProcessBuilder(commandList); 
        Process p = build.start();
               
        if (scriptOutputFlag == true) { 
            getScriptOutput(p);
        }
    }

    static void writePlaylist(String[] args) {
        String output =
            "tell application \"iTunes\"\n" +
            "    play the playlist named \"";
        
        output += argsParser(args);
        
        output += "\"\n" +
        "end tell";

        writeScript(output);
    }

    static void writeSong(String[] args) {

        String output =
            "tell application \"iTunes\"\n" +
            "    play track \"";
        
        output += argsParser(args);
        
        output += "\"\n" +
        "end tell";

        writeScript(output);
    }

    static String argsParser(String[] args) {

        String output = "";

        for (int i = 1; i<args.length; i++) {

            output += args[i];

            // Only add spacing when there is more than one word
            // and the word is not the last in the name of the song/playlist
            // applescript takes care of capitalisation
            if (args.length > 2 && i != args.length-1) {
                
                output += " ";
            }
        }

        return output;
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

    // See: https://stackoverflow.com/questions/16714127/how-to-redirect-process-builders-output-to-a-string
    // Could add the error stream with processBuilder.inheritIO()
    static void getScriptOutput(Process process) throws IOException {

        BufferedReader reader = 
                new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            
            builder.append(line);
        }

        String result = builder.toString();
        System.out.println(result);   
    }

    public static Formatter initFormatter(String filename, String workingDirectory, Formatter output) {

        try {

            output = new Formatter(Paths.get(workingDirectory) + "/scripts/" + filename);
            
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