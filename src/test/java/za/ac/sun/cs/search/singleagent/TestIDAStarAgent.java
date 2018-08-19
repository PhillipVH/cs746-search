package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDAStarAgent;
import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Board.ImplicitBoard;
import za.ac.sun.cs.search.singleagent.Heuristic.MisplacedTilesHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.ManhattanHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.LinearConflictHeuristic;
import za.ac.sun.cs.search.singleagent.Heuristic.NullHeuristic;
public class TestIDAStarAgent {

    @Test
    public void MisplacedTilesTest() {
        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new MisplacedTilesHeuristic());

        Direction[] solution = idaStarAgent.solve();

        System.out.println("Explored Nodes: " + idaStarAgent.getExploredNodes());
    }

    @Test
    public void manhattanDistanceTest() throws Exception {

        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new ManhattanHeuristic());
        Direction[] path = idaStarAgent.solve();

        System.out.println("Explored: " + idaStarAgent.getExploredNodes());
        System.out.println("Length: " + path.length);

    }

 

    @Test
    public void linearConflictTest() throws Exception {

        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new LinearConflictHeuristic());
        Direction[] path = idaStarAgent.solve();

        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }

    @Test
    public void NullTest() throws Exception {

        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        IDAStarAgent idaStarAgent = new IDAStarAgent(configuration, new NullHeuristic());
        Direction[] path = idaStarAgent.solve();

        System.out.println("Explored: " + idaStarAgent.getExploredNodes());

    }
}
