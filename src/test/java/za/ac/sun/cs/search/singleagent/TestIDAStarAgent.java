package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.ImplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedTilesHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.ManhattanHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.LinearConflictHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedInadmissableHeuristic;

public class TestIDAStarAgent {

    @Test
    public void misplacedTilesTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };
        short configuration2[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new MisplacedTilesHeuristic());
        Direction[] path = idaStarAgent.solve();

        ImplicitBoard board = new ImplicitBoard(configuration2);
        board.visualizePath(path);
        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }

    @Test
    public void manhattanDistanceTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 14, 13, 15, 7, 11, 12, 9, 5, 6, 0, 2, 1, 4, 8, 10, 3 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }

    @Test
    public void linearConflictTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 14, 13, 15, 7, 11, 12, 9, 5, 6, 0, 2, 1, 4, 8, 10, 3 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new LinearConflictHeuristic());
        Direction[] path = idaStarAgent.solve();

        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }

    @Test
    public void inadmissableTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };
        short configuration2[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new MisplacedInadmissableHeuristic());
        Direction[] path = idaStarAgent.solve();

        ImplicitBoard board = new ImplicitBoard(configuration2);
        board.visualizePath(path);
        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }
}