package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        playWithAutomaton();
    }

    public static void playWithAutomaton() throws Exception {
        Game g = new Game();
        Automaton a = new Automaton(g);
        Scanner in = new Scanner(System.in);
        
        while (!g.isGameOver()) {
            System.out.print("Your turn, human: ");
            int move = in.nextInt();

            System.out.println("Human played " + move);
            g.makeMove(move);
            System.out.println(Arrays.toString(g.getField()));

            move = a.findGoodMove();
            System.out.println("Machine played " + move);
            g.makeMove(move);

            System.out.println(Arrays.toString(g.getField()));
        }

        in.close();

        System.out.println("Game over");
        switch (g.getWinner()) {
            case 0:
                System.out.println("Draw");
                break;
            case 'x':
                System.out.println("Human won");
                break;
            default:
                System.out.println("Machine won");
                break;
        }
    }
}
