package Week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
/**
 * Created by greg on 9/8/16.
 */
public class PercolationStats {

   private double[] fr;
   private double m;
   private double s;
   private int trials;

   public PercolationStats(int n, int trials) throws IllegalArgumentException{
        if(n<=0 || trials <=0){
            throw new IllegalArgumentException();
        }
       // Start running trials
       int size = n*n;
       this.trials = trials;
       fr = new double[trials];
       int r1, r2;
       for(int i=0; i<trials; i++){
           double counter = 0;
           Percolation p = new Percolation(n);
           while (!p.percolates()){
               // Get a random number. If its not open, then open it.
               r1=StdRandom.uniform(1,n+1);
               r2=StdRandom.uniform(1,n+1);
               while (p.isOpen(r1,r2)){
                   r1=StdRandom.uniform(1,n+1);
                   r2=StdRandom.uniform(1,n+1);
               }
               p.open(r1, r2);
               counter++;
           }
           fr[i] = counter/size;
       }
   }

   public double mean(){
       m = StdStats.mean(fr);
       return m;
   };

   public double stddev(){
       s = StdStats.stddev(fr);
       return s;
   }

   public double confidenceLo(){
       return m-((1.96*s)/Math.sqrt(this.trials));
   }

   public double confidenceHi(){
       return m+((1.96*s)/Math.sqrt(this.trials));
   }

   public static void main(String[] args){
        PercolationStats pr = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

       System.out.println("mean = " + pr.mean());
       System.out.println("stddev = " + pr.stddev());
       System.out.println("95% confidence interval = " + pr.confidenceLo() + ", " + pr.confidenceHi());
   }
}
