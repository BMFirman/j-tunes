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
            List<String> commandList = new ArrayList<String>();
            commandList.add("osascript");

            if (args.length == 0) {
                commandList.add("scripts/playPause.scpt");

            } else if (args[0].equals("pp")) {
                commandList.add("scripts/playPause.scpt");

            } else if (args[0].equals("pl")) {
                commandList.add("scripts/script.scpt"); 
                writePlaylist(args[1]);

            } else if (args[0].equals("n")) {
                commandList.add("scripts/nextTrack.scpt");

            } else if (args[0].equals("pr")) {
                commandList.add("scripts/nextTrack.scpt");

            } else {
                commandList.add("scripts/playPause.scpt");
            }
 
            executor(commandList);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No flag was provided.");
        }

    }

    static void executor(List<String> commandList) throws IOException {
        ProcessBuilder build = new ProcessBuilder(commandList); 
        Process p = build.start();
        System.out.println("command: " + build.command());        
    }

    static void writePlaylist(String playlistName) {
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
            output = new Formatter(Paths.get(workingDirectory) + "/scripts/" + filename);
            System.out.println(Paths.get(workingDirectory) + "/scripts/" + filename);
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