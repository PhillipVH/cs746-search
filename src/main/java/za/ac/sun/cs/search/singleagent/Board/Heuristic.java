package za.ac.sun.cs.search.singleagent.Board;


public interface Heuristic {
    int getHeuristicCostEstimate(Domain domain); 
}