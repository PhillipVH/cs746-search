package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDFSGridAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Grid.ImplicitGrid;

public class TestIDFSGridAgent {

    @Test
    public void smokeTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */
        boolean configuration[][] = { { false, true, true }, { true, true, true }, { false, true, true } };
        boolean configuration2[][] = { { false, true, true }, { true, true, true }, { false, true, true } };

        short[] playerPosition = { 2, 2 };
        short[] playerPosition2 = { 2, 2 };
        short[] goalPosition = { 0, 1 };

        IDFSGridAgent idfsAgent = new IDFSGridAgent(configuration, playerPosition, goalPosition);
        Direction[] path = idfsAgent.solve();

        ImplicitGrid grid = new ImplicitGrid(configuration2, playerPosition2, goalPosition);
        grid.visualizePath(path);
    }
}