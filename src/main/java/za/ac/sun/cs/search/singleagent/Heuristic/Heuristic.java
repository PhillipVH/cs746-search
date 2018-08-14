package za.ac.sun.cs.search.singleagent.Heuristic;

import za.ac.sun.cs.search.singleagent.Domain.Domain;

public interface Heuristic {
    int getHeuristicCostEstimate(Domain domain); 
}