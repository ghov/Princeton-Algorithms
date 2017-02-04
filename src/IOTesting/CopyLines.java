package IOTesting;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Created by greg on 9/8/16.
 */
public class CopyLines {
    public static void main(String[] args) throws IOException{

        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try{
            inputStream = new BufferedReader(new FileReader("xanadu.txt"));
            System.out.println("Reading");
            outputStream = new PrintWriter(new FileWriter("characterOutput.txt"));

            String l;
            while ((l = inputStream.readLine()) != null){
                outputStream.println();
                System.out.println(l);
            }

        }finally {
            // outputStream.flush();
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
