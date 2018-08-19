package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

/* Eucledian distance heuristic, calcualtes the straight line distance between the player and the goal */

public class EuclideanHeuristic implements Heuristic {
    private double epsilon;

    public EuclideanHeuristic(double epsilon) {
        this.epsilon = epsilon;
    }

    public EuclideanHeuristic() {
        this.epsilon = 1;
    }

    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        short[] playerPosition = domain.getPlayerPosition();
        short[] goalPosition = domain.getGoalState();

        double xCost = Math.pow((playerPosition[0] - goalPosition[0]), 2);
        double yCost = Math.pow((playerPosition[1] - goalPosition[1]), 2);

        return (int) (this.epsilon * Math.sqrt(xCost + yCost));

    }
}