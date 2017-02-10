package Week5.src;

import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by greg on 2/6/17.
 */
public class Point2D implements Comparable<Point2D> {

    private double x, y;

    private double euclidianDistance(Point2D first, Point2D second){
        return Math.sqrt(Math.pow(first.x - second.x,2) + Math.pow(first.y - second.y,2));
    }

    public Point2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double x(){
        return this.x;
    }

    public double y(){
        return this.y;
    }

    public int compareTo(Point2D that){
        if(this.y < that.y){
            return -1;
        }else if(this.y > that.y){
            return 1;
        }else{
            if(this.x < that.x){
                return -1;
            }else if(this.x > that.x){
                return 1;
            }else{
                return 0;
            }
        }
    }

    public double distanceTo(Point2D that){
        return euclidianDistance(this, that);
    }

    public double distanceSquaredTo(Point2D that){
        return Math.pow(euclidianDistance(this, that), 2);
    }

    public boolean equals(Object that){
        if(this == that) return true;
        if(that == null) return false;
        if(this.getClass() != that.getClass()) return false;

        Point2D inputPoint = (Point2D) that;

        if(this.x != inputPoint.x || this.y != inputPoint.y) return false;

        return true;
    }

    public void draw(){
        StdDraw.point(this.x, this.y);
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    public static void main(String[] args){
        Point2D point = new Point2D(0.1,0.1);
        System.out.println(point.toString());
        point.draw();
    }
}
