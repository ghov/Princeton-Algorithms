package Week4;

import edu.princeton.cs.algs4.In;

import java.util.Random;
import java.util.Stack;

/**
 * Created by greg on 1/19/17.
 */
public class Board {

    private int[] boardArray;
    private int[][] goalArray;
    private int dimension;

    private void createGoalArray(int dimension){
        goalArray = new int[dimension][dimension];
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                if(i == dimension-1  && j == dimension-1) goalArray[dimension-1][dimension-1] = 0;
                else {
                    if (i == 0) {
                        goalArray[i][j] = i + j + 1;
                    } else {
                        goalArray[i][j] = goalArray[i - 1][j] + dimension;
                    }
                }
            }
        }
    }

    private int[][] swapBlocks(int[][] inputArr, int firstI,int firstJ,int secondI,int secondJ){
        int tempVal1;
        int tempVal2;
        int[][] tempArray = new int[dimension][dimension];

        tempVal1 = this.boardArray[firstI][firstJ];
        tempVal2 = this.boardArray[secondI][secondJ];

        for(int i=0;i<dimension;i++) {
            for (int j = 0; j < dimension; j++) {
                tempArray[i][j] = inputArr[i][j];
            }
        }

        tempArray[firstI][firstJ] = tempVal2;
        tempArray[secondI][secondJ] = tempVal1;

        return tempArray;
    }

    public Board(int[][] blocks){

        dimension = blocks.length;
        boardArray = new int[blocks.length];
        createGoalArray(blocks.length);

        for(int i=0; i<blocks.length; i++){
            for(int j=0; j<blocks.length; j++){
                boardArray[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension(){
        return dimension;
    }

    // Hamming is calculated by finding the number of blocks out of position
    public int hamming(){
        int counter = 0;

        for(int i=0; i<dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (this.boardArray[i][j] != 0 && this.boardArray[i][j] != goalArray[i][j]) counter++;
            }
        }

        return counter;
    }

    public int manhattan() {

        int manhattenSum = 0;
        int tempVali = 0;
        int tempValj = 0;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if ((this.boardArray[i][j] != goalArray[i][j]) && (this.boardArray[i][j] != 0)) {

                    tempVali = (int) Math.floor((this.boardArray[i][j] - 1) / dimension);
                    tempValj = (this.boardArray[i][j] - 1) % dimension;

                    manhattenSum += Math.abs(i - tempVali) + Math.abs(j - tempValj);

                }
            }
        }
        return manhattenSum;
    }

    public boolean isGoal(){
        for(int i=0;i<dimension();i++){
            for(int j=0; j<dimension(); j++){
                if (this.boardArray[i][j] != goalArray[i][j]) return false;
            }
        }
        return true;
    }

    public Board twin(){
        // Must not be 0 and must not switch the same value
        Random random1 = new Random();
        Random random2 = new Random();
        int firstI = random1.nextInt(dimension);
        int firstJ = random1.nextInt(dimension);

        int secondI = random2.nextInt(dimension);
        int secondJ = random2.nextInt(dimension);

        // Checks that none of the values are 0 and that they are not the same
        while ((boardArray[firstI][firstJ] == 0) || (boardArray[secondI][secondJ] == 0)
                || (firstI==secondI && firstJ==secondJ)){
           firstI = random1.nextInt(dimension);
           firstJ = random1.nextInt(dimension);
           secondI = random2.nextInt(dimension);
           secondJ = random2.nextInt(dimension);
        }

        int[][] tempArr = new int[dimension][dimension];

        tempArr = swapBlocks(this.boardArray, firstI, firstJ, secondI, secondJ);

        Board newBoard = new Board(tempArr);
        return newBoard;
    }

    public boolean equals(Object y){
        if(this == y) return true;
        if(y == null) return false;
        if(this.getClass() != y.getClass()) return false;

        Board inputBoard = (Board) y;

        if(dimension != inputBoard.dimension()) return false;

        for(int i=0; i<boardArray.length; i++){
            for(int j=0; j<boardArray.length; j++){
                if (this.boardArray[i][j] != inputBoard.boardArray[i][j]) return false;
            }
        }

        return true;
    }


    // Make up to 4 new boards and put into container to return.
    // Each new board is made by switching the empty block with its neighbor
    public Iterable<Board> neighbors(){

        int [][] tempArr = new int[dimension][dimension];
        int blankI=0;
        int blankJ=0;
        Stack<Board> stack= new Stack<>();

        // Find the blank block
        for(int i=0; i<dimension; i++){
            for(int j=0; j<dimension; j++){
                if(this.boardArray[i][j] == 0){
                    blankI = i;
                    blankJ = j;
                }
            }
        }

        // Top
        if(blankI != 0){
            tempArr = swapBlocks(this.boardArray, blankI, blankJ, blankI-1, blankJ);
            stack.add(new Board(tempArr));
        }

        // Right
        if(blankJ != dimension-1){
            tempArr = swapBlocks(this.boardArray, blankI, blankJ, blankI, blankJ+1);
            stack.add(new Board(tempArr));
        }

        // Bottom
        if(blankI != dimension-1){
            tempArr = swapBlocks(this.boardArray, blankI, blankJ, blankI+1, blankJ);
            stack.add(new Board(tempArr));
        }

        // Left
        if(blankJ != 0){
            tempArr = swapBlocks(this.boardArray, blankI, blankJ, blankI, blankJ-1);
            stack.add(new Board(tempArr));
        }

        return stack;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for(int i=0; i<dimension(); i++){
            for(int j=0; j<dimension(); j++){
                s.append(String.format("%2d ", boardArray[i][j]));
            }
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

