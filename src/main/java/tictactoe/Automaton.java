package tictactoe;

import java.util.Arrays;

public class Automaton {
    private Game g;
    private int[] depth;
    private int[] moves;

    private char currentPlayer;

    Automaton(Game g) {
        this.g = g;
    }

    public int findGoodMove() {
        this.moves = new int[9];
        this.depth = new int[9];
        this.currentPlayer = this.g.getSideToMove();

        int[] legalMoves = this.g.getLegalMoves();
        
        for (int legalMove : legalMoves) {
            isMoveWinning(this.g.copy(), legalMove, 1);
        }

        if (this.moves.length == 0) {
            return -1;
        } else {
            // System.out.println(Arrays.toString(this.depth));
            // System.out.println(Arrays.toString(this.moves));

            int bestMove = legalMoves[0];

            for (int i = 0; i < this.moves.length; i++) {
                if (
                    this.moves[i] > this.moves[bestMove]
                    || (this.moves[i] == this.moves[bestMove]
                    && this.depth[i] != 0
                    && this.depth[i] < this.depth[bestMove])
                ) {
                    bestMove = i;
                }
            }

            return bestMove;
        }
    }

    private boolean isMoveWinning(Game g, int move, int d) {
        boolean hasWinningMoves = false;

        try {
            g.makeMove(move);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        if (g.isGameOver()) {
            if (g.getWinner() == this.currentPlayer) {
                this.moves[move] += 1;

                if (this.depth[move] > d || this.depth[move] == 0) {
                    this.depth[move] = d;
                }

                return true;
            }
        
            return false;
        }

        int[] legalMoves = g.getLegalMoves();

        for (int legalMove : legalMoves) {
            if (!isMoveWinning(g.copy(), legalMove, d+1)) {
                if (g.getSideToMove() != currentPlayer) {
                    this.moves[move] += 1;

                    hasWinningMoves = true;
                }
            }
        }

        return hasWinningMoves;
    }
}
