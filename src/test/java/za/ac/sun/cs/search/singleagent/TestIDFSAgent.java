package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDFSAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.ImplicitBoard;

public class TestIDFSAgent {

    @Test
    public void smokeTest() throws Exception {
        /* TODO Will probably be passed in as an argument. */
        short configuration[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };
        short configuration2[] = { 8, 7, 5, 3, 0, 1, 4, 2, 6 };

        IDFSAgent idfsAgent = new IDFSAgent(configuration);
        Direction[] path = idfsAgent.solve();

        ImplicitBoard board = new ImplicitBoard(configuration2);
        board.visualizePath(path);
    }
}