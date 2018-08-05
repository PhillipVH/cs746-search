package za.ac.sun.cs.search.singleagent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class TestExplicitBoard {

    ExplicitBoard board;
    short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};

    @Before
    public void setupBoard() {
        board = new ExplicitBoard(configuration);
    }

    @Test
    public void testGetNeighbors() {

        LinkedList<ExplicitBoard> neighbors = board.getNeighbors();

        assert neighbors.size() == 4;

        for (ExplicitBoard neighbor : neighbors) {
            System.out.println(neighbor.toString());
        }

    }

    @Test
    public void testSwapTiles() {
        ExplicitBoard initialBoard = new ExplicitBoard(configuration);

        System.out.println("[original] Before swap");
        System.out.println(board.toString());

        ExplicitBoard newBoard = board.swapTiles(1, 1, 1, 2);
        System.out.println("[original] After swap");
        System.out.println(board.toString());

        System.out.println("[new] After swap");
        System.out.println(newBoard.toString());

        /* Make sure that the move happenend. */
        Assert.assertNotEquals(newBoard, board);

        /* Make sure the original board has not been modified. */
        Assert.assertEquals(initialBoard, board);



        System.out.println(newBoard);
    }

    @Test
    public void testMakeMove() {
        ExplicitBoard newBoard = board.makeMove(Direction.UP);

        /* Make sure the move was applied. */
        Assert.assertNotEquals(newBoard, board);

        /* Make sure the original board is unmodified. */
        ExplicitBoard initialBoard = new ExplicitBoard(configuration);
        Assert.assertEquals(initialBoard, board);


        System.out.println(newBoard);
    }
}
