package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

/**
 * This heuristic always returns 0.
 */
public class NullHeuristic implements Heuristic {

    @Override
    public int getHeuristicCostEstimate(Domain domain) {
        return 0;
    }
}
