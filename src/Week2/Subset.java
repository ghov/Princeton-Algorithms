
import edu.princeton.cs.algs4.StdIn;
/**
 * Created by greg on 9/18/16.
 */
public class Subset {

    public static void main(String[] args){

        int k;
        k = Integer.parseInt(args[0]);

        RandomizedQueue<String> myQueue = new RandomizedQueue<>();

        String[] arr = StdIn.readAllStrings();

        for (int i=0; i<arr.length; i++){
            myQueue.enqueue(arr[i]);
        }

        for(int i=0; i<k; i++){
            System.out.println(myQueue.dequeue());
        }

    }
}
