package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

public class ManhattanHeuristic implements Heuristic {

    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        int cost = 0;
        int idx = 0;
        int N = domain.getSize();
        short[] goalState = domain.getGoalState();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                short tile = domain.getAt(i, j);
                /* Don't count the blank tile. */
                if (tile == 0) {
                    idx++;
                    continue;
                }
                if (tile != goalState[idx++]) {
                    cost += getDistance(goalState, i, j, tile);
                }
            }
        }

        return cost;
    }

    private int getDistance(short[] goalState, int row, int column, short tile) {
        int cost = 0;

        for (int i = 0; i < goalState.length; i++) {
            if (goalState[i] == tile) {
                cost += Math.abs((i / 3) - row);
                cost += Math.abs((i % 3) - column);
                return cost;
            }
        }

        return 0;
    }
}