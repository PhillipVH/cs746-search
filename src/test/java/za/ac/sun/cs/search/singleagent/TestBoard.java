package za.ac.sun.cs.search.singleagent;

import org.junit.Before;
import org.junit.Test;

public class TestBoard {

    /* Configuration stolen from https://algorithmsinsight.wordpress.com/graph-theory-2/a-star-in-general/implementing-a-star-to-solve-n-puzzle/. */
    short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};

    Board board;

    @Before
    public void setup()  {
        board = new ExplicitBoard(configuration);
    }

    @Test
    public void smokeTest() {
        System.out.println(board.toString());
    }

    @Test
    public void putAtTest() {

    }
}
