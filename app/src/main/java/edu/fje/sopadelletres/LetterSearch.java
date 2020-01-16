package edu.fje.sopadelletres;

import java.util.Random;

public class LetterSearch {
    private final char[] alphabet = {
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private char[][] emptyBoard;
    private char[][] fullBoard;
    private String[] words;
    private int size;

    public LetterSearch(String[] wordList, int size) {
        words = wordList;
        this.size = size;
        emptyBoard = new char[size][size];
        for(int r = 0; r< emptyBoard.length; r++) {
            for (int c = 0; c< emptyBoard[0].length; c++) {
                emptyBoard[r][c] = ' ';
            }
        }
        for(String word: words) {
            add(word, emptyBoard);
        }
        emptyBoard = fillRestSolution(emptyBoard);
        fullBoard = new char[emptyBoard.length][emptyBoard.length];
        for (int r = 0; r< emptyBoard.length; r++) {
            for (int c = 0; c< emptyBoard[0].length; c++) {
                fullBoard[r][c] = emptyBoard[r][c];
            }
        }
        emptyBoard = fillRestRandom(emptyBoard);
    }

    private void add(String w, char[][] board) {
        String word = w.toUpperCase();
        char[][] original = new char[board.length][board.length];
        for (int r=0; r<board.length; r++) {
            for (int c=0; c<board[0].length; c++) {
                original[r][c] = board[r][c];
            }
        }
        Random r = new Random();
        boolean flip = r.nextBoolean();
        if(flip) { word = flip(word); }

        int orientation = r.nextInt(4);
        int row = board.length;
        int col = board.length;

        if      (orientation == 0 && col>board.length-word.length()) { col -= word.length(); }
        else if (orientation == 1 && row>board.length-word.length()) { row -= word.length(); }
        else if (orientation == 2) {
            if(col>board.length-word.length()) col -= word.length();
            if(row>board.length-word.length()) row -= word.length();
        } else if (orientation == 3) {
            if(col>board.length-word.length()) col = board.length-word.length();
        }
        int tryRow, tryCol;
        boolean filled = false;
        do {
            tryRow = r.nextInt(row);
            if (orientation == 3 && tryRow<word.length()) tryRow = word.length();
            tryCol = r.nextInt(col);
            for (int i = 0; i < word.length(); i++) {
                if (board[tryRow][tryCol] == ' ' || board[tryRow][tryCol] == word.charAt(i)) {
                    board[tryRow][tryCol] = word.charAt(i);
                    if      (orientation == 0) tryCol++;
                    else if (orientation == 1) tryRow++;
                    else if (orientation == 2) { tryCol++; tryRow++; }
                    else if (orientation == 3) { tryCol++; tryRow--; }
                } else {
                    for(int j=i; j>0; j--) {
                        if      (orientation == 0) tryCol--;
                        else if (orientation == 1) tryRow--;
                        else if (orientation == 2) { tryCol--; tryRow--; }
                        else if (orientation == 3) { tryCol--; tryRow++; }
                        board[tryRow][tryCol] = original[tryRow][tryCol];
                    }
                    filled = false;
                    break;
                }
                filled = true;
            }
        } while (!filled);
    }

    private String flip(String word) {
        StringBuilder flipped = new StringBuilder();
        for(int i=word.length()-1; i>=0; i--)
            flipped.append(word.charAt(i));
        return flipped.toString();
    }
    private char[][] fillRestRandom(char[][] board) {
        Random rdm = new Random();
        int randChar;
        for(int r=0; r<board.length; r++) {
            for(int c=0; c<board[0].length; c++) {
                if(board[r][c] == '.') {
                    randChar = rdm.nextInt(alphabet.length);
                    board[r][c] = alphabet[randChar];
                }
            }
        }
        return board;
    }
    private char[][] fillRestSolution(char[][] board) {
        for(int r=0; r<board.length; r++) {
            for(int c=0; c<board[0].length; c++) {
                if(board[r][c] == ' ') { board[r][c] = '.'; }
            }
        }
        return board;
    }

    public char[][] getWordSearch() {
        return emptyBoard;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("Puzzle:\n");
        for(int r = 0; r< emptyBoard.length; r++) {
            for(int c = 0; c< emptyBoard.length; c++) {
                ret.append(emptyBoard[r][c]);
                ret.append(' ');
            }
            ret.append("\n");
        }
        ret.append("\n");
        ret.append("Solution:\n");
        for(int r = 0; r< fullBoard.length; r++) {
            for(int c = 0; c< fullBoard.length; c++) {
                ret.append(fullBoard[r][c]);
                ret.append(' ');
            }
            ret.append("\n");
        }
        return ret.toString();
    }
}
