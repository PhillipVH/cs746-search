package za.ac.sun.cs.search.singleagent;

import java.beans.Transient;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarGridAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Grid.ImplicitGrid;
import za.ac.sun.cs.search.singleagent.Heuristic.EucledianHeuristic;

public class TestIDAStarAgentGrid {

    @Test

    public void smokeTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        boolean configuration[][] = { { false, true }, { true, true } };
        boolean configuration2[][] = { { false, true }, { true, true } };

        short[] playerPosition = { 1, 0 };
        short[] goalPosition = { 0, 1 };

        IDAStarGridAgent idaStarAgent = new IDAStarGridAgent(configuration, playerPosition, goalPosition,
                new EucledianHeuristic());
        Direction[] path = idaStarAgent.solve();

        ImplicitGrid grid = new ImplicitGrid(configuration2, playerPosition, goalPosition);
        grid.visualizePath(path);

    }
}