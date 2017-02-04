package IOTesting;

// Stream classes descended from Reader and Writer
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.SyncFailedException;

/**
 * Created by greg on 9/8/16.
 */
public class CopyCharacters {

    public static void main(String[] args) throws IOException{
        FileReader inputstream = null;
        FileWriter outputstream = null;

        try{
            inputstream = new FileReader("xanadu.txt");
            outputstream = new FileWriter("outAgain.txt");
            int c;

            while ((c = inputstream.read()) != -1){
                outputstream.write(c);
                // System.out.println((char)c);
            }

        }finally {
            if(inputstream != null){
                inputstream.close();
            }
            if(outputstream != null){
                outputstream.close();
            }
        }
    }
}
