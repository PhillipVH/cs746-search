package za.ac.sun.cs.search.singleagent;

import org.junit.Before;
import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Board.Board;
import za.ac.sun.cs.search.singleagent.Board.ExplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedTilesHeuristic;

public class TestBoard {

    /* Configuration stolen from https://algorithmsinsight.wordpress.com/graph-theory-2/a-star-in-general/implementing-a-star-to-solve-n-puzzle/. */
    short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};

    Board board;

    @Before
    public void setup()  {
        board = new ExplicitBoard(configuration, new MisplacedTilesHeuristic());
    }

    @Test
    public void smokeTest() {
        System.out.println(board.toString());
    }

    @Test
    public void putAtTest() {

    }
}
