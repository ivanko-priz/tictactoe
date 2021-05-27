package tictactoe;

import java.util.TreeMap;

public class Automaton {
    private Game g;
    private TreeMap<Integer, Integer> moves;
    private char currentPlayer;

    Automaton(Game g) {
        this.g = g;
        this.moves = new TreeMap<>();
    }

    public int findGoodMove() {
        int[] legalMoves = this.g.getLegalMoves();
        
        this.currentPlayer = this.g.getSideToMove();
        this.moves = new TreeMap<>();

        for (int legalMove : legalMoves) {
            if (isMoveWinning(this.g.copy(), legalMove)) {
                this.moves.put(legalMove, this.moves.containsKey(legalMove) ? this.moves.get(legalMove) + 1 : 1);
            }
        }

        if (this.moves.size() == 0) {
            return -1;
        } else {
            // System.out.println("Good moves are:");
            // System.out.println(this.moves);
            
            int maxKey = 0;

            for (int key : this.moves.keySet()) {
                if (this.moves.get(key) > this.moves.get(maxKey)) {
                    maxKey = key;
                }
            }

            return maxKey;
        }
    }

    private boolean isMoveWinning(Game g, int move) {
        try {
            g.makeMove(move);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        if (g.isGameOver()) {
            if (g.getWinner() != g.getSideToMove()) {
                // if (g.getSideToMove() != this.currentPlayer) {
                //     this.moves.put(move, this.moves.containsKey(move) ? this.moves.get(move) + 1 : 1);
                // }

                return true;
            }
        
            return false;
        }

        int[] legalMoves = g.getLegalMoves();

        for (int legalMove : legalMoves) {
            if (!isMoveWinning(g.copy(), legalMove)) {
                if (g.getSideToMove() != currentPlayer) {
                    this.moves.put(legalMove, this.moves.containsKey(legalMove) ? this.moves.get(legalMove) + 1 : 1);
                }

                return true;
            }
        }

        return false;
    }

}
