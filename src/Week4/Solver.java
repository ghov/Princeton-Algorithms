package Week4;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;


/**
 * Created by greg on 1/19/17.
 */
public class Solver {

    private boolean solveable = false;
    private SearchNode finalNode;

    private class SearchNode implements Comparable<SearchNode>{
        Board board;
        int moves;
        SearchNode previousNode = null;
        int priority;

        public SearchNode(Board b){
            this(b, null);
        }

        public SearchNode(Board b, SearchNode previous){
            this.board = b;
            this.previousNode = previous;

            if(previous==null){
                moves = 0;
            }else{
                moves = previous.moves+1;
            }

            this.priority = this.board.manhattan()+this.moves;
        }

        public int compareTo(SearchNode inputNode){
            if(this.priority == inputNode.priority) return 0;
            else if(this.priority > inputNode.priority) return 1;
            else return -1;
        }
    }

    public Solver(Board initial) throws java.lang.NullPointerException {

        if(initial == null){
            throw new java.lang.NullPointerException();
        }

        MinPQ<SearchNode> queue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();

        queue.insert(new SearchNode(initial));
        twinQueue.insert(new SearchNode(initial.twin()));

        // Try the normal board, then the twin board
        while(!this.solveable){
            this.finalNode = solveQueue(queue);
            // if(this.finalNode!=null && this.finalNode.moves > 100) return;
            if(this.finalNode!=null){
                this.solveable = true;
                return;
            }
            this.finalNode = solveQueue(twinQueue);
            // if(this.finalNode!=null && this.finalNode.moves > 100) return;
            if(this.finalNode!=null){
                this.finalNode = null;
                return;
            }
        }
    }

    private SearchNode solveQueue(MinPQ<SearchNode> inputQueue){
        // Takes a MinPQ, either original or twin, and tries to solve it.
        SearchNode lastNode;

        if(inputQueue.isEmpty()) lastNode = null;
        lastNode = inputQueue.delMin();
        if(lastNode.board.isGoal()){
            return lastNode;
        }else {
            for(Board b : lastNode.board.neighbors()){
                if(lastNode.previousNode == null || !b.equals(lastNode.previousNode.board)){
                    inputQueue.insert(new SearchNode(b, lastNode));
                }
            }
        }
        return null;
    }

    public boolean isSolvable(){
        return this.solveable;
    }

    public int moves(){
        if(this.finalNode != null) return this.finalNode.moves;
        else return -1;
    }

    public Iterable<Board> solution(){
        if(!solveable){
            return null;
        }

        Stack<Board> stack = new Stack<>() ;
        SearchNode temp = this.finalNode;

        stack.push(temp.board);

        while (temp.previousNode!=null){
            stack.push(temp.previousNode.board);
            temp = temp.previousNode;
        }
        return stack;
    }

    public static void main(String[] args){

// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())//!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
