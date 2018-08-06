package za.ac.sun.cs.search.singleagent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class TestExplicitBoard {

    ExplicitBoard board;
    short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};
    short goal[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};

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
    public void testThreeNeighbors() {

        short configuration[] = {1, 0, 2, 3, 4, 5, 6, 7, 8};
        ExplicitBoard newBoard = new ExplicitBoard(configuration);

        Assert.assertEquals(3, newBoard.getNeighbors().size());


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

    @Test
    public void testContains() {
        PriorityQueue<ExplicitBoard> openSet = new PriorityQueue<>();

        openSet.add(board);
        openSet.add(board);

        Assert.assertEquals(2, openSet.size());

        Assert.assertTrue(openSet.contains(board));
    }

    @Test
    public void testComparison() {
        ExplicitBoard newBoard = new ExplicitBoard(configuration);

        board.setCostFromStart(12);
        newBoard.setCostFromStart(0);


        /* The configuration used has a heurstic cost of 7. */
        Assert.assertEquals(19, board.getCost());


        Assert.assertEquals(7, newBoard.getCost());

        Assert.assertTrue(board.compareTo(newBoard) > 0);
        Assert.assertTrue(newBoard.compareTo(board) < 0);
    }

    @Test
    public void testReverseNaturalOrder() {

        PriorityQueue<ExplicitBoard> openSet = new PriorityQueue<>(Comparator.reverseOrder());

        ExplicitBoard newBoard = new ExplicitBoard(configuration);
        board.setCostFromStart(12);

        newBoard.setCostFromStart(0);

        openSet.add(board);
        openSet.add(newBoard);

        /* Ensure that lower cost nodes are dequeued first. */
        Assert.assertEquals(newBoard, openSet.poll());
        Assert.assertEquals(7, newBoard.getCost());

        Assert.assertEquals(board, openSet.poll());

    }

    @Test
    public void testHeuristic() {
        ExplicitBoard terminalBoard = new ExplicitBoard(goal);

        /* The board with the goal state as internal state is a terminal node. */
        Assert.assertTrue(terminalBoard.isTerminal());

        /* The terminal board has a heuristic cost of zero. */
        Assert.assertEquals(0, terminalBoard.getEstimatedCost());

        /* The non-terminal board used as an example should have a score of 7 (we don't count the blank tile. */
        Assert.assertEquals(7, board.getEstimatedCost());
    }

    @Test
    public void testHashContains() {
        HashSet<ExplicitBoard> set = new HashSet<>();

        set.add(board);

        Assert.assertTrue(set.contains(board));
    }

}