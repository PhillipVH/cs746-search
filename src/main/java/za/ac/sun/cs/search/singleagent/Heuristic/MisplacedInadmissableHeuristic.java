package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;
/* Misplaced Tiles heuristic, which is inadmissable because we include the empty tile. */

public class MisplacedInadmissableHeuristic implements Heuristic {

    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        int cost = 0;
        int idx = 0;
        int N = domain.getSize();
        short[] goalState = domain.getGoalState();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (domain.getAt(i, j) != goalState[idx++]) {
                    cost += 1;
                }
            }
        }

        return cost;
    }
}