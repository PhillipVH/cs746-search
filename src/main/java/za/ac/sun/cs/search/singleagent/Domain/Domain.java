package za.ac.sun.cs.search.singleagent.Domain;

/* Interface, which the N-Puzzle and Pathfinder problem both implement. */

public interface Domain {
    short getAt(int i, int j);

    int getSize();

    short[] getGoalState();

    default short[] getPlayerPosition() {
        return null;
    }
}