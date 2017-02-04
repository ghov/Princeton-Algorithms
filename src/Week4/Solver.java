package Week4;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 * Created by greg on 1/19/17.
 */
public class Solver {

    private MinPQ<SearchNode> queue;
    private SearchNode init;
    private int counter = 0;
    private SearchNode finalNode;

    private class SearchNode{
        Board board = null;
        int moves = 0;
        SearchNode previousNode = null;

        public Board getBoard(){
            return board;
        }

        public SearchNode(Board b){
            board = b;
        }

        public SearchNode(Board b, int m, SearchNode previous){
            this.moves = m;
            this.board = b;
            this.previousNode = previous;
        }
        public int priority(){
            return board.hamming() + moves;
        }

        public void incrementMoves(){
            moves++;
        }
    }

    public Solver(Board initial) throws java.lang.NullPointerException {

        // Checks for null input
        if (initial == null) {
            throw new java.lang.NullPointerException();
        }

        queue = new MinPQ<>(this.comp());

        SearchNode temp;
        init = new SearchNode(initial);
        SearchNode temp2;

        queue.insert(init);
        while (!queue.isEmpty()) {
            if (queue.min().getBoard().isGoal()) {
                finalNode = queue.min();
                break;
            } else {
                this.counter++;
                temp = queue.delMin();
                // System.out.println(temp.board.toString());
                // while (!queue.isEmpty()) queue.delMin();
                for (Board b : temp.getBoard().neighbors()) {
                    if (counter == 1) {
                        temp2 = new SearchNode(b, this.counter, temp);
                        queue.insert(temp2);
                    } else {
                        if (!b.equals(temp.previousNode.board)) {
                            temp2 = new SearchNode(b, this.counter, temp);
                            queue.insert(temp2);
                        }
                    }
                }
            }
        }
    }

    private Comparator<SearchNode> comp(){
        return new myComp();
    }

    private class myComp implements Comparator<SearchNode>{
        public int compare(SearchNode a, SearchNode b){
            if(a.priority() == b.priority()) return 0;
            else if(a.priority() > b.priority()) return 1;
            else return -1;
        }
    }

    public boolean isSolvable(){

        // Use the initial game board and its twin. Only one of the boards will reach a goal.
        SearchNode original = init;
        SearchNode twin = new SearchNode(init.getBoard().twin());
        SearchNode tempOriginal;
        SearchNode tempTwin;

        MinPQ<SearchNode> queueOriginal = new MinPQ<>(this.comp());
        MinPQ<SearchNode> queueTwin = new MinPQ<>(this.comp());
        queueOriginal.insert(original);
        queueTwin.insert(twin);
        int counter = 0;

        while (!queueOriginal.isEmpty() || !queueTwin.isEmpty()){
            if(queueOriginal.min().getBoard().isGoal()){
                return true;
            }else if(queueTwin.min().getBoard().isGoal()){
                return false;
            }else {
                counter++;
                // Take out the min Board from each queue and insert the neighbors
                tempOriginal = queueOriginal.delMin();
                tempTwin = queueTwin.delMin();
                for (Board b : tempOriginal.getBoard().neighbors()){
                    queueOriginal.insert(new SearchNode(b, counter, tempOriginal));
                }
                for (Board b : tempTwin.getBoard().neighbors()){
                    queueTwin.insert(new SearchNode(b, counter, tempTwin));
                }
            }
        }
        return false;
    }

    public int moves(){
        int counter=0;
        SearchNode temp = finalNode;

        while (temp.previousNode!=null){
            counter++;
            temp = temp.previousNode;
        }

        return counter;
    }

    public Iterable<Board> solution(){
        Stack<Board> stack = new Stack<>() ;
        SearchNode temp = finalNode;

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
        if (false)//!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
