package JavaProjectsC.Game1;

import java.util.*;

class TicTacToe {
    static char[][] board;

    public TicTacToe() {
        board = new char[3][3];
        initBoard();
    }

    void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    static void displayBoard() {
        System.out.println("-------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    static void placeMark(int row, int column, char mark) {
        try {
            board[row][column] = mark;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid Position");
        }
    }

    static boolean colCheckWin() {
        for (int j = 0; j <= 2; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    static boolean rowCheckWin() {
        for (int i = 0; i <= 2; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    static boolean diagCheckWin() {
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true;
        }
        if (board[2][0] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true;
        }
        return false;
    }

    static boolean checkDraw(){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j]==' '){
                    return false;
                }
            }
        }
        return true;
    }
}

abstract class Player{
    String name;
    char mark;

    abstract void makeMove();

    boolean isValid(int row, int column) {
        if (row >= 0 && row <= 2 && column >= 0 && column <= 2) {
            if (TicTacToe.board[row][column] == ' ') {
                return true;
            }
        }
        return false;
    }
}

class AI_Player extends Player{

    AI_Player(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() {
        Scanner sc = new Scanner(System.in);
        int row, column;
        do {
            System.out.println("Enter row and column:");
            row = sc.nextInt();
            column = sc.nextInt();
        } while (!isValid(row, column));
        TicTacToe.placeMark(row, column, mark);
    }

}

class HumanPlayer extends Player {
    
    HumanPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    void makeMove() {
        Scanner sc = new Scanner(System.in);
        int row, column;
        do {
            System.out.println("Enter row and column:");
            row = sc.nextInt();
            column = sc.nextInt();
        } while (!isValid(row, column));
        TicTacToe.placeMark(row, column, mark);
    }
}

public class LaunchGame {
    public static void main(String[] args) {
        TicTacToe g1 = new TicTacToe();
        HumanPlayer p1 = new HumanPlayer("Rahul", 'X');
        HumanPlayer p2 = new HumanPlayer("Vishal", 'O');
        TicTacToe.displayBoard();
        Player currentPlayer;
        currentPlayer = p1;
        while(true){
            System.out.println(currentPlayer.name + " Make a move");
            currentPlayer.makeMove();
            TicTacToe.displayBoard();
            if (TicTacToe.checkDraw() || TicTacToe.colCheckWin() || TicTacToe.diagCheckWin() || TicTacToe.rowCheckWin()) {
                if(TicTacToe.checkDraw()){
                    System.out.println("It is a DRAW!");
                    break;
                }
                System.out.println(currentPlayer.name + " has Won");
                break;
            }else {
                if (currentPlayer == p1) {
                    currentPlayer = p2;
                } else {
                    currentPlayer = p1;
                }
            }
        } 
    }
}
