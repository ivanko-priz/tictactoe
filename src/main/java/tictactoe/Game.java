package tictactoe;

public class Game {
    private char[] field;
    private char sideToMove;
    private boolean isOver;
    private int movesMade;
    private char winner;
    private final char X = 'x';
    private final char O = 'o';

    Game() {
        this.field = new char[9];
        this.sideToMove = this.X;
        this.isOver = false;
        this.movesMade = 0;
    }

    private Game(char[] field, char sideToMove, boolean isOver, int movesMade) {
        this.field = field.clone();
        this.sideToMove = sideToMove;
        this.isOver = isOver;
        this.movesMade = movesMade;
    }

    public void makeMove(int coord) throws Exception {
        if (this.isOver) {
            return;
        }
        
        if (coord < 0 || coord >= this.field.length) {
            throw new Exception("Wrong coordinates");
        }

        if (this.field[coord] != 0) {
            throw new Exception("Square already marked");
        }

        this.field[coord] = this.sideToMove;
        this.sideToMove = this.sideToMove == this.X ? this.O : this.X;
        this.movesMade += 1;

        this.isOver = checkIfOver();
    }   
    
    private boolean checkIfOver() {
        int[][] winningCombos = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},

            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},

            {0, 4, 8},
            {6, 4, 2}
        };
        char val;

        for (int[] combo: winningCombos) {
            val = this.field[combo[0]];
            if (val == 0) continue;

            for (int i = 1; i < combo.length; i++) {
                if (val != this.field[combo[i]]) break;
                else if (i == combo.length - 1) {
                    this.winner = val;

                    return true;
                }
            }
        }

        if (getLegalMoves().length == 0) {
            return true;
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

    public char getWinner() {
        return this.winner;
    }

    public int[] getLegalMoves() {
        int[] moves;

        if (!this.isOver) {
            int j = 0;
            moves = new int[this.field.length - this.movesMade];

            for (int i = 0; i < this.field.length; i++) {
                if (this.field[i] == 0) {
                    moves[j] = i;
                    j += 1;
                }
            }
        } else {
            moves = new int[0];
        }

        return moves;
    }

    public Game copy() {
        return new Game(this.field, this.sideToMove, this.isOver, this.movesMade);
    }

}
