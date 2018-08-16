package za.ac.sun.cs.search.singleagent;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarAgent;
import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Heuristic.ManhattanHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.LinearConflictHeuristic;

public class TestKorf {

    /* Examples 1-10 from Korf. */
    static short configurations[][] = { { 14, 13, 15, 7, 11, 12, 9, 5, 6, 0, 2, 1, 4, 8, 10, 3 },
            { 13, 5, 4, 10, 9, 12, 8, 14, 2, 3, 7, 1, 0, 15, 11, 6 },
            { 14, 7, 8, 2, 13, 11, 10, 4, 9, 12, 5, 0, 3, 6, 1, 15 },
            { 5, 12, 10, 7, 15, 11, 14, 0, 8, 2, 1, 13, 3, 4, 9, 6 },
            { 4, 7, 14, 13, 10, 3, 9, 12, 11, 5, 6, 15, 1, 2, 8, 0 },
            { 14, 7, 1, 9, 12, 3, 6, 15, 8, 11, 2, 5, 10, 0, 4, 13 },
            { 2, 11, 15, 5, 13, 4, 6, 7, 12, 8, 10, 1, 9, 3, 14, 0 },
            { 12, 11, 15, 3, 8, 0, 4, 2, 6, 13, 9, 5, 14, 1, 10, 7 },
            { 3, 14, 9, 11, 5, 4, 8, 2, 13, 12, 6, 7, 10, 1, 15, 0 },
            { 13, 11, 8, 9, 0, 15, 7, 10, 4, 3, 6, 14, 5, 12, 2, 1 } };

    static short configuration[];
    static int i = 0;

    @Before
    public void setupConfiguration() {
        configuration = configurations[i++];
    }

    @Test
    public void test01() throws Exception {
        /* TODO Will probably be passed in as an argument. */

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(41, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(57, path.length);
        Assert.assertEquals(276361933, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test02() {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(43, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(55, path.length);
        Assert.assertEquals(15300442, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test03() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(41, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(59, path.length);
        Assert.assertEquals(565994203, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test04() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(42, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(56, path.length);
        Assert.assertEquals(62643179, idaStarAgent.getExploredNodes());
    }

    @Test
    public void test05() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(42, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(56, path.length);
        Assert.assertEquals(11020325, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test06() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(36, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(52, path.length);
        Assert.assertEquals(32201660, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test07() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(30, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(52, path.length);
        Assert.assertEquals(387138094, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test08() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(32, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(50, path.length);
        Assert.assertEquals(39118937, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test09() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(32, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(46, path.length);
        Assert.assertEquals(1650696, idaStarAgent.getExploredNodes());

    }

    @Test
    public void test10() throws Exception {

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        Assert.assertEquals(43, idaStarAgent.getInitialEstimate());
        Assert.assertEquals(59, path.length);
        Assert.assertEquals(198758703, idaStarAgent.getExploredNodes());

    }
}