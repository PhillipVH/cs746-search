package za.ac.sun.cs.search.singleagent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Board.ExplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedTilesHeuristic;

import java.util.*;

import static za.ac.sun.cs.search.singleagent.Domain.Board.Direction.*;

public class TestExplicitBoard {

    ExplicitBoard board;
    short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};
    short goal[] = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    @Before
    public void setupBoard() {
        board = new ExplicitBoard(configuration, new MisplacedTilesHeuristic());
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
        ExplicitBoard newBoard = new ExplicitBoard(configuration, new MisplacedTilesHeuristic());

        Assert.assertEquals(3, newBoard.getNeighbors().size());


    }

    @Test
    public void testSwapTiles() {
        ExplicitBoard initialBoard = new ExplicitBoard(configuration,new MisplacedTilesHeuristic());

        System.out.println("[original] Before swap");
        System.out.println(board.toString());

        ExplicitBoard newBoard = (ExplicitBoard) board.swapTiles(1, 1, 1, 2);
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
        ExplicitBoard newBoard = (ExplicitBoard) board.makeMove(UP);

        /* Make sure the move was applied. */
        Assert.assertNotEquals(newBoard, board);

        /* Make sure the original board is unmodified. */
        ExplicitBoard initialBoard = new ExplicitBoard(configuration, new MisplacedTilesHeuristic());
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
        ExplicitBoard newBoard = new ExplicitBoard(configuration, new MisplacedTilesHeuristic());

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

        ExplicitBoard newBoard = new ExplicitBoard(configuration, new MisplacedTilesHeuristic());
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
        Heuristic heuristic = new MisplacedTilesHeuristic();
        ExplicitBoard terminalBoard = new ExplicitBoard(goal, heuristic);

        /* The board with the goal state as internal state is a terminal node. */
        Assert.assertTrue(terminalBoard.isTerminal());

        /* The terminal board has a heuristic cost of zero. */
        Assert.assertEquals(0, heuristic.getHeuristicCostEstimate(terminalBoard));

        /* The non-terminal board used as an example should have a score of 7 (we don't count the blank tile. */
        Assert.assertEquals(7, heuristic.getHeuristicCostEstimate(board));
    }

    @Test
    public void testHashContains() {
        HashSet<ExplicitBoard> set = new HashSet<>();

        set.add(board);

        Assert.assertTrue(set.contains(board));
    }

    @Test
    public void testIncreasingCostBug() {
        board.setCostFromStart(1);

        Assert.assertEquals(8, board.getCost());

        board.setCostFromStart(0);

        Assert.assertEquals(7, board.getCost());
    }

    @Test
    public void testExplicitGoalState() {
        short[] configuration = {0, 1, 2, 3};
        short[] goal = {3, 2, 1, 0};

        ExplicitBoard reverse = new ExplicitBoard(configuration, goal, new MisplacedTilesHeuristic());

        /* Even though the board starts in a traditional start state, it should not be flagged as a terminal node. */
        Assert.assertFalse(reverse.isTerminal());

        /* The shortest solution to this as determined by AStarAgent. */
        Direction[] solution = new Direction[] {RIGHT, DOWN, LEFT, UP, RIGHT, DOWN};

        /* Apply the moves in and confirm the board is now in our defined goal state. */
        for (Direction move : solution) {
            reverse = reverse.makeMove(move);
        }

        System.out.println(reverse);

        Assert.assertTrue(reverse.isTerminal());
    }

}
