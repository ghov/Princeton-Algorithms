package Week3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by greg on 9/25/16.
 */
public class FastCollinearPoints {

    private LineSegment[] line;
    private double slope;
    private Stack<Point> myStack = new Stack<>();
    private int count = 0;
    private Point[] tempCopy;
    private Point[] tempLine = new Point[4];
    private Set<String> duplicateCheck = new HashSet<String>();

    private boolean duplicates(final Point[] tempPoints){

        Set<String> group = new HashSet<String>();
        for(Point p : tempPoints){
            if (group.contains(p.toString())){
                return true;
            }
            group.add(p.toString());
        }
        return false;
    }

    public FastCollinearPoints(Point[] points) throws java.lang.IllegalArgumentException, java.lang.NullPointerException{

        if(points == null){
            throw new NullPointerException();
        }

        for(Point p : points){
            if(p == null){
                throw new NullPointerException();
            }
        }

        if(duplicates(points)){
            throw new IllegalArgumentException();
        }


        Arrays.sort(points);
        tempCopy = Arrays.copyOf(points,points.length);
        for(Point p : points){
            Arrays.sort(tempCopy, p.slopeOrder());
            for(int i = 1; i < tempCopy.length-2; i++){
                if(tempCopy[0].slopeTo(tempCopy[i]) == tempCopy[0].slopeTo(tempCopy[i+1]) &&
                tempCopy[0].slopeTo(tempCopy[i+1]) == tempCopy[0].slopeTo(tempCopy[i+2])) {
                    tempLine[0] = tempCopy[0];
                    tempLine[1] = tempCopy[i];
                    tempLine[2] = tempCopy[i + 1];
                    tempLine[3] = tempCopy[i + 2];
                    Arrays.sort(tempLine);
                    if (duplicateCheck.contains(tempLine[0].toString()) || duplicateCheck.contains(tempLine[3].toString())) {
                        // do nothing
                    } else{
                        count++;
                        duplicateCheck.add(tempLine[0].toString());
                        duplicateCheck.add(tempLine[0].toString());
                        myStack.push(tempLine[0]);
                        myStack.push(tempLine[3]);
                    }
                }
            }
        }

        if(count != 0){
            int c = 0;
            line = new LineSegment[count];
            while (!myStack.empty()){
                line[c] = new LineSegment(myStack.pop(), myStack.pop());
                c++;
            }
        }

    }

    public int numberOfSegments(){
        return count;
    }

    public LineSegment[] segments(){
        if(count !=0 ) {
            return line;
        }
        else{
            return new LineSegment[0];
        }
    }
}
