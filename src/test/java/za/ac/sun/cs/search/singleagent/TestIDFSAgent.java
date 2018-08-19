package za.ac.sun.cs.search.singleagent;

import org.junit.Test;
import za.ac.sun.cs.search.singleagent.Agent.IDFSAgent;
import za.ac.sun.cs.search.singleagent.Domain.Board.Direction;
import za.ac.sun.cs.search.singleagent.Domain.Board.ImplicitBoard;

public class TestIDFSAgent {

    @Test
    public void Hard8Test() {
        short configuration[] = { 8, 6, 7, 2, 5, 4, 3, 0, 1 };

        IDFSAgent idfsAgent = new IDFSAgent(configuration);

        Direction[] solution = idfsAgent.solve();

        System.out.println("Explored Nodes: " + idfsAgent.getExploredNodes());
    }
}