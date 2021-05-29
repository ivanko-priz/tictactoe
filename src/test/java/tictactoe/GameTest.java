package tictactoe;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GameTest {
    private final String SQUARE_ALREADY_MARK_EXCEPTION_MSG = "Square already marked";
    private final String WRONG_COORDINATE_EXCEPTION_MSG = "Wrong coordinates";
    private final char X = 'x';
    private final char O = 'o';

    @Test
    public void testMakeMoveOnSameCoordThrows() {
        Game g = new Game();
        
        try {
            g.makeMove(0);
            g.makeMove(0);
        } catch(Exception e) {
            assertEquals(SQUARE_ALREADY_MARK_EXCEPTION_MSG, e.getMessage());
        }
    }

    @Test
    public void testMakeMoveWithWrongCoordinates() {
        Game g = new Game();

        try {
            g.makeMove(-1);
        } catch (Exception e) {
            assertEquals(WRONG_COORDINATE_EXCEPTION_MSG, e.getMessage());
        }

        try {
            g.makeMove(g.getField().length);
        } catch (Exception e) {
            assertEquals(WRONG_COORDINATE_EXCEPTION_MSG, e.getMessage());
        }
    }

    @Test
    public void testMakeMoveUpdatesClassFields() throws Exception {
        char[] expectedField = new char[9];
        char expectedSideToMove = X;

        expectedField[0] = X;

        Game g = new Game();

        assertEquals(expectedSideToMove, g.getSideToMove());
        
        g.makeMove(0);

        assertArrayEquals(expectedField, g.getField());

        expectedSideToMove = O;

        assertEquals(expectedSideToMove, g.getSideToMove());
    }

    @Test
    public void testCopy() throws Exception {
        Game g = new Game();
        Game cp;

        char[] expected = new char[9];
        expected[0] = X;
        expected[5] = O;

        g.makeMove(0);
        g.makeMove(5);

        cp = g.copy();

        g.makeMove(3);

        assertArrayEquals(expected, cp.getField());
        assertNotEquals(g.getSideToMove(), cp.getSideToMove());
    }

    @Test
    public void testCheckIfOverWhenWinning() throws Exception {
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

        Game g = new Game();

        assertFalse("Game should not be over", g.isGameOver());

        for (int[] combo : winningCombos) {
            int step = Math.abs(combo[0] - combo[1]) == 3 ? 1 : 3;

            for (int coord : combo) {
                g.makeMove(coord);
                g.makeMove((coord + step) % 9);
            }

            assertTrue("Game should be over", g.isGameOver());
            assertEquals(X, g.getWinner());

            g = new Game();
        }
    }

    @Test
    public void testCheckIfOverWhenNoMoves() throws Exception {
        Game g = new Game();

        for (int i = 0; i < g.getField().length; i++) {
            g.makeMove((i + 6) % 9);
        }

        assertTrue("Game should be over", g.isGameOver());
        assertEquals(0, g.getWinner());
    }

    @Test
    public void testGetLegalMoves() throws Exception {
        Game g = new Game();

        List<Integer> legalMoves = new ArrayList<>();
        int[] expected;
        int coord;

        for (int i = 0; i < g.getField().length; i++) {
            legalMoves.add(i);
        }

        for (int i = 0; i < g.getField().length; i++) {
            coord = (i + 6) % 9;
            expected = legalMoves.stream().mapToInt(n -> n).toArray();
            
            assertArrayEquals(expected, g.getLegalMoves());

            legalMoves.remove((Object)coord);
            g.makeMove(coord);
        }
    }
}
