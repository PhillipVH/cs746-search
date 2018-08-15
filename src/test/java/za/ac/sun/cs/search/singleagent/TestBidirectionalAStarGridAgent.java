package za.ac.sun.cs.search.singleagent;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.BidirectionalAStarGridAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Heuristic.EucledianHeuristic;

import java.util.Arrays;

public class TestBidirectionalAStarGridAgent {

    @Test
    public void smokeTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */
        boolean configuration[][] = { { false, true, true }, { true, true, true }, { false, true, true } };

        short[] playerPosition = { 2, 2 };
        short[] goalPosition = { 0, 1 };

        BidirectionalAStarGridAgent biDirectionalAstarAgent = new BidirectionalAStarGridAgent(configuration,
                playerPosition, goalPosition, true, new EucledianHeuristic());
        Direction[] solution = biDirectionalAstarAgent.solve();

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }

}
