package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

/* Interface for use when creating heuristics. */

public interface Heuristic {
    int getHeuristicCostEstimate(Domain domain);
}