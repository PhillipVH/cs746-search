package za.ac.sun.cs.search.singleagent;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.AStarAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Heuristic.Heuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.ManhattanHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedTilesHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.NullHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.LinearConflictHeuristic;

import java.util.Arrays;

public class TestAStarAgent {

    @Test
    public void smokeTest() {
        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };
        AStarAgent aStarAgent = new AStarAgent(configuration, new MisplacedTilesHeuristic());

        Direction[] solution = aStarAgent.solve();

        Assert.assertEquals(22, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
        System.out.println("Nodes Explored: " + aStarAgent.getExploredNodes()); // 23_696
    }

    @Test
    public void smokeTestTaxicab() {
        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };
        AStarAgent aStarAgent = new AStarAgent(configuration, new ManhattanHeuristic());

        Direction[] solution = aStarAgent.solve();

        Assert.assertEquals(22, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
        System.out.println("Nodes Explored: " + aStarAgent.getExploredNodes()); // 23_696
    }

    @Test
    public void smallTest() {
        short configuration[] = { 3, 2, 1, 0 };

        AStarAgent aStarAgent = new AStarAgent(configuration, new MisplacedTilesHeuristic());

        Direction[] solution = aStarAgent.solve();

        Assert.assertEquals(6, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }

    @Test
    public void hard8Puzzle() {
        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        AStarAgent aStarAgent = new AStarAgent(configuration, new MisplacedTilesHeuristic());

        Direction[] solution = aStarAgent.solve();

        Assert.assertEquals(27, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }

    @Test
    public void manhattanDistanceTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };

        AStarAgent aStarAgent = new AStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = aStarAgent.solve();

        System.out.println("Explored: " + aStarAgent.getExploredNodes());

    }

    @Test
    public void linearConflictTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };

        AStarAgent aStarAgent = new AStarAgent(configuration, new LinearConflictHeuristic());
        Direction[] path = aStarAgent.solve();

        System.out.println("Explored: " + aStarAgent.getExploredNodes());

    }
}
