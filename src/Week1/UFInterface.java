package Week1;

/**
 * Created by greg on 9/7/16.
 */
public interface UFInterface {

    // Constructor takes int N as the number of objects

    // Add connected between p and q
    void union(int p, int q);

    // True if p and q are connected
    boolean connected(int p, int q);

    // Component identifier for p(0 to N-1)
    //int find(p);

    // Return number of components
    //int count();
}
