package Week5.src;
import java.util.TreeSet;
import java.util.Stack;
//import edu.princeton.cs.algs4.Point2D;
//import edu.princeton.cs.algs4.RectHV;

/**
 * Created by greg on 2/6/17.
 */
public class PointSET {

    private TreeSet<Point2D> set;

    public PointSET(){
        set = new TreeSet<>();
    }

    public boolean isEmpty(){
        return set.isEmpty();
    }

    public int size(){
        return set.size();
    }

    public void insert(Point2D p){
        if (p == null){
            throw new java.lang.NullPointerException("Attempted to insert a null point");
        }
        set.add(p);
    }

    public boolean contains(Point2D p){
        if (p == null){
            throw new java.lang.NullPointerException("Attempted to search for a null value");
        }
        return set.contains(p);
    }

    public void draw(){
        // Call the draw method of each point2d
        for(Point2D p : set){
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null){
            throw new java.lang.NullPointerException("Attempted range search for a null rectangle");
        }

        Stack<Point2D> stack = new Stack<>();
        // Do a quadratic search by going through all of the points
        for(Point2D p : set){
            if(rect.contains(p)) stack.push(p);
        }
        return stack;
    }

    public Point2D nearest(Point2D p){
    // Maintain a nearest point variable. Go through each point in the set and update the nearest point variable
        if(p == null){
            throw new java.lang.NullPointerException("Attempted nearest search for a null point");
        }

        if(set.isEmpty()) return null;

        Point2D best = null;
        for(Point2D point : set){
            if(best == null || (p.distanceTo(point) < p.distanceTo(best))) best = point;
        }
        return best;
    }

    public static void main(String[] args){

    }
}
