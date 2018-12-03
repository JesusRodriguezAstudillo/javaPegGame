/*
** Author: Jesus Rodriguez
** Class: CSCE 4430
** Date: 3 December 2018
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/*
** A simple class that will hold over and to pegs
*/
class entry {
    int o, t;
    
    entry(int a, int b){
        o = a;
        t = b;
    }
}

/*
** a class that will serve as a collection of entries
*/

class entryArr{
    ArrayList<entry> collection = new ArrayList<entry>();
    
    public void addEntry(int a, int b){
        collection.add(new entry(a,b));
    }
    
    public int arrSize() {
        return collection.size();
    }
    
    public entry getEntry(int index) {
        return collection.get(index);
    }
}

/*
** The class that will solve the peg puzzle game
*/
public class peg {
    
    ArrayList<entryArr> moves;
    
    peg() {
        // allocate memory for the possible moves
        moves =  new ArrayList<entryArr>();
       
        // create memory for each entry in moves
        for(int i = 0; i < 15; i++) moves.add(new entryArr());
        
        // add the individual moves to the moves list
        moves.get(0).addEntry(1,3);
        moves.get(0).addEntry(2,5);
        moves.get(1).addEntry(3,6);
        moves.get(1).addEntry(4,8);
        moves.get(2).addEntry(4,7);
        moves.get(2).addEntry(5,9);
        moves.get(3).addEntry(1,0);
        moves.get(3).addEntry(6,10);
        moves.get(3).addEntry(7,12);
        moves.get(3).addEntry(4,5);
        moves.get(4).addEntry(7,11);
        moves.get(4).addEntry(8,13);
        moves.get(5).addEntry(2,0);
        moves.get(5).addEntry(8,12);
        moves.get(5).addEntry(4,3);
        moves.get(5).addEntry(9,14);
        moves.get(6).addEntry(3,1);
        moves.get(6).addEntry(7,8);
        moves.get(7).addEntry(4,2);
        moves.get(7).addEntry(8,9);
        moves.get(8).addEntry(4,1);
        moves.get(8).addEntry(7,6);
        moves.get(9).addEntry(8,7);
        moves.get(9).addEntry(5,2);
        moves.get(10).addEntry(6,3);
        moves.get(10).addEntry(11,12);
        moves.get(11).addEntry(7,4);
        moves.get(11).addEntry(12,13);
        moves.get(12).addEntry(7,3);
        moves.get(12).addEntry(8,5);
        moves.get(12).addEntry(11,10);
        moves.get(12).addEntry(13,14);
        moves.get(13).addEntry(8,4);
        moves.get(13).addEntry(12,11);
        moves.get(14).addEntry(9,5);
        moves.get(14).addEntry(13,12);
    }
    
    /*
    ** the function that initiates the board and prints the result
    */
    public void initBoard(int start) {
        ArrayList<Integer> board = new ArrayList<Integer>(); // the peg board
        ArrayList<int[] > solveMoves = new ArrayList<int[]>(); // the array that will hold all the moves
        
        // add the pegs to the board
        for(int i = 0; i < 15; i++) board.add(i);
        
        // remove the starting peg
        board.remove(new Integer(start));
        // solve the peg puzzle
        solve(board, solveMoves);
        
        // print the results
        print(start, solveMoves);
    }
    
    /*
    ** the function that will solve the peg game
    */
    public ArrayList<Integer> solve(ArrayList<Integer> board, ArrayList<int[]> sMoves) {
        
        if(board.size() < 2) return board; // if there is only one peg, finished
        
        // loop through the pegs and determine which moves are possible
        for(int i = 0; i < 15; i++) {
            
            // if the peg is in the board
            if(board.contains(i)){
                
                // check each entry beloging to the peg
                for(int j = 0; j < moves.get(i).arrSize(); j++){
                    Integer o = moves.get(i).getEntry(j).o;
                    Integer t = moves.get(i).getEntry(j).t;
                    
                    // if the over peg is in and the to peg is not in
                    if(board.contains(o) && !(board.contains(t))){
                        
                        // copy the array
                        ArrayList<Integer> boardCopy = new ArrayList<Integer>(board);
                       
                        // change the board with the peg move
                        board.remove(new Integer(i));
                        board.remove(new Integer(o));
                        board.add(t);
                        
                        // add the move to the moves
                        int[] entry = {i,o,t};
                        sMoves.add(entry);
                        
                        // recursively solve the puzzle
                        board = solve(board, sMoves);
                        
                        // if the board only has one peg
                        if(board.size() < 2) return board;
                        
                        // recopy the old board
                        board = new ArrayList<Integer>(boardCopy);
                        
                        // remove the last move
                        sMoves.remove(sMoves.size() - 1);
                    }
                }
            }
        }
        return board;
    }
    
    /*
    ** a function that calls the appriate start function
    */
    public void play(int start) {
    
        initBoard(start);
        
        return;
    }
    /*
    ** the function that prints the solution
    */
    public void print(int start, ArrayList<int[]> solveMoves) {
        
        // recreate the board and initialize it
        ArrayList<Integer> board = new ArrayList<Integer>(); 
        for(int i = 0; i < 15; i++) board.add(i);
        board.remove(new Integer(start));
        
        // print the board at the start
        printBoard(board);
        
        // for each move to solve the puzzle
        for(int i = 0; i < solveMoves.size(); i++) {
            
            // update the current board and print
            int f = solveMoves.get(i)[0];
            int o = solveMoves.get(i)[1];
            int t = solveMoves.get(i)[2];
            
            board.remove(new Integer(f));
            board.remove(new Integer(o));
            board.add(t);
            
            printBoard(board);
        }
    }
    
    /*
    ** the function that prints the board as it changes
    */
    public void printBoard(ArrayList<Integer> board) {
        char[] pegBoard = {'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'};
        
        for(int i = 0; i < board.size(); i++) pegBoard[board.get(i)] = 'X';
        
        System.out.println("    " + pegBoard[0]);
        System.out.println("   " + pegBoard[1] + " " + pegBoard[2]);
        System.out.println("  " + pegBoard[3] + " " + pegBoard[4] + " " + pegBoard[5]);
        System.out.println(" " + pegBoard[6] + " " + pegBoard[7] + " " + pegBoard[8] + " " + pegBoard[9]);
        System.out.println(pegBoard[10] + " " + pegBoard[11] + " " + pegBoard[12] + " " + pegBoard[13] + " " + pegBoard[14]);
    }
    
    public static void main(String[] args) {
        peg pegGame = new peg();
        
        for(int i = 0; i < 5; i++ ) {
	    System.out.println("===========================================");
	    System.out.println("Starting Game with open peg at: " + i);
            pegGame.play(i);
        }
    }
}

