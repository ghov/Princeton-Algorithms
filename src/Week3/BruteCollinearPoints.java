package Week3;

import java.util.*;

/**
 * Created by greg on 9/25/16.
 */
public class BruteCollinearPoints {

    private LineSegment[] line;
    private double slope;
    private Stack<Point> myStack = new Stack<>();
    private int count = 0;
    private Point[] temp = new Point[4];

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

    public BruteCollinearPoints(Point[] points) throws java.lang.IllegalArgumentException, java.lang.NullPointerException{

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

        for(int i =0; i<points.length-3; i++){
            for(int j = i+1; j < points.length-2; j++){
                slope = points[i].slopeTo(points[j]);
                for(int k = j+1; k<points.length-1; k++){
                    if(slope == points[i].slopeTo(points[k])){
                        for(int m = k+1; m < points.length; m++){
                            if(slope == points[i].slopeTo(points[m])){
                                count++;
                                temp[0] = points[i];
                                temp[1] = points[j];
                                temp[2] = points[k];
                                temp[3] = points[m];
                                Arrays.sort(temp);
                                myStack.push(temp[0]);
                                myStack.push(temp[3]);
                            }
                        }
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
