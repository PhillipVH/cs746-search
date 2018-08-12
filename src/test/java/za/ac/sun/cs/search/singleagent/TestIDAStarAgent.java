package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.ImplicitBoard;

public class TestIDAStarAgent {

    @Test
    public void smokeTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */
        short configuration[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};
        short configuration2[] = {8, 7, 5, 3, 0, 1, 4, 2, 6};

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration);
        Direction[] path = idaStarAgent.solve();

        ImplicitBoard board = new ImplicitBoard(configuration2);
        board.visualizePath(path);
    }

    @Test
    public void smallTest() throws Exception {
        short configuration[] = {3, 2, 1, 0};
        short configuration2[] = {3, 2, 1, 0};

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration);
        Direction[] path = idaStarAgent.solve();

        ImplicitBoard board = new ImplicitBoard(configuration2);
        board.visualizePath(path);
    }

    @Test
    public void hard8Puzzle() throws Exception {
        short configuration[] = {8, 6, 7, 2, 5, 4, 3, 0, 1};
        short configuration2[] = {8, 6, 7, 2, 5, 4, 3, 0, 1};

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration);

        Direction[] path = idaStarAgent.solve();

        ImplicitBoard board = new ImplicitBoard(configuration2);
        board.visualizePath(path);
    }
}
