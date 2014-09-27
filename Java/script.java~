import java.io.*;
 
public class script {
 
    public static void main(String argv[]) {
        try {
            String line;
            /**
             * getRunTime() Returns the runtime object associated with the current
             * Java application. Most of the methods of class Runtime are instance
             * methods and must be invoked with respect to the current runtime object.
             *
             * exec() method Executes the specified string command in a separate
             * process. The command argument is parsed into tokens and then
             * executed as a command in a separate process. The token
             * parsing is done by a java.util.StringTokenizer created by the call.
             *
             */
            Process p = Runtime.getRuntime().exec("echo 10*20 | bc");
 
            /**
             * Create a buffered reader from the Process' input stream.
             */
            BufferedReader input = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
 
            /**
             * Loop through the input stream to print the program output into console.
             */
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            /**
             * Finally close the reader
             */
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
