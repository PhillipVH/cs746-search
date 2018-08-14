package za.ac.sun.cs.search.singleagent.Board;

import za.ac.sun.cs.search.singleagent.Board.Direction;
import za.ac.sun.cs.search.singleagent.Board.ImplicitBoard;

import java.util.Stack;

public class MisplacedTilesHeuristic implements Heuristic {
   
    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        int cost = 0;
        int idx = 0;
        int N = domain.getSize();
        short[] goalState = domain.getGoalState();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                /* Don't count the blank tile. */
                if (domain.getAt(i, j) == 0) {
                    idx++;
                    continue;
                }
                if (domain.getAt(i, j) != goalState[idx++]) {
                    cost += 1;
                }
            }
        }

        return cost;
    }
}