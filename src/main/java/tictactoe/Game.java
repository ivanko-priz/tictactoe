package tictactoe;

public class Game {
    private char[] field;
    private char sideToMove;
    private boolean isOver;
    private final char X = 'x';
    private final char O = 'o';

    Game() {
        this.field = new char[9];
        this.sideToMove = this.X;
        this.isOver = false;
    }

    public void makeMove(int coord) throws Exception {
        if (this.isOver) {
            throw new Exception("Game is already over");
        }

        if (coord < 0 || coord >= this.field.length) {
            throw new Exception("Wrong coordinates");
        }

        if (this.field[coord] == 'x' || this.field[coord] == 'y') {
            throw new Exception("Square already marked");
        }

        this.field[coord] = this.sideToMove;
        this.sideToMove = this.sideToMove == this.X ? this.O : this.X;

        this.isOver = checkIfOver();
    }   
    
    private boolean checkIfOver() {
        int[][] winningCombo = {
            {0, 1, 2},
            {3, 4, 5},
            {7, 8, 9},

            {0, 3, 7},
            {1, 4, 8},
            {2, 5, 9},

            {0, 4, 9},
            {7, 4, 2}
        };
        char val;

        for (int[] combo: winningCombo) {
            val = this.field[combo[0]];
            if (val != this.O || val != this.X) break;

            for (int i = 1; i < combo.length; i++) {
                if (val != this.field[combo[i]]) break;
                else if (i == combo.length - 1) return true;
            }
        }

        return false;
    }

    public boolean isGameOver() {
        return this.isOver;
    }

    public char[] getField() {
        return this.field;
    }

    public char getSideToMove() {
        return this.sideToMove;
    }

}
