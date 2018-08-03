package za.ac.sun.cs.search.singleagent;

import org.junit.Test;

public class TestAStarAgent {

    @Test
    public void smokeTest() {
        /* TODO Will probably be passed in as an argument. */
        short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};

        AStarAgent aStarAgent = new AStarAgent(configuration);

        aStarAgent.solve();
    }
}
