package za.ac.sun.cs.search.singleagent;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.AStarAgent;
import za.ac.sun.cs.search.singleagent.Agent.BidirectionalAStarAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;

import java.util.Arrays;

public class TestBidirectionalAStarAgent {

    @Test
    public void smokeTest() {
        short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration);

        Direction[] solution = bidirectionalAStarAgent.solve();

        Assert.assertEquals(22, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }

    @Test
    public void smallTest() {
        short configuration[] = {3, 2, 1, 0};

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration);

        Direction[] solution = bidirectionalAStarAgent.solve();

        Assert.assertEquals(6, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }

    @Test
    public void hard8Puzzle() {
        short configuration[] = {8, 6, 7, 2, 5, 4, 3, 0, 1};

        BidirectionalAStarAgent bidirectionalAStarAgent = new BidirectionalAStarAgent(configuration);

        Direction[] solution = bidirectionalAStarAgent.solve();

        Assert.assertEquals(27, solution.length);

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }
}
