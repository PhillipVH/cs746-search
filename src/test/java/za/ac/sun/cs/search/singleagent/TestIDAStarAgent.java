package za.ac.sun.cs.search.singleagent;

import org.junit.Test;

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
}
