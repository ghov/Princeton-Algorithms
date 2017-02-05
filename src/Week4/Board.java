package Week4;

import edu.princeton.cs.algs4.In;

import java.util.Random;
import java.util.Stack;

/**
 * Created by greg on 1/19/17.
 */

// How to convert 1-D array values to 2-D array values. In other words, take i and create i,j

public class Board {

    private int[] boardArray;
    private int dimension;
    private int dimensionSquared;

    private int[][] swapBlocks(int[] inputArr, int firstI,int secondI){
        int tempVal1;
        int tempVal2;
        int firstTempI;
        int secondTempI;
        int firstTempJ;
        int secondTempJ;
        int[][] tempArray = new int[dimension][dimension];
        int counter = 0;

        if(firstI < dimension){
            firstTempI = 0;
        }else {
            firstTempI = (int)Math.floor((double)firstI/dimension);
        }
        firstTempJ = firstI % dimension;

        if(secondI < dimension){
            secondTempI = 0;
        }else {
            secondTempI = (int)Math.floor((double)secondI/dimension);
        }
        secondTempJ = secondI % dimension;


        tempVal1 = this.boardArray[firstI];
        tempVal2 = this.boardArray[secondI];

        // create a 2-d array from the given 1-D array
        for(int i=0;i<dimension;i++) {
            for(int j=0; j<dimension; j++){
                tempArray[i][j] = inputArr[counter++];
            }
        }

        tempArray[firstTempI][firstTempJ] = tempVal2;
        tempArray[secondTempI][secondTempJ] = tempVal1;

        return tempArray;
    }

    public Board(int[][] blocks){

        dimension = blocks.length;
        dimensionSquared = blocks.length*blocks.length;
        boardArray = new int[dimensionSquared];
        int counter = 0;

        // Probably dont want to hold the goal array in memory, takes up too much memory
        // createGoalArray(blocks.length);

        for(int i=0; i<blocks.length; i++){
            for(int j=0; j<blocks.length; j++){
                boardArray[counter++] = blocks[i][j];
            }
        }
    }

    public int dimension(){
        return dimension;
    }

    // Hamming is calculated by finding the number of blocks out of position
    public int hamming(){
        int counter = 0;

        for(int i = 0; i<dimensionSquared; i++){
            if(i==(dimensionSquared-1)){
                if(this.boardArray[i] != 0) counter++;
            }
            else {
                if (this.boardArray[i] != 0 && this.boardArray[i] != i+1) counter++;
            }
        }
        return counter;
    }

    public int manhattan() {

        int manhattenSum = 0;
        int tempVali = 0;
        int tempValj = 0;

        int iToi = 0;
        int iToj = 0;

        for (int i = 0; i < dimensionSquared; i++) {
            if (i == (dimensionSquared - 1)) {
                if (this.boardArray[i] != 0) {

                    tempVali = (int) Math.floor((this.boardArray[i] - 1) / dimension);
                    tempValj = (this.boardArray[i]-1) % dimension;

                    iToi = (int) Math.floor(i/dimension);
                    iToj = i % dimension;
                    manhattenSum += Math.abs(iToi - tempVali) + Math.abs(iToj - tempValj);
                }
            } else {
                if ((this.boardArray[i] != i+1) && (this.boardArray[i] != 0)) {

                    tempVali = (int) Math.floor((this.boardArray[i] - 1) / dimension);//2
                    tempValj = (this.boardArray[i]-1) % dimension;//0

                    iToi = (int) Math.floor(i/dimension);

                    if(i<dimension){
                        iToj = i;
                    }else{
                        iToj = i % dimension;
                    }

                    manhattenSum += Math.abs(iToi - tempVali) + Math.abs(iToj - tempValj);
                }
            }
        }
        return manhattenSum;
    }

    public boolean isGoal(){
        for(int i=0;i<dimensionSquared;i++){
            if(i==(dimensionSquared-1)){
                return this.boardArray[i] == 0;
            }else {
                if (this.boardArray[i] != i+1) return false;
            }
        }
        return true;
    }

    public Board twin(){
        // Must not be 0 and must not switch the same value
        Random random1 = new Random();
        Random random2 = new Random();
        int firstI = random1.nextInt(dimensionSquared);

        int secondI = random2.nextInt(dimensionSquared);

        // Checks that none of the values are 0 and that they are not the same
        while ((boardArray[firstI] == 0) || (boardArray[secondI] == 0)
                || (firstI==secondI)){
           firstI = random1.nextInt(dimensionSquared);
           secondI = random2.nextInt(dimensionSquared);
        }

        int[][] tempArr;

        tempArr = swapBlocks(this.boardArray, firstI, secondI);

        Board newBoard = new Board(tempArr);
        return newBoard;
    }

    public boolean equals(Object y){
        if(this == y) return true;
        if(y == null) return false;
        if(this.getClass() != y.getClass()) return false;

        Board inputBoard = (Board) y;

        if(dimension != inputBoard.dimension()) return false;

        for(int i=0; i<dimensionSquared; i++){
                if (this.boardArray[i] != inputBoard.boardArray[i]) return false;
        }

        return true;
    }


    // Make up to 4 new boards and put into container to return.
    // Each new board is made by switching the empty block with its neighbor
    public Iterable<Board> neighbors(){

        int [][] tempArr;
        int blankI=0;
        Stack<Board> stack= new Stack<>();

        // Find the blank block
        for(int i=0; i<dimensionSquared; i++){
            if(this.boardArray[i] == 0){
                blankI = i;
            }
        }

        // Top
        //if(blankI != 0){
        if(blankI>=dimension){
            tempArr = swapBlocks(this.boardArray, blankI, blankI-dimension);
            stack.add(new Board(tempArr));
        }

        // Right
        if(((blankI+1)% dimension) !=0){
            tempArr = swapBlocks(this.boardArray, blankI, blankI+1);
            stack.add(new Board(tempArr));
        }

        // Bottom
        if(((int)Math.floor(((double)blankI)/dimension)) < (dimension-1)) {
            tempArr = swapBlocks(this.boardArray, blankI, blankI+dimension);
            stack.add(new Board(tempArr));
        }

        // Left
        if((blankI % dimension) != 0){
            tempArr = swapBlocks(this.boardArray, blankI, blankI-1);
            stack.add(new Board(tempArr));
        }

        return stack;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for(int i=0; i<dimensionSquared; i++){
            s.append(String.format("%2d ", boardArray[i]));
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args){

        int[][] test1 = new int[10][10];
        int[][] test2 = new int[10][10];
        int[][] test3 = new int[1][1];

        test3[0][0] = 1;

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                test1[i][j] = i+j;
                test2[i][j] = i+j;
            }
        }

        // test2[0][0] = 99;

        Board board = new Board(test1);
        Board board2 = new Board(test2);
        Board board3 = new Board(test3);

        // System.out.println(board.dimension());
        // System.out.println(board.equals(board2));
        // System.out.println(board.toString());
        // System.out.println(board3.isGoal());

        // System.out.println(board3.hamming());
        // System.out.println(board3.manhattan());

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // System.out.println(initial.twin());
        System.out.println(initial.manhattan());


    }

}

