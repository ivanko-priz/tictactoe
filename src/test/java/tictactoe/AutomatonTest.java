package tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AutomatonTest {
    @Test
    public void testFindGoodMoveOnEmptyBoard() {
        Game g = new Game();
        Automaton a = new Automaton(g);
        int goodMove = 4;

        assertEquals(goodMove, a.findGoodMove());
    }

    @Test
    public void testFindGoodMoveInWinningPosition() throws Exception {
        Game g;
        Automaton a;

        int[][] gameplays = {
            {0, 3, 1, 4, 8},
            {6, 4, 0, 1, 5},
            {0, 8, 1, 4},
            {2, 4, 5, 7}
        };
        char expectedWinner;

        for (int[] moves : gameplays) {
            g = new Game();
            a = new Automaton(g);
            
            expectedWinner = moves.length % 2 == 0 ? 'x' : 'o';

            for (int move : moves) {
                g.makeMove(move);
            }

            g.makeMove(a.findGoodMove());

            assertTrue("Game should be over", g.isGameOver());
            assertEquals(expectedWinner, g.getWinner());
        }
    }

    @Test
    public void testFindGoodMoveAgainstItself() throws Exception {
        Game g = new Game();
        Automaton a = new Automaton(g);
        char expectedWinner = 0;

        while(!g.isGameOver()) {
            g.makeMove(a.findGoodMove());
        }

        assertEquals(expectedWinner, g.getWinner());
    }
}
