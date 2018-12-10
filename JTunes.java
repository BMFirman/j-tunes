package jtunes;
import java.lang.*; 
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.nio.file.Paths;

class JTunes {
    public static boolean scriptOutputFlag = false;

    public static void main(String[] args) throws IOException {
        
        try {
            
            List<String> commandList = new ArrayList<String>();
            commandList.add("osascript");
            commandList.add(commandSelector(args));
 
            executor(commandList, scriptOutputFlag);

        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("No flag was provided.");
        }

    }

    static String commandSelector(String[] args) throws ArrayIndexOutOfBoundsException {
            
            if (args.length == 0) {

                return "scripts/playPause.scpt";

            } else if (args[0].equals("pp")) {

                return "scripts/playPause.scpt";

            } else if (args[0].equals("pl")) {

                writePlaylist(args);
                return "scripts/script.scpt"; 
                

            } else if (args[0].equals("n")) {

                return "scripts/nextTrack.scpt";

            } else if (args[0].equals("pr")) {

                return "scripts/nextTrack.scpt";

            } else if (args[0].equals("l")) {

                setScriptOutputFlag(); 
                return "scripts/trackinfo.scpt";

            } else if (args[0].equals("s")) {

                return "scripts/shuffleOn.scpt";

            } else if (args[0].equals("so")) {

                return "scripts/shuffleOff.scpt";
            
            } else if (args[0].equals("t")) {
                
                writeSong(args);
                return "scripts/script.scpt"; 

            } else if (args[0].equals("r")) {

                return "scripts/repeatAll.scpt";

            } else if (args[0].equals("ro")) {

                return "scripts/repeatOff.scpt";
            
            } else if (args[0].equals("r1")) {

                return "scripts/repeatOne.scpt";

            } else {
                return "scripts/playPause.scpt";
            }
    }

    public static void setScriptOutputFlag() {
        scriptOutputFlag = true;
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