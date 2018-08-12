package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.AStarAgent;

public class TestAStarAgent {

    @Test
    public void smokeTest() {
        /* TODO Will probably be passed in as an argument. */
        short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};
//        short configuration[] = {1, 0, 2, 3, 4, 5, 6, 7, 8};

        AStarAgent aStarAgent = new AStarAgent(configuration);

        aStarAgent.solve();
    }

    @Test
    public void smallTest() {
        short configuration[] = {3, 2, 1, 0};

        AStarAgent aStarAgent = new AStarAgent(configuration);

        aStarAgent.solve();
    }

    @Test
    public void hard8Puzzle() {
        short configuration[] = {8, 6, 7, 2, 5, 4, 3, 0, 1};

        AStarAgent aStarAgent = new AStarAgent(configuration);

        aStarAgent.solve();
    }
}
