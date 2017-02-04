package Week2;
import edu.princeton.cs.algs4.StdIn;

import java.util.Stack;

/**
 * Created by greg on 9/9/16.
 */
public class StackTest {

    public static void main(String[] args){
        Stack<String> stack = new Stack<>();
        while (!StdIn.isEmpty()){
         String s = StdIn.readString();
            if(s.equals("-")){
                System.out.println(stack.pop());
            }else{
                stack.push(s);
            }

        }
    }
}
