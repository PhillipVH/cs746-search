package za.ac.sun.cs.search.singleagent;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.BidirectionalAStarAgent;
import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Heuristic.ManhattanHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedTilesHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.LinearConflictHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.NullHeuristic;

import java.util.Arrays;

public class TestBidirectionalAStarAgent {

    @Test
    public void smokeTest() {
        short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration, true, new MisplacedTilesHeuristic());

        Direction[] solution = bidirectionalAStarAgent.solve();

        Assert.assertEquals(22, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }

    @Test
    public void smallTest() {
        short configuration[] = {3, 2, 1, 0};

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration, true, new MisplacedTilesHeuristic());

        Direction[] solution = bidirectionalAStarAgent.solve();

        Assert.assertEquals(6, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }

    @Test
    public void MisplacedTilesTest() {
        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration, true, new MisplacedTilesHeuristic());

        Direction[] solution = bidirectionalAStarAgent.solve();

        System.out.println("Explored Nodes: " + bidirectionalAStarAgent.getExploredNodes());
    }

    @Test
    public void manhattanDistanceTest() throws Exception {

        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration, true, new ManhattanHeuristic());
        Direction[] path = bidirectionalAStarAgent.solve();

        System.out.println("Explored: " + bidirectionalAStarAgent.getExploredNodes());

    }

 

    @Test
    public void linearConflictTest() throws Exception {

        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration, true, new LinearConflictHeuristic());
        Direction[] path = bidirectionalAStarAgent.solve();

        System.out.println("Explored: " + bidirectionalAStarAgent.getExploredNodes());

    }

    @Test
    public void NullTest() throws Exception {

        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration, true, new NullHeuristic());
        Direction[] path = bidirectionalAStarAgent.solve();

        System.out.println("Explored: " + bidirectionalAStarAgent.getExploredNodes());

    }
}
