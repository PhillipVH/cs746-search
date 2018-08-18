package za.ac.sun.cs.search.singleagent.Domain;

/**
 * Common domain-specific methods for the N-puzzle and path-finding puzzle.
 */
public interface Domain {
    short getAt(int i, int j);

    int getSize();

    short[] getGoalState();

    default short[] getPlayerPosition() {
        return null;
    }
}