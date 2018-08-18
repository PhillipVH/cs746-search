package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

/* Manhattan distance heuristic. */

public class ManhattanPathFinderHeuristic implements Heuristic {
    private double epsilon;

    public ManhattanPathFinderHeuristic(double epsilon) {
        this.epsilon = epsilon;
    }

    public ManhattanPathFinderHeuristic() {
        this.epsilon = 1;
    }

    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        short[] playerPosition = domain.getPlayerPosition();
        short[] goalPosition = domain.getGoalState();
        int cost = 0;

        cost += Math.abs(playerPosition[0] - goalPosition[0]);
        cost += Math.abs(playerPosition[1] - goalPosition[1]);

        return (int) (this.epsilon * cost);
    }

}