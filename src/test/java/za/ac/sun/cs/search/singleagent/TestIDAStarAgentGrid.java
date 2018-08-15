package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.ImplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedTilesHeuristic;

public class TestIDAStarAgentGrid {

    @Test
    public void smokeTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };
        short configuration2[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new MisplacedTilesHeuristic());
        Direction[] path = idaStarAgent.solve();

        ImplicitBoard board = new ImplicitBoard(configuration2);
        board.visualizePath(path);

    }
}
