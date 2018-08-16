package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import org.junit.Assert;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Heuristic.ManhattanHeuristic;

public class TestKorf {

    @Test
    public void test01() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 14, 13, 15, 7, 11, 12, 9, 5, 6, 0, 2, 1, 4, 8, 10, 3 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(57, path.length);

    }

    @Test
    public void test02() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 13, 5, 4, 10, 9, 12, 8, 14, 2, 3, 7, 1, 0, 15, 11, 6 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(55, path.length);

    }

    @Test
    public void test03() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        short configuration[] = { 14, 7, 8, 2, 13, 11, 10, 4, 9, 12, 5, 0, 3, 6, 1, 15 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(59, path.length);

    }
}