package Week5.src;

import java.util.Stack;
import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.Point2D;
//import edu.princeton.cs.algs4.RectHV;

/**
 * Created by greg on 2/6/17.
 */
public class KdTree {

    private static class Node{
        private Point2D point;
        private RectHV rectangle;
        private Node left;
        private Node right;
        private boolean vertical;

        private Node(Point2D p, RectHV rect, Node l, Node r, boolean alignment){
            point = p;
            rectangle = rect;
            right = r;
            left = l;
            vertical = alignment;
        }
    }

    // Declare private variables for the kdTree
    private int size;
    private Node root;

    public KdTree(){
        root = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    private RectHV createRect(Point2D p, Node parent){
        // Returns a new Rectangle based on the point given. Used in creating a new node
        RectHV tempRect;
        if(parent == null){
            tempRect = new RectHV(0,0,1,1);
        }
        else if(parent.vertical) {
            double tempXmin, tempXmax;
            if (insertComp(parent, p) < 0){
                tempXmin = parent.rectangle.xmin();
                tempXmax = parent.point.x();
            }
            else{
                tempXmin = parent.point.x();
                tempXmax = parent.rectangle.xmax();
            }
            tempRect = new RectHV(tempXmin,parent.rectangle.ymin(),tempXmax,parent.rectangle.ymax());
        }
        else{
            double tempYmin, tempYmax;
            if(insertComp(parent,p) < 0){
                tempYmin = parent.rectangle.ymin();
                tempYmax = parent.point.y();
            }
            else{
                tempYmin = parent.point.y();
                tempYmax = parent.rectangle.ymax();
            }
            tempRect = new RectHV(parent.rectangle.xmin(),tempYmin,parent.rectangle.xmax(),tempYmax);
        }
        return tempRect;
    }

    private Node insertRoot(Node n, Point2D p, Node parent, boolean alignment){
        if(n==null){
            return new Node(p, createRect(p,parent), null, null, alignment);
        }

        if(insertComp(n,p) < 0) n.left = insertRoot(n.left, p, n, !n.vertical);
        else if(insertComp(n, p) > 0) n.right = insertRoot(n.right, p, n, !n.vertical);

        return n;
    }

    private int insertComp(Node parent, Point2D p){
        // Need to figure out how to deal with vertical and horizontal cases
        // returns -1 if query point is to left/below node point
        // returns 1 if query point is to right/above node point

         if(parent.vertical) {
             if (p.x() < parent.point.x()) return -1;
             else if (p.x() > parent.point.x()) return 1;
             else {
                 if(p.y()< parent.point.y()) return -1;
                 else if (p.y() > parent.point.y()) return 1;
                 else return 0;
             }
         }
         else {
                 if(p.y() < parent.point.y()) return -1;
                 else if(p.y() > parent.point.y()) return 1;
                 else{
                    if(p.x()< parent.point.x()) return -1;
                    else if (p.x() > parent.point.x()) return 1;
                    else return 0;
                }
         }
    }

    public void insert(Point2D p){
        if(p == null) throw new java.lang.NullPointerException("Attempted to insert a null point");
        if(!contains(p)) {
            root = insertRoot(root, p, null, true);
            size++;
        }
    }

    public boolean contains(Point2D p){
        // Uses same logic as a binary tree
        if(p == null) throw new java.lang.NullPointerException("Attempted contains for a null point");

        return containsRoot(root, p);
    }

    private boolean containsRoot(Node n, Point2D p){
        if(n == null) return false;
        else if (n.point.compareTo(p) == 0) return true;

        if(insertComp(n, p) < 0) return containsRoot(n.left, p);
        else if (insertComp(n, p) > 0) return containsRoot(n.right, p);
        else return false;
    }

    public void draw(){
        StdDraw.clear();
        recursiveDraw(root);
    }

    private void recursiveDraw(Node n){
        if(n == null) return;
        else {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(n.point.x(), n.point.y());

            if(n.vertical){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(n.point.x(),n.rectangle.ymin(),n.point.x(),n.rectangle.ymax());
            }else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(n.rectangle.xmin(), n.point.y(), n.rectangle.xmax(), n.point.y());
            }
            recursiveDraw(n.left);
            recursiveDraw(n.right);
        }
    }

