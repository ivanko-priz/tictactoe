package tictactoe;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Game g = new Game();
        Automaton a = new Automaton(g);

        int goodMove = a.findGoodMove();

        System.out.println("Good move is: " + goodMove);
    }
}
