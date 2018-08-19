package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.AStarGridAgent;
import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Heuristic.EuclideanHeuristic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TestAStarGridAgent {

    @Test
    public void smokeTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */
        boolean configuration[][] = { { false, true, true }, { true, true, true }, { false, true, true } };

        short[] playerPosition = { 2, 2 };
        short[] goalPosition = { 0, 1 };

        AStarGridAgent astarAgent = new AStarGridAgent(configuration, playerPosition, goalPosition,
                new EuclideanHeuristic());
        Direction[] solution = astarAgent.solve();

        System.out.println(Arrays.toString(solution));
        System.out.println("Solution Cost: " + solution.length);
    }
}
