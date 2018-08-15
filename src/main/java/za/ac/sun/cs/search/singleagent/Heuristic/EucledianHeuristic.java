package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

public class EucledianHeuristic implements Heuristic {

    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        short[] playerPosition = domain.getPlayerPosition();
        short[] goalPosition = domain.getGoalState();

        double xCost = Math.pow((playerPosition[0] - goalPosition[0]), 2);
        double yCost = Math.pow((playerPosition[1] - goalPosition[1]), 2);

        return (int) Math.sqrt(xCost + yCost);

    }
}