package Week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by greg on 9/8/16.
 */
public class Percolation{

    private int[] open;
    private int size;
    private WeightedQuickUnionUF uf;

    // Converts the row and column into one value
    private int converter(int i, int j){
        return ((size*(i-1)) + (j-1)) + 2;
    };

    // Create n by n grid with all sites blocked
    public Percolation(int n) throws IllegalArgumentException{
        if(n<=0){
            throw new IllegalArgumentException();
        }
        open = new int[(n*n) +2];
        size = n;
        uf = new WeightedQuickUnionUF((n*n) + 2);
        for(int c = 0; c<n; c++){
            uf.union(0,c+2);
            // uf.union(1,((n*n) + 1)-c);
        }
    }

    // Open site (row i, column j) if it is not already open
    public void open(int i, int j) throws IndexOutOfBoundsException{
        if(i<1 || j<1 || i>size || j>size){
            throw new IndexOutOfBoundsException();
        }

        // Check if the sites adjacent to it are open. If so, then union them

        // Check left
        if(j>1) {
            if (isOpen(i, j - 1)) {
                uf.union(converter(i, j), converter(i, j - 1));
            }
        }
        // Check right
        if(j<size) {
            if (isOpen(i, j + 1)) {
                uf.union(converter(i, j), converter(i, j + 1));
            }
        }
        // Check top
        if(i>1) {
            if (isOpen(i - 1, j)) {
                uf.union(converter(i, j), converter(i - 1, j));
            }
        }
        // Check bottom
        if(i<size) {
            if (isOpen(i + 1, j)) {
                uf.union(converter(i, j), converter(i + 1, j));
            }
        }

        open[converter(i,j)] = 1;

        if(isOpen(size,j) && uf.connected(0,converter(size,j))){
           uf.union(1, converter(size,j));
        }
    }

    // Is site (row i, column j) open?
    public boolean isOpen(int i, int j) throws IndexOutOfBoundsException{
        if(i<1 || j<1 || i>size || j>size){
            throw new IndexOutOfBoundsException();
        }

        int val = converter(i,j);
        if((val>=2 && val<=(size*size)+1) && open[val] == 1){
            return true;
        }
        return false;
    }

    public boolean isFull(int i, int j) throws IndexOutOfBoundsException{
        if(i<1 || j<1 || i>size || j>size){
            throw new IndexOutOfBoundsException();
        }

        if(isOpen(i,j) && uf.connected(0,converter(i,j))) {
            return true;
        }
        return false;
    }

    public boolean percolates() {
        if (size > 1) {
            if (uf.connected(0, 1)) {
                return true;
            }
            return false;
        } else {
            if (isOpen(1, 1)) {
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args){

        Percolation p = new Percolation(2);

        // int[] t = {1,6,2,6,3,6,4,6,5,6,5,5,4,4,3,4,2,4,2,3,2,2,2,1,3,1,4,1,5,1,5,2,6,2,5,4};
        // int [] t = {1,3,2,3,3,3,3,1,2,1,1,1};
        int[] t = {1,1,2,2,1,2};
        for(int i=0; i<t.length; i+=2){

            if(p.isOpen(t[i],t[i+1])){
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is open");
            }else{
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is closed");
            }

            if(p.isFull(t[i],t[i+1])){
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is Full");
            }else{
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is not Full");
            }

            System.out.println("Opening (" + t[i] +"," + t[i+1] + ")" );

            p.open(t[i],t[i+1]);

            if(p.isOpen(t[i],t[i+1])){
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is open");
            }else{
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is closed");
            }

            if(p.isFull(t[i],t[i+1])){
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is Full");
            }else{
                System.out.println("The site (" + t[i] + "," + t[i+1] + ") is not Full");
            }

            System.out.println();
        }

    }
}
