package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarGridAgent;
import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Grid.ImplicitGrid;
import za.ac.sun.cs.search.singleagent.Heuristic.EucledianHeuristic;

public class TestIDAStarAgentGrid {

    @Test

    public void eucledianTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        boolean configuration[][] = { { false, true, true }, { true, true, true }, { false, true, true } };
        boolean configuration2[][] = { { false, true, true }, { true, true, true }, { false, true, true } };

        short[] playerPosition = { 2, 2 };
        short[] playerPosition2 = { 2, 2 };
        short[] goalPosition = { 0, 1 };

        IDAStarGridAgent idaStarAgent = new IDAStarGridAgent(configuration, playerPosition, goalPosition,
                new EucledianHeuristic());
        Direction[] path = idaStarAgent.solve();

        ImplicitGrid grid = new ImplicitGrid(configuration2, playerPosition2, goalPosition);
        grid.visualizePath(path);
        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }

    @Test

    public void inadmissableTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        boolean configuration[][] = { { false, true, true }, { true, true, true }, { false, true, true } };
        boolean configuration2[][] = { { false, true, true }, { true, true, true }, { false, true, true } };

        short[] playerPosition = { 2, 2 };
        short[] playerPosition2 = { 2, 2 };
        short[] goalPosition = { 0, 1 };

        IDAStarGridAgent idaStarAgent = new IDAStarGridAgent(configuration, playerPosition, goalPosition,
                new EucledianHeuristic(1.5));
        Direction[] path = idaStarAgent.solve();

        ImplicitGrid grid = new ImplicitGrid(configuration2, playerPosition2, goalPosition);
        grid.visualizePath(path);
        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }
}