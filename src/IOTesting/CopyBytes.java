package IOTesting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by greg on 9/8/16.
 */
public class CopyBytes {

    public static void main(String[] args) throws IOException{

    FileInputStream in = null;
    FileOutputStream out = null;

    try{
        in = new FileInputStream(args[0]);
        // System.out.println("Reading data");
        out = new FileOutputStream(args[1]);
        int c;

        while((c = in.read()) != -1){
            // System.out.println((char)c);
            out.write(c);
        }
    }finally {
        if(in != null){
            in.close();
        }
        if(out!=null){
            out.close();
        }
    }
    }
}
