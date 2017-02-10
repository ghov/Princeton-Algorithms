package Week5.src;

/**
 * Created by greg on 2/6/17.
 */

// A rectangle with horizontal and vertical values
public class RectHV {

    private double xmin,ymin,xmax,ymax;

    public RectHV(double xmin, double ymin, double xmax, double ymax){
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }

    public double xmin(){
        return this.xmin;
    }

    public double ymin(){
        return this.ymin;
    }

    public double xmax(){
        return this.xmax;
    }

    public double ymax(){
        return this.ymax;
    }

    public boolean contains(Point2D p){
        if((p.x() >= this.xmin && p.x() <= this.xmax) && (p.y() >= this.ymin && p.y() <= this.ymax)){
            return true;
        }else return false;
    }

    private boolean containsPoint(double x, double y){
        if((x >= this.xmin && x <= this.xmax) && (y >= this.ymin && y <= this.ymax)){
            return true;
        }else return false;
    }

    public boolean intersects(RectHV that){
        // if either rectangle contains any of the edge points of the other rectangle, we have a intersection
        if(this.containsPoint(that.xmin, that.ymin) || this.containsPoint(that.xmin, that.ymax) ||
                this.containsPoint(that.xmax, that.ymin) || this.containsPoint(that.xmax, that.ymax)){
            return true;
        }else return false;
    }

    public double distanceTo(Point2D p){
        // Check if the rectangle contains the point, hence a distance of 0
        if(contains(p)) return 0;

        // Check if it is in the range of either the x or y variables
        if(p.x() >= xmin && p.x() <= xmax) return Math.min(p.distanceTo(new Point2D(p.x(),this.ymin)),
                p.distanceTo(new Point2D(p.x(),this.ymax)));
        if(p.y() >= ymin && p.y() <= ymax) return Math.min(p.distanceTo(new Point2D(this.xmin, p.y())),
                p.distanceTo(new Point2D(this.xmax, p.y())));

        // Otherwise, it must be in one of the corners and shortest path will be to one of the four corners
        // Check if point is above the rectangle
        if(p.y() > this.ymax) return Math.min(p.distanceTo(new Point2D(this.xmin, this.ymax)),
                p.distanceTo(new Point2D(this.xmax, this.ymax)));

        // Check if it is below the rectangle
        if(p.y() < this.ymax) return Math.min(p.distanceTo(new Point2D(this.xmin, this.xmin)),
                p.distanceTo(new Point2D(this.xmax, this.ymin)));

        return -1;
    }

    public double distanceSquaredTo(Point2D p){
        // Check if the rectangle contains the point, hence a distance of 0
        if(contains(p)) return 0;

        // Check if it is in the range of either the x or y variables
        if(p.x() >= xmin && p.x() <= xmax) return Math.min(p.distanceSquaredTo(new Point2D(p.x(),this.ymin)),
                p.distanceSquaredTo(new Point2D(p.x(),this.ymax)));
        if(p.y() >= ymin && p.y() <= ymax) return Math.min(p.distanceSquaredTo(new Point2D(this.xmin, p.y())),
                p.distanceSquaredTo(new Point2D(this.xmax, p.y())));

        // Otherwise, it must be in one of the corners and shortest path will be to one of the four corners
        // Check if point is above the rectangle
        if(p.y() > this.ymax) return Math.min(p.distanceSquaredTo(new Point2D(this.xmin, this.ymax)),
                p.distanceSquaredTo(new Point2D(this.xmax, this.ymax)));

        // Check if it is below the rectangle
        if(p.y() < this.ymax) return Math.min(p.distanceSquaredTo(new Point2D(this.xmin, this.xmin)),
                p.distanceSquaredTo(new Point2D(this.xmax, this.ymin)));

        return -1;
    }

    public boolean equals(Object that){
        if(this == that) return true;
        if(that == null) return false;
        if(this.getClass() != that.getClass()) return false;

        RectHV inputRectangle = (RectHV) that;

        if(this.xmin != inputRectangle.xmin || this.ymin != inputRectangle.ymin
                || this.xmax != inputRectangle.xmax || this.ymax != inputRectangle.ymax) return false;

        return true;
    }

    public void draw(){

    }

    public String toString(){
        return "(" + this.xmin + ", " + this.ymin + ", " + this.xmax + ", " + this.ymax + ")";
    }

}