    public Iterable<Point2D> range(RectHV r){
        if(r == null) throw new java.lang.NullPointerException("Attempted range search for a null rectangle.");

        Stack<Point2D> stack = new Stack<>();

        if(isEmpty()) return stack;
        else recursiveRange(root,r, stack);
        return stack;
    }

    private void recursiveRange(Node n, RectHV r, Stack<Point2D> stack){

        if(n == null || !n.rectangle.intersects(r)) return;
        if(n.rectangle.intersects(r)){
            if(r.contains(n.point)) stack.push(n.point);
            recursiveRange(n.left, r, stack);
            recursiveRange(n.right, r, stack);
        }
    }

    public Point2D nearest(Point2D p){

        // Start with the best being the root node
        // Choose the subtree with the smallest distance to its rectangle.
        // return if node is null
        // Search other subtree only if its rectangle nearest is less than best point

        if(p == null) throw new java.lang.NullPointerException("Attempted nearest search with " +
                "a null point");

        if(isEmpty()) return null;

        Point2D best = root.point;

        best = recursiveNearest(p,best, root);

        return best;
    }

    private Point2D recursiveNearest(Point2D query, Point2D best, Node current){

        if(current!=null){
            if(current.rectangle.distanceTo(query) < query.distanceTo(best)){
                if(current.point.distanceTo(query) < best.distanceTo(query) )
                best = current.point;

                best = recursiveNearest(query, best, current.left);
                best = recursiveNearest(query, best, current.right);

            }
            else return best;
        }
        else return best;

        return best;
    }

    public static void main(String[] args){

        KdTree k = new KdTree();
        k.insert(new Point2D(0.5,0.5));
        k.insert(new Point2D(0.5, 0.4));
        k.insert(new Point2D(0.5, 0.6));
        k.insert(new Point2D(0.5, 0.7));
        System.out.println(k.contains(new Point2D(0.5, 0.6)));
        System.out.println(k.contains(new Point2D(0.5, 0.4)));

        /*
        KdTree k = new KdTree();
        k.insert(new Point2D(0.5,0.5));
        //k.insert(new Point2D(0.5,0.5));
        double x=0, y=0;

        for(int i = 0; i<100; i++){
            for(int j = 0; j < 100; j++){
                x += 0.01;
                y += 0.01;
                k.insert(new Point2D(x,y));
                if(k.contains(new Point2D(x,y)) != true){
                    System.out.println("Error on x: " + x + " and y: " +y);
                }
            }
        }

        //System.out.println(k.size);
        //System.out.println(k.contains(new Point2D(0.5,0.5)));

        /*
        KdTree k = new KdTree();
        Point2D one = new Point2D(0.5, 0.5);
        Point2D two = new Point2D(0.8, 0.8);
        Point2D three = new Point2D(0.1, 0.1);

        k.insert(one);
        System.out.println(k.isEmpty());
        System.out.println(k.size());
        k.insert(two);
        System.out.println(k.root.right.point);
        k.insert(three);
        System.out.println(k.root.left.point);
        System.out.println(k.contains(one));
        System.out.println(k.contains(two));
        System.out.println(k.contains(three));
        System.out.println(k.contains(new Point2D(0.3,0.3)));
        System.out.println(k.root.rectangle);
        System.out.println(k.root.left.rectangle); // 0,0,0.5,1
        System.out.println(k.root.right.rectangle);
        k.insert(new Point2D(0.2,0.7));
        // k.draw();
        for(Point2D p : k.range(new RectHV(0,0,1,1))){
            System.out.println(p);
        }

        System.out.println(k.nearest(new Point2D(0.25,0.25)));
        */
    }
}
